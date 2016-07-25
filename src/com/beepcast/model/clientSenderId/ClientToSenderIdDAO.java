package com.beepcast.model.clientSenderId;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToSenderIdDAO {

  static final DLogContext lctx = new SimpleContext( "ClientToSenderIdDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public ClientToSenderIdDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public boolean insertBean( ClientToSenderIdBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }

    // read params
    String outgoingNumber = bean.getOutgoingNumber();
    String senderId = bean.getSenderId();
    String description = bean.getDescription();

    // clean params
    outgoingNumber = ( outgoingNumber == null ) ? "" : outgoingNumber.trim();
    senderId = ( senderId == null ) ? "" : senderId.trim();
    description = ( description == null ) ? "" : description.trim();

    // compose sql
    String sqlInsert = "INSERT INTO client_to_senderid ( client_id ";
    sqlInsert += ", outgoing_number , senderID , description ";
    sqlInsert += ", active , date_inserted , date_updated ) ";
    String sqlValues = "VALUES ( " + bean.getClientId() + " , '"
        + StringEscapeUtils.escapeSql( outgoingNumber ) + "' , '"
        + StringEscapeUtils.escapeSql( senderId ) + "' , '"
        + StringEscapeUtils.escapeSql( description )
        + "' , 1 , NOW() , NOW() ) ";
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

  public ClientToSenderIdBean selectBean( int clientId , String outgoingNumber ,
      String senderId ) {
    ClientToSenderIdBean bean = null;

    // populate sql
    String sqlSelectFrom = ClientToSenderIdQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";
    if ( outgoingNumber != null ) {
      sqlWhere += "AND ( outgoing_number = '"
          + StringEscapeUtils.escapeSql( outgoingNumber ) + "' ) ";
    }
    if ( senderId != null ) {
      sqlWhere += "AND ( senderID = '" + StringEscapeUtils.escapeSql( senderId )
          + "' ) ";
    }
    String sqlOrder = "ORDER BY id ASC ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    // populate record
    bean = selectBean( sql );
    return bean;
  }

  public ClientToSenderIdBean selectBean( int clientToSenderIdId ) {
    ClientToSenderIdBean bean = null;

    // populate sql
    String sqlSelectFrom = ClientToSenderIdQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( id = " + clientToSenderIdId + " ) ";
    String sql = sqlSelectFrom + sqlWhere;

    // populate record
    bean = selectBean( sql );
    return bean;
  }

  public boolean updateProfile( ClientToSenderIdBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }

    // read params
    String outgoingNumber = bean.getOutgoingNumber();
    String senderId = bean.getSenderId();
    String description = bean.getDescription();

    // clean params
    outgoingNumber = ( outgoingNumber == null ) ? "" : outgoingNumber.trim();
    senderId = ( senderId == null ) ? "" : senderId.trim();
    description = ( description == null ) ? "" : description.trim();

    // compose sql
    String sqlUpdate = "UPDATE client_to_senderid ";
    String sqlSet = "SET outgoing_number = '"
        + StringEscapeUtils.escapeSql( outgoingNumber ) + "' ";
    sqlSet += ", senderID = '" + StringEscapeUtils.escapeSql( senderId ) + "' ";
    sqlSet += ", description = '" + StringEscapeUtils.escapeSql( description )
        + "' ";
    sqlSet += ", date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + bean.getId() + " ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    // execute sql
    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer iresult = dbLib.executeQuery( "profiledb" , sql );
    if ( ( iresult != null ) && ( iresult.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean updateInActive( int id ) {
    boolean result = false;

    // compose sql
    String sqlUpdate = "UPDATE client_to_senderid ";
    String sqlSet = "SET active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

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

  private ClientToSenderIdBean selectBean( String sql ) {
    ClientToSenderIdBean bean = null;

    // execute sql
    // DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }
    Iterator it = qr.iterator();
    if ( !it.hasNext() ) {
      return bean;
    }

    // populate record
    bean = ClientToSenderIdQuery.populateRecord( (QueryItem) it.next() );
    return bean;
  }

}
