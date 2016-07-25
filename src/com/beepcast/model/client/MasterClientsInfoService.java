package com.beepcast.model.client;

import java.util.ArrayList;
import java.util.List;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class MasterClientsInfoService {

  static final DLogContext lctx = new SimpleContext( "MasterClientsInfoService" );

  private MasterClientsInfoDAO dao;

  public MasterClientsInfoService() {
    dao = new MasterClientsInfoDAO();
  }

  public List getClientsBasedOnMasterClientId( int clientType ,
      int masterClientId ) {
    return getClientsBasedOnMasterClientId( clientType , masterClientId , null );
  }

  public List getClientsBasedOnMasterClientId( int clientType ,
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

    list = dao.getClientsBasedOnMasterClientId( masterClientId , clientStates );

    return list;
  }

}
