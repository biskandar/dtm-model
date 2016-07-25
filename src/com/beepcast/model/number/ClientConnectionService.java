package com.beepcast.model.number;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientConnectionService {

  static final DLogContext lctx = new SimpleContext( "ClientConnectionService" );

  private ClientConnectionDAO dao;

  public ClientConnectionService() {
    dao = new ClientConnectionDAO();
  }

  public boolean insertBean( ClientConnectionBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert bean "
          + ", found null client connection bean" );
      return result;
    }
    result = dao.insertBean( bean );
    return result;
  }

  public ClientConnectionBean selectBean( int groupClientConnectionId ,
      String number ) {
    ClientConnectionBean bean = null;
    if ( groupClientConnectionId < 1 ) {
      DLog.warning( lctx , "Failed to select bean "
          + ", found zero group client connection id" );
      return bean;
    }
    if ( StringUtils.isBlank( number ) ) {
      DLog.warning( lctx , "Failed to select bean "
          + ", found blank number param" );
      return bean;
    }
    bean = dao.selectBean( groupClientConnectionId , number );
    return bean;
  }

  public boolean deleteBean( ClientConnectionBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to delete bean "
          + ", found null client connection bean" );
      return result;
    }
    result = dao.deleteBean( bean );
    return result;
  }

  public List listActiveBeans( int groupClientConnectionId ) {
    List list = null;
    if ( groupClientConnectionId < 1 ) {
      DLog.warning( lctx , "Failed to list active beans "
          + ", found zero group client connection id" );
      return list;
    }
    list = dao.listBeans( groupClientConnectionId , true );
    return list;
  }

}
