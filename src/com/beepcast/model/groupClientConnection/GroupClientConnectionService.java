package com.beepcast.model.groupClientConnection;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GroupClientConnectionService {

  static final DLogContext lctx = new SimpleContext(
      "GroupClientConnectionService" );

  private GroupClientConnectionDAO dao;

  public GroupClientConnectionService() {
    dao = new GroupClientConnectionDAO();
  }

  public boolean insert( GroupClientConnectionBean bean ) {
    boolean inserted = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert , found null bean" );
      return inserted;
    }
    inserted = dao.insert( bean );
    return inserted;
  }

  public GroupClientConnectionBean selectById( int id ) {
    GroupClientConnectionBean bean = null;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to select by id , found zero id" );
      return bean;
    }
    bean = dao.selectById( id );
    return bean;
  }

  public boolean update( GroupClientConnectionBean bean ) {
    boolean updated = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to update , found null bean" );
      return updated;
    }
    updated = dao.update( bean );
    return updated;
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
