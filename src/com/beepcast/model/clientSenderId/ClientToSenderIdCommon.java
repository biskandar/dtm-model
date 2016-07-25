package com.beepcast.model.clientSenderId;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToSenderIdCommon {

  static final DLogContext lctx = new SimpleContext( "ClientToSenderIdCommon" );

  public static ClientToSenderIdBean selectBean( int clientId ,
      String outgoingNumber , String senderId ) {
    ClientToSenderIdBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to read bean , found zero client id" );
      return bean;
    }
    ClientToSenderIdService service = new ClientToSenderIdService();
    bean = service.selectBean( clientId , outgoingNumber , senderId );
    return bean;
  }

}
