package com.beepcast.model.webTransaction;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class WebTransactionService {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "WebTransactionService" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private WebTransactionDAO dao;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public WebTransactionService() {
    dao = new WebTransactionDAO();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insert( WebTransactionBean bean ) {
    boolean result = false;

    // validate must be params

    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert web transaction bean "
          + ", found null bean" );
      return result;
    }
    if ( StringUtils.isBlank( bean.getMessageId() ) ) {
      DLog.warning( lctx , "Failed to insert web transaction bean "
          + ", found blank message id" );
      return result;
    }
    if ( bean.getEventId() < 1 ) {
      DLog.warning( lctx , "Failed to insert web transaction bean "
          + ", found zero event id" );
      return result;
    }
    if ( StringUtils.isBlank( bean.getMobileNumber() ) ) {
      DLog.warning( lctx , "Failed to insert web transaction bean "
          + ", found blank mobile number" );
      return result;
    }

    // clean params

    String browserAddress = bean.getBrowserAddress();
    browserAddress = StringUtils.trimToEmpty( browserAddress );
    bean.setBrowserAddress( browserAddress );

    String browserAgent = bean.getBrowserAgent();
    browserAgent = StringUtils.trimToEmpty( browserAgent );
    bean.setBrowserAgent( browserAgent );

    String messageRequest = bean.getMessageRequest();
    messageRequest = StringUtils.trimToEmpty( messageRequest );
    bean.setMessageRequest( messageRequest );

    String messageResponse = bean.getMessageResponse();
    messageResponse = StringUtils.trimToEmpty( messageResponse );
    bean.setMessageResponse( messageResponse );

    String statusCode = bean.getStatusCode();
    statusCode = StringUtils.trimToEmpty( statusCode );
    bean.setStatusCode( statusCode );

    String statusDescription = bean.getStatusDescription();
    statusDescription = StringUtils.trimToEmpty( statusDescription );
    bean.setStatusDescription( statusDescription );

    // insert bean into table

    result = dao.insert( bean );

    return result;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  // ...

}
