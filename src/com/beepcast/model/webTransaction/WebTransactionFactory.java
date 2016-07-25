package com.beepcast.model.webTransaction;

public class WebTransactionFactory {

  public static WebTransactionBean createWebTransactionBean( String messageId ,
      int eventId , String mobileNumber , double debitAmount ,
      String browserAddress , String browserAgent , String messageRequest ,
      String messageResponse , String statusCode , String statusDescription ) {
    WebTransactionBean bean = new WebTransactionBean();
    bean.setMessageId( messageId );
    bean.setEventId( eventId );
    bean.setMobileNumber( mobileNumber );
    bean.setDebitAmount( debitAmount );
    bean.setBrowserAddress( browserAddress );
    bean.setBrowserAgent( browserAgent );
    bean.setMessageRequest( messageRequest );
    bean.setMessageResponse( messageResponse );
    bean.setStatusCode( statusCode );
    bean.setStatusDescription( statusDescription );
    return bean;
  }

}
