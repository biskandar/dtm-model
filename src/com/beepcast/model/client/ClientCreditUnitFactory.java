package com.beepcast.model.client;

import java.util.Date;

public class ClientCreditUnitFactory {

  public static ClientCreditUnitBean createClientCreditUnitBean( int clientId ,
      double unit , String description ) {
    return createClientCreditUnitBean( null , clientId , null , unit , null ,
        description );
  }

  public static ClientCreditUnitBean createClientCreditUnitBean(
      String transactionId , int clientId , String method , double unit ,
      String status , String description ) {
    ClientCreditUnitBean bean = null;

    Date now = new Date();

    bean = new ClientCreditUnitBean();
    bean.setTransactionId( transactionId );
    bean.setClientId( clientId );
    bean.setMethod( method );
    bean.setUnit( unit );
    bean.setStatus( status );
    bean.setDescription( description );
    bean.setActive( true );
    bean.setDateInserted( now );
    bean.setDateUpdated( now );

    return bean;
  }

}
