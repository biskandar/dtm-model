package com.beepcast.model.event;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.model.util.DateTimeFormat;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.beepcast.util.Util;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EventDAO {

  static final DLogContext lctx = new SimpleContext( "EventDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public EventDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public long insert( EventBean eventBean ) throws IOException {

    // verify unique event for this client
    String eventName = eventBean.getEventName();
    long clientID = eventBean.getClientID();
    boolean channel = eventBean.getChannel();

    if ( select( eventName , clientID , channel ) != null ) {
      DLog.warning( lctx , "Failed to insert new channel "
          + ", found event name is already exist = " + eventName );
      throw new IOException( "Event [" + eventName + "] is already exists." );
    }

    // set next 10 years
    Calendar next10years = Calendar.getInstance();
    next10years.add( Calendar.YEAR , 10 );

    // validate datetime
    Date eventStartDate = eventBean.getStartDate();
    eventStartDate = ( eventStartDate == null ) ? next10years.getTime()
        : eventStartDate;
    Date eventEndDate = eventBean.getEndDate();
    eventEndDate = ( eventEndDate == null ) ? next10years.getTime()
        : eventEndDate;
    Date eventRemindDate = eventBean.getRemindDate();
    eventRemindDate = ( eventRemindDate == null ) ? next10years.getTime()
        : eventRemindDate;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into event " + "(EVENT_NAME,CLIENT_ID,CATAGORY_ID,"
        + "START_DATE,END_DATE,REMIND_DATE,REMIND_FREQ,NUM_CODES,"
        + "CODE_LENGTH,CODES,COMMENT,PROCESS,PROCESS_TYPE,"
        + "PING_COUNT,BUDGET,USED_BUDGET,CHANNEL,MOBILE_MENU_ENABLE,"
        + "MOBILE_MENU_NAME,OVERBUDGET_DATE,UNSUBSCRIBE_IMMEDIATE,"
        + "BIT_FLAGS,MOBILE_MENU_BRAND_NAME,UNSUBSCRIBE_RESPONSE,"
        + "OUTGOING_NUMBER,CLIENT_EVENT_ID,SENDERID,"
        + "DISPLAY,SUSPEND,ACTIVE,DATE_INSERTED,DATE_UPDATED) " + "values ("
        + "'"
        + StringEscapeUtils.escapeSql( eventName )
        + "',"
        + clientID
        + ","
        + eventBean.getCatagoryID()
        + ","
        + "'"
        + DateTimeFormat.convertToString( eventStartDate )
        + "','"
        + DateTimeFormat.convertToString( eventEndDate )
        + "','"
        + DateTimeFormat.convertToString( eventRemindDate )
        + "','"
        + eventBean.getRemindFreq()
        + "',"
        + eventBean.getNumCodes()
        + ","
        + eventBean.getCodeLength()
        + ",'"
        + eventBean.getCodes()
        + "','"
        + StringEscapeUtils.escapeSql( eventBean.getComment() )
        + "','"
        + StringEscapeUtils.escapeSql( eventBean.getProcess() )
        + "',"
        + eventBean.getProcessType()
        + ","
        + eventBean.getPingCount()
        + ","
        + "'"
        + eventBean.getBudget()
        + "',"
        + "'"
        + eventBean.getUsedBudget()
        + "',"
        + ( channel ? 1 : 0 )
        + ","
        + ( ( eventBean.getMobileMenuEnabled() == true ) ? 1 : 0 )
        + ","
        + "'"
        + StringEscapeUtils.escapeSql( eventBean.getMobileMenuName() )
        + "',"
        + "'"
        + DateTimeFormat.convertToString( eventBean.getOverbudgetDate() )
        + "',"
        + ( eventBean.getUnsubscribeImmediate() ? 1 : 0 )
        + ","
        + eventBean.getBitFlags()
        + ",'"
        + StringEscapeUtils.escapeSql( eventBean.getMobileMenuBrandName() )
        + "','"
        + StringEscapeUtils.escapeSql( eventBean.getUnsubscribeResponse() )
        + "','"
        + StringEscapeUtils.escapeSql( eventBean.getOutgoingNumber() )
        + "','"
        + StringEscapeUtils.escapeSql( eventBean.getClientEventID() )
        + "','"
        + StringEscapeUtils.escapeSql( eventBean.getSenderID() )
        + "',"
        + ( eventBean.isDisplay() ? 1 : 0 )
        + ","
        + ( eventBean.isSuspend() ? 1 : 0 )
        + ","
        + ( eventBean.isActive() ? 1 : 0 )
        + ",'"
        + DateTimeFormat.convertToString( eventBean.getDateInserted() )
        + "','"
        + DateTimeFormat.convertToString( eventBean.getDateUpdated() ) + "')";

    /*--------------------------
      execute query
    --------------------------*/
    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    dbLib.executeQuery( "profiledb" , sql );

    /*--------------------------
      return event id
    --------------------------*/
    long eventID = 0;
    EventBean eventBeanTemp = select( eventName , clientID , channel );
    if ( eventBeanTemp != null ) {
      eventID = eventBeanTemp.getEventID();
    }

    return eventID;
  }

  public EventBean select( long eventID ) throws IOException {
    EventBean eventBean = null;
    // verify params
    if ( eventID < 1 ) {
      return eventBean;
    }
    // compose sql
    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM event ";
    String sqlWhere = "WHERE ( event_id = " + eventID + " ) ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;
    // populate bean
    eventBean = populateBean( sql );
    return eventBean;
  }

  public EventBean select( String eventName , long clientID , boolean channel )
      throws IOException {
    EventBean eventBean = null;
    // verify params
    if ( ( eventName == null ) || ( eventName.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to select event , found empty event name" );
      return eventBean;
    }
    if ( clientID < 1 ) {
      DLog.warning( lctx , "Failed to select event , found zero clientId" );
      return eventBean;
    }
    // compose sql
    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM event ";
    String sqlWhere = "WHERE ( channel = " + ( channel ? 1 : 0 ) + " ) ";
    sqlWhere += "AND ( event_name = '"
        + StringEscapeUtils.escapeSql( eventName ) + "' ) ";
    sqlWhere += "AND ( client_id = " + clientID + " ) ";
    String sqlOrder = "ORDER BY event_id DESC ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder + sqlLimit;
    // populate bean
    eventBean = populateBean( sql );
    return eventBean;
  }

  public LinkedHashMap selectAll( long clientID , Boolean active ,
      Boolean display , Boolean channel ) throws IOException {
    LinkedHashMap events = null;
    // verify params
    if ( clientID < 1 ) {
      return events;
    }
    // compose sql
    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM event ";
    String sqlWhere = "WHERE ( client_id = " + clientID + " ) ";
    if ( active != null ) {
      sqlWhere += "AND ( active = " + ( active.booleanValue() ? 1 : 0 ) + " ) ";
    }
    if ( display != null ) {
      sqlWhere += "AND ( display = " + ( display.booleanValue() ? 1 : 0 )
          + " ) ";
    }
    if ( channel != null ) {
      sqlWhere += "AND ( channel = " + ( channel.booleanValue() ? 1 : 0 )
          + " ) ";
    }
    String sqlOrder = "ORDER BY event_name ASC ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;
    // populate beans
    events = populateBeans( sql );
    return events;
  }

  public String[] selectChannelIDs( Date dateTm ) throws IOException {

    String channelIDs[] = null;
    Vector v = new Vector( 1000 , 1000 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "select event_id from event " + "where remind_date<='"
        + Util.strFormat( dateTm , "yyyy-mm-dd hh:nn:ss" ) + "' "
        + "and channel=1";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        v.addElement( "" + (long) rs.getDouble( "event_id" ) );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    /*--------------------------
      build array of channelIDs
    --------------------------*/
    channelIDs = new String[v.size()];
    for ( int i = 0 ; i < channelIDs.length ; i++ ) {
      channelIDs[i] = (String) v.elementAt( i );
    }

    // System.out.println(channelIDs);
    return channelIDs;

  } // selectChannelIDs(date)

  public int totalActiveEvents( int clientId ) {
    int totalRecords = 0;

    String sqlSelect = "SELECT COUNT(event_id) AS total ";
    String sqlFrom = "FROM event ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";

    String sql = sqlSelect + sqlFrom + sqlWhere;

    // DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return totalRecords;
    }
    Iterator iter = qr.iterator();
    if ( !iter.hasNext() ) {
      return totalRecords;
    }
    QueryItem qi = (QueryItem) iter.next();
    try {
      totalRecords = Integer.parseInt( qi.getFirstValue() );
    } catch ( NumberFormatException e ) {
    }

    return totalRecords;
  }

  public boolean updateChannelCodes( long eventID , String codes ) {
    boolean result = false;

    String sqlUpdate = "UPDATE event ";
    String sqlSet = "SET codes = '" + codes + "' , date_updated = NOW() ";
    String sqlWhere = "WHERE ( event_id = " + eventID + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer iresult = dbLib.executeQuery( "profiledb" , sql );
    if ( ( iresult != null ) && ( iresult.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean updatePingCount( EventBean event ) throws IOException {
    boolean result = false;

    if ( event == null ) {
      DLog.warning( lctx , "Failed to update ping count event "
          + ", found null event object" );
      return result;
    }

    long eventID = event.getEventID();
    if ( eventID < 1 ) {
      DLog.warning( lctx , "Failed to update ping count event "
          + ", found zero eventID" );
      return result;
    }

    String sqlUpdate = "UPDATE event ";
    String sqlSet = "SET ping_count = " + event.getPingCount() + " ";
    String sqlWhere = "WHERE event_id = " + eventID + " ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean update( EventBean eventBean ) throws IOException {
    boolean result = false;

    if ( eventBean == null ) {
      DLog.warning( lctx , "Failed to update event , found null event object" );
      return result;
    }

    long eventID = eventBean.getEventID();
    if ( eventID < 1 ) {
      DLog.warning( lctx , "Failed to update event , found zero eventID" );
      return result;
    }

    EventBean selectEventBean = select( eventBean.getEventName() ,
        eventBean.getClientID() , eventBean.isChannel() );
    if ( ( selectEventBean != null )
        && ( selectEventBean.getEventID() != eventBean.getEventID() ) ) {
      DLog.warning( lctx , "Failed to update event , found event name "
          + "is already exist = " + eventBean.getEventName() );
      throw new IOException( "Event [" + eventBean.getEventName()
          + "] is already exists." );
    }

    // set next 10 years
    Calendar next10years = Calendar.getInstance();
    next10years.add( Calendar.YEAR , 10 );

    // validate datetime
    Date eventStartDate = eventBean.getStartDate();
    eventStartDate = ( eventStartDate == null ) ? next10years.getTime()
        : eventStartDate;
    Date eventEndDate = eventBean.getEndDate();
    eventEndDate = ( eventEndDate == null ) ? next10years.getTime()
        : eventEndDate;
    Date eventRemindDate = eventBean.getRemindDate();
    eventRemindDate = ( eventRemindDate == null ) ? next10years.getTime()
        : eventRemindDate;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "update event set " + "EVENT_NAME='"
        + StringEscapeUtils.escapeSql( eventBean.getEventName() )
        + "',"
        + "CLIENT_ID="
        + eventBean.getClientID()
        + ","
        + "CATAGORY_ID="
        + eventBean.getCatagoryID()
        + ","
        + "START_DATE='"
        + Util.strFormat( eventStartDate , "yyyy-mm-dd hh:nn:ss" )
        + "',"
        + "END_DATE='"
        + Util.strFormat( eventEndDate , "yyyy-mm-dd hh:nn:ss" )
        + "',"
        + "REMIND_DATE='"
        + Util.strFormat( eventRemindDate , "yyyy-mm-dd hh:nn:ss" )
        + "',"
        + "REMIND_FREQ='"
        + eventBean.getRemindFreq()
        + "',"
        + "NUM_CODES="
        + eventBean.getNumCodes()
        + ","
        + "CODE_LENGTH="
        + eventBean.getCodeLength()
        + ","
        + "CODES='"
        + eventBean.getCodes()
        + "',"
        + "COMMENT='"
        + StringEscapeUtils.escapeSql( eventBean.getComment() )
        + "',"
        + "PROCESS='"
        + StringEscapeUtils.escapeSql( eventBean.getProcess() )
        + "',"
        + "PROCESS_TYPE="
        + eventBean.getProcessType()
        + ","
        + "PING_COUNT="
        + eventBean.getPingCount()
        + ","
        + "BUDGET='"
        + eventBean.getBudget()
        + "',"
        + "USED_BUDGET='"
        + eventBean.getUsedBudget()
        + "',"
        + "CHANNEL="
        + ( ( eventBean.getChannel() == true ) ? 1 : 0 )
        + ","
        + "MOBILE_MENU_ENABLE="
        + ( ( eventBean.getMobileMenuEnabled() == true ) ? 1 : 0 )
        + ","
        + "MOBILE_MENU_NAME='"
        + StringEscapeUtils.escapeSql( eventBean.getMobileMenuName() )
        + "',"
        + "OVERBUDGET_DATE='"
        + Util
            .strFormat( eventBean.getOverbudgetDate() , "yyyy-mm-dd hh:nn:ss" )
        + "'," + "UNSUBSCRIBE_IMMEDIATE="
        + ( ( eventBean.getUnsubscribeImmediate() == true ) ? 1 : 0 )
        + ",BIT_FLAGS=" + eventBean.getBitFlags() + ",MOBILE_MENU_BRAND_NAME='"
        + StringEscapeUtils.escapeSql( eventBean.getMobileMenuBrandName() )
        + "',UNSUBSCRIBE_RESPONSE='"
        + StringEscapeUtils.escapeSql( eventBean.getUnsubscribeResponse() )
        + "',OUTGOING_NUMBER='"
        + StringEscapeUtils.escapeSql( eventBean.getOutgoingNumber() )
        + "',CLIENT_EVENT_ID='"
        + StringEscapeUtils.escapeSql( eventBean.getClientEventID() )
        + "',SENDERID='"
        + StringEscapeUtils.escapeSql( eventBean.getSenderID() ) + "',SUSPEND="
        + ( eventBean.isSuspend() ? 1 : 0 ) + ",ACTIVE="
        + ( eventBean.isActive() ? 1 : 0 ) + ",DATE_UPDATED='"
        + DateTimeFormat.convertToString( eventBean.getDateUpdated() )
        + "' where event_id = " + eventID;

    /*--------------------------
      execute query
    --------------------------*/
    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt == null ) || ( irslt.intValue() < 1 ) ) {
      return result;
    }

    result = true;
    return result;
  } // update()

  public void setActive( EventBean _event ) throws IOException {

    long eventID = _event.getEventID();
    if ( eventID < 1 ) {
      DLog.warning( lctx , "Failed to active event , found zero eventId" );
      return;
    }

    String sqlUpdate = "Update event ";
    String sqlSet = "SET active = 1 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( event_id = " + eventID + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // setActive()

  public void setInactive( EventBean _event ) throws IOException {

    long eventID = _event.getEventID();
    if ( eventID < 1 ) {
      DLog.warning( lctx , "Failed to inactive event , found zero eventId" );
      return;
    }

    String sqlUpdate = "Update event ";
    String sqlSet = "SET active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( event_id = " + eventID + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // setInactive()

  public boolean updateSuspend( long eventId , boolean suspend ) {
    boolean result = false;

    int isuspend = suspend ? 1 : 0;

    String sqlUpdate = "Update event ";
    String sqlSet = "SET suspend = " + isuspend + " , date_updated = NOW() ";
    String sqlWhere = "WHERE ( event_id = " + eventId + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public void setHidden( EventBean _event ) throws IOException {

    long eventID = _event.getEventID();
    if ( eventID < 1 ) {
      DLog.warning( lctx , "Failed to hide the event , found zero eventId" );
      return;
    }

    String sqlUpdate = "Update event ";
    String sqlSet = "SET display = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( event_id = " + eventID + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // setInactive()

  public void setShown( EventBean _event ) throws IOException {

    long eventID = _event.getEventID();
    if ( eventID < 1 ) {
      DLog.warning( lctx , "Failed to show the event , found zero eventId" );
      return;
    }

    String sqlUpdate = "Update event ";
    String sqlSet = "SET display = 1 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( event_id = " + eventID + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // setInactive()

  public boolean delete( EventBean eventBean ) throws IOException {
    boolean result = false;

    if ( eventBean == null ) {
      DLog.warning( lctx , "Failed to delete event "
          + ", found null event bean" );
      return result;
    }

    long eventID = eventBean.getEventID();
    if ( eventID < 1 ) {
      DLog.warning( lctx , "Failed to delete event " + ", found zero event id" );
      return result;
    }

    // add suffix delete keyword in the event name
    String eventName = eventBean.getEventName();
    eventName = eventName + " (deleted)";

    String sqlUpdate = "Update event ";
    String sqlSet = "SET event_name = '"
        + StringEscapeUtils.escapeSql( eventName )
        + "' , active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( event_id = " + eventID + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlDelete" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  } // delete()

  private LinkedHashMap populateBeans( String sql ) throws IOException {
    LinkedHashMap eventBeans = new LinkedHashMap();
    if ( StringUtils.isBlank( sql ) ) {
      return eventBeans;
    }
    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        EventBean eventBean = populateBean( rs );
        if ( eventBean == null ) {
          continue;
        }
        eventBeans.put( Long.toString( eventBean.getEventID() ) , eventBean );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }
    return eventBeans;
  }

  private EventBean populateBean( String sql ) throws IOException {
    EventBean eventBean = null;
    if ( StringUtils.isBlank( sql ) ) {
      return eventBean;
    }

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery( sql );
      if ( rs.next() ) {
        eventBean = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return eventBean;
  }

  private EventBean populateBean( ResultSet rs ) throws SQLException {
    EventBean eventBean = new EventBean();

    eventBean.setEventID( (long) rs.getDouble( "event_id" ) );
    eventBean.setEventName( rs.getString( "event_name" ) );
    eventBean.setClientID( (long) rs.getDouble( "client_id" ) );
    eventBean.setCatagoryID( (long) rs.getDouble( "catagory_id" ) );
    eventBean.setStartDate( Util.getUtilDate( rs.getDate( "start_date" ) ,
        rs.getTime( "start_date" ) ) );
    eventBean.setEndDate( Util.getUtilDate( rs.getDate( "end_date" ) ,
        rs.getTime( "end_date" ) ) );
    eventBean.setRemindDate( Util.getUtilDate( rs.getDate( "remind_date" ) ,
        rs.getTime( "remind_date" ) ) );
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
    eventBean.setOverbudgetDate( Util.getUtilDate(
        rs.getDate( "overbudget_date" ) , rs.getTime( "overbudget_date" ) ) );
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
    eventBean.setDateUpdated( new Date() );

    return eventBean;

  }

}
