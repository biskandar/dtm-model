package com.beepcast.model.client;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToDedicatedModemsService {

  static final DLogContext lctx = new SimpleContext(
      "ClientToDedicatedModemsService" );

  private ClientToDedicatedModemsDAO dao;

  public ClientToDedicatedModemsService() {
    dao = new ClientToDedicatedModemsDAO();
  }

  public ClientToDedicatedModemsBean generateBean( int clientId ) {
    ClientToDedicatedModemsBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to list dedicated modems "
          + ", found zero client id" );
      return bean;
    }
    bean = dao.generateBean( clientId );
    return bean;
  }

}
