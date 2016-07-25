package com.beepcast.model.outgoingNumberToProvider;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class OutgoingNumberToProviderService {

  static final DLogContext lctx = new SimpleContext(
      "OutgoingNumberToProviderService" );

  private OutgoingNumberToProviderDAO dao;

  public OutgoingNumberToProviderService() {
    dao = new OutgoingNumberToProviderDAO();
  }

  public boolean insert( OutgoingNumberToProviderBean bean ) {
    boolean inserted = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert , found null bean" );
      return inserted;
    }
    inserted = dao.insert( bean );
    return inserted;
  }

  public OutgoingNumberToProviderBean selectById( int id ) {
    OutgoingNumberToProviderBean bean = null;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to select by id , found zero id" );
      return bean;
    }
    bean = dao.selectById( id );
    return bean;
  }

  public boolean update( OutgoingNumberToProviderBean bean ) {
    boolean updated = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to update , found null bean" );
      return updated;
    }
    updated = dao.update( bean );
    return updated;
  }

  public boolean updateSuspendById( int id , boolean suspend ) {
    boolean deleted = false;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to update suspend by id , found zero id" );
      return deleted;
    }
    deleted = dao.updateSuspendById( id , suspend );
    return deleted;
  }

  public boolean deleteById( int id ) {
    boolean deleted = false;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to delete by id , found zero id" );
      return deleted;
    }
    deleted = dao.deleteById( id );
    return deleted;
  }

}
