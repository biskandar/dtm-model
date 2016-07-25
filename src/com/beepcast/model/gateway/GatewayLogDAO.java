package com.beepcast.model.gateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.encrypt.EncryptApp;
import com.beepcast.model.util.DateTimeFormat;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GatewayLogDAO {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "GatewayLogDAO" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  private final String keyPhoneNumber;
  private final String keyMessage;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public GatewayLogDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();

    EncryptApp encryptApp = EncryptApp.getInstance();
    keyPhoneNumber = encryptApp.getKeyValue( EncryptApp.KEYNAME_PHONENUMBER );
    keyMessage = encryptApp.getKeyValue( EncryptApp.KEYNAME_MESSAGE );
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insertIncomingMessage( String messageId , String messageType ,
      int messageCount , int eventId , String provider , double debitAmount ,
      Date dateTm , String phoneNumber , int phoneCountryId ,
      String destinationNumber , String messageContent , String messageStatus ) {
    boolean result = false;

    dateTm = ( dateTm == null ) ? new Date() : dateTm;
    String strDateTm = DateTimeFormat.convertToString( dateTm );
    String senderId = "";

    // compose sql
    String sqlInsert = "INSERT INTO gateway_log ( "
        + "status , external_status , message_id , event_id , provider , "
        + "date_tm , mode , phone , encrypt_phone , phone_country_id , "
        + "message_type , message_count , message , encrypt_message , "
        + "debit_amount , short_code , encrypt_short_code "
        + ", senderID , encrypt_senderID ) ";
    String sqlValues = "VALUES( '"
        + StringEscapeUtils.escapeSql( messageStatus ) + "','','" + messageId
        + "'," + eventId + ",'" + StringEscapeUtils.escapeSql( provider )
        + "','" + strDateTm + "','RECV','',"
        + sqlEncryptPhoneNumber( phoneNumber ) + "," + phoneCountryId + ",'"
        + messageType + "'," + messageCount + ",'',"
        + sqlEncryptMessage( messageContent ) + "," + debitAmount + ",'',"
        + sqlEncryptShortCode( destinationNumber ) + ",'',"
        + sqlEncryptSenderId( senderId ) + " ) ";
    String sql = sqlInsert + sqlValues;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql
    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean insertOutgoingMessage( String messageId , String messageType ,
      int messageCount , int eventId , int channelSessionId , String provider ,
      double debitAmount , Date dateTm , String phoneNumber ,
      int phoneCountryId , String originalNumber , String messageContent ,
      String messageStatus ) {
    boolean result = false;

    dateTm = ( dateTm == null ) ? new Date() : dateTm;
    String strDateTm = DateTimeFormat.convertToString( dateTm );
    String senderId = "";

    // compose sql
    String sqlInsert = "INSERT INTO gateway_log ( "
        + "status , external_status , message_id , event_id , "
        + "channel_session_id , provider , date_tm , mode , phone , "
        + "encrypt_phone , phone_country_id , message_type , message_count , "
        + "message , encrypt_message , debit_amount , short_code , "
        + "encrypt_short_code , senderID , encrypt_senderID ) ";
    String sqlValues = "VALUES( '"
        + StringEscapeUtils.escapeSql( messageStatus ) + "','','" + messageId
        + "'," + eventId + "," + channelSessionId + ",'"
        + StringEscapeUtils.escapeSql( provider ) + "','" + strDateTm
        + "','SEND',''," + sqlEncryptPhoneNumber( phoneNumber ) + ","
        + phoneCountryId + ",'" + messageType + "'," + messageCount + ",'',"
        + sqlEncryptMessage( messageContent ) + "," + debitAmount + ",'',"
        + sqlEncryptShortCode( originalNumber ) + ",'',"
        + sqlEncryptSenderId( senderId ) + " ) ";
    String sql = sqlInsert + sqlValues;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql
    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean insert( GatewayLogBean bean ) {
    boolean result = false;

    if ( bean == null ) {
      return result;
    }

    // compose sql

    String sqlInsert = "INSERT INTO gateway_log ";
    sqlInsert += "(status,external_status,message_id,external_message_id";
    sqlInsert += ",event_id,channel_session_id,provider,date_tm,status_date_tm";
    sqlInsert += ",mode,retry,phone,encrypt_phone,phone_country_id";
    sqlInsert += ",message_type,message_count,message,encrypt_message";
    sqlInsert += ",debit_amount,short_code,encrypt_short_code,senderID";
    sqlInsert += ",encrypt_senderID) ";

    String sqlValues = "VALUES ('"
        + StringEscapeUtils.escapeSql( bean.getStatus() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getExternalStatus() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getMessageID() ) + "','',"
        + bean.getEventID() + "," + bean.getChannelSessionID() + ",'"
        + StringEscapeUtils.escapeSql( bean.getProvider() ) + "','"
        + DateTimeFormat.convertToString( bean.getDateTm() ) + "',NULL,'"
        + StringEscapeUtils.escapeSql( bean.getMode() ) + "',"
        + bean.getRetry() + ",''," + sqlEncryptPhoneNumber( bean.getPhone() )
        + "," + bean.getPhoneCountryId() + ",'"
        + StringEscapeUtils.escapeSql( bean.getMessageType() ) + "',"
        + bean.getMessageCount() + ",'',"
        + sqlEncryptMessage( bean.getMessage() ) + "," + bean.getDebitAmount()
        + ",''," + sqlEncryptShortCode( bean.getShortCode() ) + ",'',"
        + sqlEncryptSenderId( bean.getSenderID() ) + ") ";

    String sql = sqlInsert + sqlValues;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute query
    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  } // insert()

  public GatewayLogBean selectByProviderAndExtMsgId( String providerId ,
      String externalMessageId ) {
    GatewayLogBean glBean = null;

    if ( providerId == null ) {
      return glBean;
    }
    if ( externalMessageId == null ) {
      return glBean;
    }

    // compose sql

    String sqlSelectFrom = sqlSelectFrom();
    String sqlWhere = "WHERE ( external_message_id = '"
        + StringEscapeUtils.escapeSql( externalMessageId ) + "' ) ";
    sqlWhere += "AND ( provider = '" + StringEscapeUtils.escapeSql( providerId )
        + "' ) ";
    String sqlOrder = "ORDER BY log_id DESC ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    // execute and populate bean

    ConnectionWrapper conn = dbLib.getReaderConnection( "transactiondb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      if ( rs.next() ) {
        glBean = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      DLog.warning( lctx , "Failed to perform query , " + sqle );
    } finally {
      conn.disconnect( true );
    }

    return glBean;
  }

  public GatewayLogBean[] select( String whereCriteria ) {
    GatewayLogBean[] glBeans = null;

    // compose sql
    String sqlSelectFrom = sqlSelectFrom();
    String sqlWhere = "";
    if ( !StringUtils.isBlank( whereCriteria ) ) {
      sqlWhere = "WHERE " + whereCriteria + " ";
    }
    String sqlOrder = "ORDER BY date_tm ASC ";
    String sqlLimit = "LIMIT 1000 ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    // execute sql and populate records
    Vector vRecords = new Vector( 10 );
    ConnectionWrapper conn = dbLib.getReaderConnection( "transactiondb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        GatewayLogBean logRecord = populateBean( rs );
        vRecords.addElement( logRecord );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      DLog.warning( lctx , "Failed to perform query , " + sqle );
    } finally {
      conn.disconnect( true );
    }

    // return in array
    glBeans = new GatewayLogBean[vRecords.size()];
    for ( int i = 0 ; i < glBeans.length ; i++ ) {
      glBeans[i] = (GatewayLogBean) vRecords.elementAt( i );
    }

    return glBeans;
  } // select(string)

  public long getMaxLogID() {
    long logId = 0;

    // compose sql
    String sqlSelect = "SELECT MAX(log_id) ";
    String sqlFrom = "FROM gateway_log ";
    String sql = sqlSelect + sqlFrom;

    // populate record
    try {
      logId = Long.parseLong( populateFirstRowField( sql ) );
    } catch ( Exception e ) {
    }
    return logId;
  }

  public boolean update( GatewayLogBean glBean ) {
    boolean result = false;
    if ( glBean == null ) {
      return result;
    }

    // clean params
    Date dateTm = glBean.getDateTm();
    if ( dateTm == null ) {
      dateTm = new Date();
    }

    // compose sql
    String sqlUpdate = "UPDATE gateway_log ";
    String sqlSet = "SET provider = '"
        + StringEscapeUtils.escapeSql( glBean.getProvider() )
        + "' , short_code = '"
        + StringEscapeUtils.escapeSql( glBean.getShortCode() )
        + "' , encrypt_short_code = "
        + sqlEncryptShortCode( glBean.getShortCode() ) + " , senderID = '"
        + StringEscapeUtils.escapeSql( glBean.getSenderID() )
        + "' , encrypt_senderID = " + sqlEncryptSenderId( glBean.getSenderID() )
        + " , date_tm = '" + DateTimeFormat.convertToString( dateTm )
        + "' , mode = '" + StringEscapeUtils.escapeSql( glBean.getMode() )
        + "' , phone = '' , encrypt_phone = "
        + sqlEncryptPhoneNumber( glBean.getPhone() ) + " , phone_country_id = "
        + glBean.getPhoneCountryId() + " , message = '' , encrypt_message = "
        + sqlEncryptMessage( glBean.getMessage() ) + " , event_id ="
        + glBean.getEventID() + ", message_id = '"
        + StringEscapeUtils.escapeSql( glBean.getMessageID() )
        + "' , status = '" + StringEscapeUtils.escapeSql( glBean.getStatus() )
        + "' ";
    String sqlWhere = "WHERE ( log_id = " + glBean.getLogID() + " ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean update( String setCriteria , String whereCriteria ) {
    boolean result = false;

    // compose sql
    String sqlUpdate = "UPDATE gateway_log ";
    String sqlSet = "SET " + setCriteria + " ";
    String sqlWhere = "WHERE " + whereCriteria + " ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean delete( GatewayLogBean logRecord ) {
    boolean result = false;

    long logID = logRecord.getLogID();

    // compose sql
    String sqlDelete = "DELETE FROM gateway_log ";
    String sqlWhere = "WHERE log_id = " + logID + " ";
    String sql = sqlDelete + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlDelete" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public int totalGatewayXipmeRecordsByChannelSessionId( int channelSessionId ,
      String xipmeMasterCode ) {
    int totalRecords = 0;
    if ( channelSessionId < 1 ) {
      return totalRecords;
    }

    // compose sql
    String sqlSelect = "SELECT COUNT( gx.id ) AS total ";
    String sqlFrom = "FROM gateway_log gl ";
    sqlFrom += "INNER JOIN gateway_xipme gx ON gx.gateway_xipme_id = gl.gateway_xipme_id ";
    String sqlWhere = "WHERE ( gl.channel_session_id = " + channelSessionId
        + " ) ";
    if ( !StringUtils.isBlank( xipmeMasterCode ) ) {
      sqlWhere += "AND ( gx.xipme_master_code = '"
          + StringEscapeUtils.escapeSql( xipmeMasterCode ) + "' ) ";
    }
    String sql = sqlSelect + sqlFrom + sqlWhere;

    // populate record
    try {
      totalRecords = Integer.parseInt( populateFirstRowField( sql ) );
    } catch ( Exception e ) {
    }

    return totalRecords;
  }

  public int totalGatewayXipmeHitRecordsByChannelSessionId(
      int channelSessionId , String xipmeMasterCode ) {
    int totalRecords = 0;
    if ( channelSessionId < 1 ) {
      return totalRecords;
    }

    // compose sql
    String sqlSelect = "SELECT COUNT( gxh.id ) AS total ";
    String sqlFrom = "FROM gateway_log gl ";
    sqlFrom += "INNER JOIN gateway_xipme_hit gxh ON gxh.gateway_xipme_id = gl.gateway_xipme_id ";
    String sqlWhere = "WHERE ( gl.channel_session_id = " + channelSessionId
        + " ) ";
    if ( !StringUtils.isBlank( xipmeMasterCode ) ) {
      sqlWhere += "AND ( gxh.xipme_master_code = '"
          + StringEscapeUtils.escapeSql( xipmeMasterCode ) + "' ) ";
    }
    String sql = sqlSelect + sqlFrom + sqlWhere;

    // populate record
    try {
      totalRecords = Integer.parseInt( populateFirstRowField( sql ) );
    } catch ( Exception e ) {
    }

    return totalRecords;
  }

  public int totalGatewayXipmeVisitRecordsByChannelSessionId(
      int channelSessionId , String xipmeMasterCode ) {
    int totalRecords = 0;
    if ( channelSessionId < 1 ) {
      return totalRecords;
    }

    // compose sql
    String sqlSelect = "SELECT COUNT( DISTINCT( gxh.visit_id ) ) AS total ";
    String sqlFrom = "FROM gateway_log gl ";
    sqlFrom += "INNER JOIN gateway_xipme_hit gxh ON gxh.gateway_xipme_id = gl.gateway_xipme_id ";
    String sqlWhere = "WHERE ( gl.channel_session_id = " + channelSessionId
        + " ) ";
    if ( !StringUtils.isBlank( xipmeMasterCode ) ) {
      sqlWhere += "AND ( gxh.xipme_master_code = '"
          + StringEscapeUtils.escapeSql( xipmeMasterCode ) + "' ) ";
    }
    sqlWhere += "AND ( gxh.visit_id IS NOT NULL ) ";
    String sql = sqlSelect + sqlFrom + sqlWhere;

    // populate record
    try {
      totalRecords = Integer.parseInt( populateFirstRowField( sql ) );
    } catch ( Exception e ) {
    }

    return totalRecords;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  private String populateFirstRowField( String sql ) {
    String str = null;

    // execute sql
    QueryResult qr = dbLib.simpleQuery( "transactiondb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return str;
    }

    // populate record
    Iterator it = qr.iterator();
    if ( !it.hasNext() ) {
      return str;
    }
    QueryItem qi = (QueryItem) it.next();
    if ( qi == null ) {
      return str;
    }
    str = qi.getFirstValue();

    return str;
  }

  private String sqlSelectFrom() {
    String sqlSelect = "SELECT log_id , event_id , channel_session_id , provider , mode ";
    sqlSelect += ", " + sqlDecryptPhoneNumber() + " ";
    sqlSelect += ", " + sqlDecryptShortCode() + " ";
    sqlSelect += ", " + sqlDecryptSenderId() + " ";
    sqlSelect += ", phone_country_id , message_type , message_count ";
    sqlSelect += ", " + sqlDecryptMessage() + " ";
    sqlSelect += ", debit_amount , message_id , external_message_id , status , external_status ";
    sqlSelect += ", retry , date_tm , status_date_tm ";
    String sqlFrom = "FROM gateway_log ";
    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  private GatewayLogBean populateBean( ResultSet rs ) throws SQLException {
    GatewayLogBean logRecord = new GatewayLogBean();

    logRecord.setLogID( (long) rs.getDouble( "log_id" ) );
    logRecord.setEventID( (long) rs.getDouble( "event_id" ) );
    logRecord.setChannelSessionID( (long) rs.getDouble( "channel_session_id" ) );
    logRecord.setProvider( rs.getString( "provider" ) );
    logRecord.setMode( rs.getString( "mode" ) );
    logRecord.setPhone( rs.getString( "phone" ) );
    logRecord.setShortCode( rs.getString( "short_code" ) );
    logRecord.setSenderID( rs.getString( "senderID" ) );
    logRecord.setPhoneCountryId( rs.getInt( "phone_country_id" ) );
    logRecord.setMessageType( rs.getString( "message_type" ) );
    logRecord.setMessageCount( rs.getString( "message_count" ) );
    logRecord.setMessage( rs.getString( "message" ) );
    logRecord.setDebitAmount( rs.getDouble( "debit_amount" ) );
    logRecord.setMessageID( rs.getString( "message_id" ) );
    logRecord.setExternalMessageID( rs.getString( "external_message_id" ) );
    logRecord.setStatus( rs.getString( "status" ) );
    logRecord.setExternalStatus( rs.getString( "external_status" ) );
    logRecord.setRetry( rs.getInt( "retry" ) );
    logRecord
        .setDateTm( DateTimeFormat.convertToDate( rs.getString( "date_tm" ) ) );
    logRecord.setStatusDateTm( DateTimeFormat.convertToDate( rs
        .getString( "status_date_tm" ) ) );

    return logRecord;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Util Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  private String sqlEncryptPhoneNumber( String phoneNumber ) {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_ENCRYPT('" );
    sb.append( phoneNumber );
    sb.append( "','" );
    sb.append( keyPhoneNumber );
    sb.append( "')" );
    return sb.toString();
  }

  private String sqlDecryptPhoneNumber() {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_DECRYPT(encrypt_phone,'" );
    sb.append( keyPhoneNumber );
    sb.append( "') AS phone" );
    return sb.toString();
  }

  private String sqlEncryptShortCode( String shortCode ) {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_ENCRYPT('" );
    sb.append( shortCode );
    sb.append( "','" );
    sb.append( keyPhoneNumber );
    sb.append( "')" );
    return sb.toString();
  }

  private String sqlDecryptShortCode() {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_DECRYPT(encrypt_short_code,'" );
    sb.append( keyPhoneNumber );
    sb.append( "') AS short_code" );
    return sb.toString();
  }

  private String sqlEncryptSenderId( String senderId ) {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_ENCRYPT('" );
    sb.append( senderId );
    sb.append( "','" );
    sb.append( keyPhoneNumber );
    sb.append( "')" );
    return sb.toString();
  }

  private String sqlDecryptSenderId() {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_DECRYPT(encrypt_senderID,'" );
    sb.append( keyPhoneNumber );
    sb.append( "') AS senderID" );
    return sb.toString();
  }

  private String sqlEncryptMessage( String message ) {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_ENCRYPT('" );
    sb.append( StringEscapeUtils.escapeSql( message ) );
    sb.append( "','" );
    sb.append( keyMessage );
    sb.append( "')" );
    return sb.toString();
  }

  private String sqlDecryptMessage() {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_DECRYPT(encrypt_message,'" );
    sb.append( keyMessage );
    sb.append( "') AS message" );
    return sb.toString();
  }

}
