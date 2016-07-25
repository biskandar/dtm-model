package com.beepcast.model.webTransaction;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class WebTransactionDAO {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "WebTransactionDAO" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private DatabaseLibrary dbLib;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public WebTransactionDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insert( WebTransactionBean bean ) {
    boolean result = false;

    // load parameters

    String messageId = bean.getMessageId();
    int eventId = bean.getEventId();
    String mobileNumber = bean.getMobileNumber();
    double debitAmount = bean.getDebitAmount();
    String browserAddress = bean.getBrowserAddress();
    String browserAgent = bean.getBrowserAgent();
    String messageRequest = bean.getMessageRequest();
    String messageResponse = bean.getMessageResponse();
    String statusCode = bean.getStatusCode();
    String statusDescription = bean.getStatusDescription();

    // compose sql

    String sqlInsert = "INSERT INTO web_transaction ( message_id "
        + ", event_id , mobile_number , debit_amount , browser_address "
        + ", browser_agent , message_request , message_response "
        + ", status_code , status_description , date_inserted ) ";

    String sqlValues = "VALUES( '" + StringEscapeUtils.escapeSql( messageId )
        + "'," + eventId + ",'" + StringEscapeUtils.escapeSql( mobileNumber )
        + "'," + debitAmount + ",'"
        + StringEscapeUtils.escapeSql( browserAddress ) + "','"
        + StringEscapeUtils.escapeSql( browserAgent ) + "','"
        + StringEscapeUtils.escapeSql( messageRequest ) + "','"
        + StringEscapeUtils.escapeSql( messageResponse ) + "','"
        + StringEscapeUtils.escapeSql( statusCode ) + "','"
        + StringEscapeUtils.escapeSql( statusDescription ) + "',NOW() ) ";

    String sql = sqlInsert + sqlValues;

    // log it
    DLog.debug( lctx , "Perform " + sql );

    // execute sql
    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  // ...

}
