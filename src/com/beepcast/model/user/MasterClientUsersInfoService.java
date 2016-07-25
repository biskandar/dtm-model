package com.beepcast.model.user;

import java.util.ArrayList;
import java.util.List;

import com.beepcast.model.client.ClientType;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class MasterClientUsersInfoService {

  static final DLogContext lctx = new SimpleContext(
      "MasterClientUsersInfoService" );

  private MasterClientUsersInfoDAO dao;

  public MasterClientUsersInfoService() {
    dao = new MasterClientUsersInfoDAO();
  }

  public List getClientUsersBasedOnMasterClientId( int clientType ,
      int masterClientId ) {
    return getClientUsersBasedOnMasterClientId( clientType , masterClientId ,
        null );
  }

  public List getClientUsersBasedOnMasterClientId( int clientType ,
      int masterClientId , String clientStates ) {
    List list = new ArrayList();

    if ( clientType == ClientType.SUPER ) {
      masterClientId = 0;
    }
    if ( clientType == ClientType.MASTER ) {
      if ( masterClientId < 1 ) {
        DLog.warning( lctx , "Failed to get the list of clients "
            + ", found zero masterClientId" );
        return list;
      }
    }

    list = dao.getClientUsersBasedOnMasterClientId( masterClientId ,
        clientStates );

    return list;
  }

}
