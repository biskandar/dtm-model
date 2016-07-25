package com.beepcast.model.clientSenderId;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToSenderIdInfosService {

  static final DLogContext lctx = new SimpleContext(
      "ClientToSenderIdInfosService" );

  private ClientToSenderIdInfosDAO dao;

  public ClientToSenderIdInfosService() {
    dao = new ClientToSenderIdInfosDAO();
  }

  public ClientToSenderIdInfosBean selectBean( int clientId ,
      String outgoingNumber , String senderId , int top , int limit ) {
    ClientToSenderIdInfosBean bean = new ClientToSenderIdInfosBean();
    bean.setTotalRecords( dao.totalRecords( clientId , outgoingNumber ,
        senderId ) );
    bean.setListRecords( dao.listRecords( clientId , outgoingNumber , senderId ,
        top , limit ) );
    return bean;
  }

}
