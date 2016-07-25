package com.beepcast.model.clientSenderId;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToSenderIdsService {

  static final DLogContext lctx = new SimpleContext( "ClientToSenderIdsService" );

  private ClientToSenderIdsDAO dao;

  public ClientToSenderIdsService() {
    dao = new ClientToSenderIdsDAO();
  }

  public ClientToSenderIdsBean selectBean( String outgoingNumber ,
      String senderId ) {
    return selectBean( outgoingNumber , senderId , 0 , 1000 );
  }

  public ClientToSenderIdsBean selectBean( String outgoingNumber ,
      String senderId , int top , int limit ) {
    ClientToSenderIdsBean bean = new ClientToSenderIdsBean();
    bean.setTotalRecords( dao.totalRecords( 0 , outgoingNumber , senderId ) );
    bean.setListRecords( dao.listRecords( 0 , outgoingNumber , senderId , top ,
        limit ) );
    return bean;
  }

  public ClientToSenderIdsBean selectBean( int clientId ,
      String outgoingNumber , String senderId ) {
    return selectBean( clientId , outgoingNumber , senderId , 0 , 1000 );
  }

  public ClientToSenderIdsBean selectBean( int clientId ,
      String outgoingNumber , String senderId , int top , int limit ) {
    ClientToSenderIdsBean bean = new ClientToSenderIdsBean();
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to select bean , found zero client id" );
      return bean;
    }
    bean.setTotalRecords( dao.totalRecords( clientId , outgoingNumber ,
        senderId ) );
    bean.setListRecords( dao.listRecords( clientId , outgoingNumber , senderId ,
        top , limit ) );
    return bean;
  }

}
