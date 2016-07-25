package com.beepcast.model.client;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientCreditUnitService {

  private static final long serialVersionUID = 2812695542456557789L;

  static final DLogContext lctx = new SimpleContext( "ClientCreditUnitService" );

  private ClientCreditUnitDAO dao;

  public ClientCreditUnitService() {
    dao = new ClientCreditUnitDAO();
  }

  public boolean insert( ClientCreditUnitBean bean ) {
    boolean inserted = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert client credit unit "
          + ", found zero bean" );
      return inserted;
    }
    int clientId = bean.getClientId();
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to insert client credit unit "
          + ", found zero clientId" );
      return inserted;
    }
    double unit = bean.getUnit();
    if ( unit == 0 ) {
      DLog.warning( lctx , "Failed to insert client credit unit "
          + ", found zero unit" );
      return inserted;
    }
    inserted = dao.insert( bean );
    return inserted;
  }

}
