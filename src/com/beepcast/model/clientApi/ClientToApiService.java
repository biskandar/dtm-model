package com.beepcast.model.clientApi;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToApiService {

  static final DLogContext lctx = new SimpleContext( "ClientApiService" );

  private ClientToApiDAO dao;

  public ClientToApiService() {
    dao = new ClientToApiDAO();
  }

  public boolean insert( ClientToApiBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }
    if ( !bean.isActive() ) {
      return result;
    }
    result = dao.insert( bean.getClientId() , bean.getClientApi() );
    return result;
  }

  public boolean update( ClientToApiBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }
    result = dao.update( bean.getId() , bean.getClientId() ,
        bean.getClientApi() );
    return result;
  }

  public boolean delete( ClientToApiBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }
    result = dao.delete( bean.getId() );
    return result;
  }

  public ClientToApiBean query( int clientId , String clientApi ) {
    ClientToApiBean bean = null;
    if ( clientId < 1 ) {
      return bean;
    }
    if ( StringUtils.isBlank( clientApi ) ) {
      return bean;
    }
    bean = dao.query( clientId , clientApi );
    return bean;
  }

}
