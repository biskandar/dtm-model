package com.beepcast.model.clientSenderId;

import java.util.ArrayList;
import java.util.List;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToSenderIdsCommon {

  static final DLogContext lctx = new SimpleContext( "ClientToSenderIdsCommon" );

  public static List selectBeans( int clientId ) {
    return selectBeans( clientId , "*" , null );
  }

  public static List selectBeans( int clientId , String outgoingNumber ,
      String senderId ) {
    List listBeans = new ArrayList();
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to read bean , found zero client id" );
      return listBeans;
    }
    ClientToSenderIdsService service = new ClientToSenderIdsService();
    ClientToSenderIdsBean bean = service.selectBean( clientId , outgoingNumber ,
        senderId );
    if ( ( bean != null ) && ( bean.getTotalRecords() > 0 ) ) {
      listBeans.addAll( bean.getListRecords() );
    }
    return listBeans;
  }

}
