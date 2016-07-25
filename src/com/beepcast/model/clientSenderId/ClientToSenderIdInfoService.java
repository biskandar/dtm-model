package com.beepcast.model.clientSenderId;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToSenderIdInfoService {

  static final DLogContext lctx = new SimpleContext(
      "ClientToSenderIdInfosService" );

  private ClientToSenderIdInfoDAO dao;

  public ClientToSenderIdInfoService() {
    dao = new ClientToSenderIdInfoDAO();
  }

  public ClientToSenderIdInfoBean selectBean( int clientToSenderIdId ) {
    ClientToSenderIdInfoBean bean = null;
    if ( clientToSenderIdId < 1 ) {
      DLog.warning( lctx , "Failed to select bean "
          + ", found zero client to sender id id" );
      return bean;
    }
    bean = dao.selectBean( clientToSenderIdId );
    return bean;
  }

}
