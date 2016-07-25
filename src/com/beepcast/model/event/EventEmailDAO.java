package com.beepcast.model.event;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EventEmailDAO {

  static final DLogContext lctx = new SimpleContext( "EventEmailDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public EventEmailDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public boolean insert( EventEmailBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }

    // read params
    int eventId = bean.getEventId();
    int processStep = bean.getProcessStep();
    String emailClob = bean.getEmailClob();

    // clean params
    emailClob = ( emailClob == null ) ? "" : emailClob;

    // compose sql
    String sqlInsert = "INSERT INTO event_email ( event_id ";
    sqlInsert += ", process_step , email_clob , date_inserted ";
    sqlInsert += ", date_updated ) ";
    String sqlValues = "VALUES ( " + eventId + " , " + processStep + " , '"
        + StringEscapeUtils.escapeSql( emailClob ) + "' , NOW() , NOW() ) ";
    String sql = sqlInsert + sqlValues;

    // execute sql
    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean updateEmailClob( EventEmailBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }

    // read params
    long eventId = bean.getEventId();
    int processStep = bean.getProcessStep();
    String emailClob = bean.getEmailClob();

    // clean params
    emailClob = ( emailClob == null ) ? "" : emailClob;

    // compose sql
    String sqlUpdate = "UPDATE event_email ";
    String sqlSet = "SET date_updated = NOW() ";
    sqlSet += ", email_clob = '" + StringEscapeUtils.escapeSql( emailClob )
        + "' ";
    String sqlWhere = "WHERE ( event_id = " + eventId + " ) ";
    sqlWhere += "AND ( process_step = " + processStep + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    // execute sql
    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean delete( EventEmailBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }

    // read params
    long eventId = bean.getEventId();
    int processStep = bean.getProcessStep();

    // compose sql
    String sqlDelete = "DELETE FROM event_email ";
    String sqlWhere = "WHERE ( event_id = " + eventId + " ) ";
    sqlWhere += "AND ( process_step = " + processStep + " ) ";
    String sql = sqlDelete + sqlWhere;

    // execute sql
    if ( opropsApp.getBoolean( "Model.DebugAllSqlDelete" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public EventEmailBean select( long eventId , int processStep ) {
    EventEmailBean bean = null;
    if ( eventId < 1 ) {
      return bean;
    }

    // compose sql
    String sqlSelectFrom = EventEmailQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( event_id = " + eventId + " ) ";
    sqlWhere += "AND ( process_step = " + processStep + " ) ";
    String sqlOrder = "ORDER BY id ASC ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    // execute sql
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }

    // populate record
    Iterator it = qr.iterator();
    if ( it.hasNext() ) {
      bean = EventEmailQuery.populateRecord( (QueryItem) it.next() );
    }

    return bean;
  }

}
