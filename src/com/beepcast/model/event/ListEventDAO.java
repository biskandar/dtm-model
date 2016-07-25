package com.beepcast.model.event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.model.util.DateTimeFormat;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListEventDAO {

  static final DLogContext lctx = new SimpleContext( "ListEventDAO" );

  private DatabaseLibrary dbLib;

  public ListEventDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalActiveEvents( int clientId , int type ,
      String inKeywordEventNames , String exKeywordEventNames , int display ) {
    long totalRecords = 0;

    if ( clientId < 1 ) {
      return totalRecords;
    }

    // compose sql
    String sqlSelect = "SELECT COUNT(event.event_id) AS total ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( clientId , type , inKeywordEventNames ,
        exKeywordEventNames , display );
    String sql = sqlSelect + sqlFrom + sqlWhere;

    // execute and fetch query
    DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      Iterator iqr = qr.iterator();
      QueryItem qi = null;
      if ( iqr.hasNext() ) {
        qi = (QueryItem) iqr.next();
      }
      if ( qi != null ) {
        try {
          totalRecords = Long.parseLong( qi.getFirstValue() );
        } catch ( NumberFormatException e ) {
        }
      }
    }

    return totalRecords;
  }

  public List selectActiveEvents( int clientId , int type ,
      String inKeywordEventNames , String exKeywordEventNames , int display ,
      int top , int limit , int orderby ) {
    List eventBeans = new ArrayList();

    if ( clientId < 1 ) {
      return eventBeans;
    }

    // compose sql
    String sqlSelect = "SELECT event.* , channel_session.id AS 'channel_session_id' ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( clientId , type , inKeywordEventNames ,
        exKeywordEventNames , display );

    String sqlOrderBy = "";
    switch ( orderby ) {
    case ListEventService.ORDERBY_EVENTID_ASC :
      sqlOrderBy = "ORDER BY event.event_id ASC ";
      break;
    case ListEventService.ORDERBY_EVENTID_DESC :
      sqlOrderBy = "ORDER BY event.event_id DESC ";
      break;
    case ListEventService.ORDERBY_EVENTNAME_ASC :
      sqlOrderBy = "ORDER BY event.event_name ASC ";
      break;
    case ListEventService.ORDERBY_EVENTNAME_DESC :
      sqlOrderBy = "ORDER BY event.event_name DESC ";
      break;
    case ListEventService.ORDERBY_DATEUPDATED_ASC :
      sqlOrderBy = "ORDER BY event.date_updated ASC ";
      break;
    case ListEventService.ORDERBY_DATEUPDATED_DESC :
      sqlOrderBy = "ORDER BY event.date_updated DESC ";
      break;
    }

    String sqlLimit = "LIMIT " + top + " , " + limit + " ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrderBy + sqlLimit;

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        EventBean event = populateBean( rs );
        eventBeans.add( event );
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to select active events , " + e );
    } finally {
      conn.disconnect( true );
    }

    return eventBeans;
  }

  private String sqlFrom() {
    String sqlFrom = "FROM event ";
    sqlFrom += "LEFT OUTER JOIN channel_session "
        + "ON ( channel_session.event_id = event.event_id ) "
        + "AND ( channel_session.active = 1 ) ";
    return sqlFrom;
  }

  private String sqlWhere( int clientId , int type ,
      String inKeywordEventNames , String exKeywordEventNames , int display ) {

    String sqlWhere = "WHERE ( event.client_id = " + clientId + " ) ";

    sqlWhere += "AND ( event.active = 1 ) AND ( event.codes <> '' ) ";

    switch ( type ) {
    case EventType.ALL :
      break;
    case EventType.EVENT :
      sqlWhere += "AND ( event.channel = 0 ) ";
      break;
    case EventType.CHANNEL :
      sqlWhere += "AND ( event.channel = 1 ) ";
      break;
    }

    if ( !StringUtils.isBlank( inKeywordEventNames ) ) {
      String sqlWhereKeywords = ListEventQuery.sqlWhereLikeKeywords(
          inKeywordEventNames , "," , "OR" , "event.event_name" , true );
      if ( ( sqlWhereKeywords != null ) && ( !sqlWhereKeywords.equals( "" ) ) ) {
        sqlWhere += "AND ( " + sqlWhereKeywords + " ) ";
      }
    }
    if ( !StringUtils.isBlank( exKeywordEventNames ) ) {
      String sqlWhereKeywords = ListEventQuery.sqlWhereLikeKeywords(
          exKeywordEventNames , "," , "AND" , "event.event_name" , false );
      if ( ( sqlWhereKeywords != null ) && ( !sqlWhereKeywords.equals( "" ) ) ) {
        sqlWhere += "AND ( " + sqlWhereKeywords + " ) ";
      }
    }

    switch ( display ) {
    case ListEventService.DISPLAY_ON :
      sqlWhere += "AND ( event.display = 1 ) ";
      break;
    case ListEventService.DISPLAY_OFF :
      sqlWhere += "AND ( event.display = 0 ) ";
      break;
    }

    return sqlWhere;
  }

  private EventBean populateBean( ResultSet rs ) throws SQLException {
    EventBean eventBean = new EventBean();
    eventBean.setEventID( (long) rs.getDouble( "event_id" ) );
    eventBean.setEventName( rs.getString( "event_name" ) );
    eventBean.setClientID( (long) rs.getDouble( "client_id" ) );
    eventBean.setCatagoryID( (long) rs.getDouble( "catagory_id" ) );
    eventBean.setStartDate( DateTimeFormat.convertToDate( rs
        .getString( "start_date" ) ) );
    eventBean.setEndDate( DateTimeFormat.convertToDate( rs
        .getString( "end_date" ) ) );
    eventBean.setRemindDate( DateTimeFormat.convertToDate( rs
        .getString( "remind_date" ) ) );
    eventBean.setRemindFreq( rs.getString( "remind_freq" ) );
    eventBean.setNumCodes( (int) rs.getDouble( "num_codes" ) );
    eventBean.setCodeLength( (int) rs.getDouble( "code_length" ) );
    eventBean.setCodes( rs.getString( "codes" ) );
    eventBean.setComment( rs.getString( "comment" ) );
    eventBean.setProcess( rs.getString( "process" ) );
    eventBean.setProcessType( (int) rs.getDouble( "process_type" ) );
    eventBean.setPingCount( (long) rs.getDouble( "ping_count" ) );
    eventBean.setBudget( new Double( rs.getString( "budget" ) ).doubleValue() );
    eventBean.setUsedBudget( new Double( rs.getString( "used_budget" ) )
        .doubleValue() );
    eventBean.setChannel( ( rs.getDouble( "channel" ) == 1 ) ? true : false );
    eventBean
        .setMobileMenuEnabled( ( rs.getDouble( "mobile_menu_enable" ) == 1 ) ? true
            : false );
    eventBean.setMobileMenuName( rs.getString( "mobile_menu_name" ) );
    eventBean.setOverbudgetDate( DateTimeFormat.convertToDate( rs
        .getString( "overbudget_date" ) ) );
    eventBean
        .setUnsubscribeImmediate( ( rs.getDouble( "unsubscribe_immediate" ) == 1 ) ? true
            : false );
    eventBean.setBitFlags( (long) rs.getDouble( "bit_flags" ) );
    eventBean.setMobileMenuBrandName( rs.getString( "mobile_menu_brand_name" ) );
    eventBean.setUnsubscribeResponse( rs.getString( "unsubscribe_response" ) );
    eventBean.setOutgoingNumber( rs.getString( "outgoing_number" ) );
    eventBean.setClientEventID( rs.getString( "client_event_id" ) );
    eventBean.setSenderID( rs.getString( "senderID" ) );
    eventBean.setDisplay( rs.getBoolean( "display" ) );
    eventBean.setSuspend( rs.getBoolean( "suspend" ) );
    eventBean.setActive( rs.getBoolean( "active" ) );
    eventBean.setDateInserted( DateTimeFormat.convertToDate( rs
        .getString( "date_inserted" ) ) );
    eventBean.setDateUpdated( DateTimeFormat.convertToDate( rs
        .getString( "date_updated" ) ) );

    eventBean.setChannelSessionId( (int) rs.getInt( "channel_session_id" ) );

    return eventBean;
  }

}
