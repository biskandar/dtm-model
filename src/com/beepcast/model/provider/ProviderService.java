package com.beepcast.model.provider;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ProviderService {

  static final DLogContext lctx = new SimpleContext( "ProviderService" );

  private ProviderDAO dao;

  public ProviderService() {
    dao = new ProviderDAO();
  }

  public boolean insert( ProviderBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert provider bean "
          + ", found null bean" );
      return result;
    }
    if ( selectActiveBeanFromProviderId( bean.getProviderId() ) != null ) {
      DLog.warning( lctx , "Failed to insert provider bean "
          + ", found conflict provider id = " + bean.getProviderId() );
      return result;
    }
    result = dao.insert( bean );
    return result;
  }

  public ProviderBean selectById( int id ) {
    ProviderBean providerBean = null;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to select by id , found invalid id = " + id );
      return providerBean;
    }
    providerBean = dao.selectById( id );
    return providerBean;
  }

  public ProviderBean selectActiveBeanFromProviderId( String providerId ) {
    ProviderBean providerBean = null;
    if ( StringUtils.isBlank( providerId ) ) {
      DLog.warning( lctx , "Failed to select active bean "
          + "from provider id , found blank provider id" );
      return providerBean;
    }
    providerBean = dao.selectActiveBeanFromProviderId( providerId );
    return providerBean;
  }

  public boolean update( ProviderBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to update provider bean "
          + ", found null bean" );
      return result;
    }
    if ( bean.getId() < 1 ) {
      DLog.warning( lctx , "Failed to insert provider bean "
          + ", found invalid id = " + bean.getId() );
      return result;
    }
    result = dao.update( bean );
    return result;
  }

  public boolean updateActiveFromId( int id , boolean active ) {
    boolean result = false;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to update active bean "
          + "from provider id , found zero id" );
      return result;
    }
    result = dao.updateActiveFromId( id , active );
    return result;
  }

  public List selectActiveBeans() {
    return dao.selectActiveBeans( null , null );
  }

  public List selectActiveIncomingBeans() {
    return dao.selectActiveBeans( ProviderDirection.DIRECTION_IN , null );
  }

  public List selectActiveOutgoingBeans() {
    return dao.selectActiveBeans( ProviderDirection.DIRECTION_OU , null );
  }

  public List selectActiveIncomingBeans( String type ) {
    return dao.selectActiveBeans( ProviderDirection.DIRECTION_IN , type );
  }

  public List selectActiveOutgoingBeans( String type ) {
    return dao.selectActiveBeans( ProviderDirection.DIRECTION_OU , type );
  }

  public List selectActiveBeansFromShortCode() {
    return dao.selectActiveBeansFromShortCode();
  }

  public List selectActiveMasterBeans() {
    return dao.selectActiveMasterBeans();
  }

}
