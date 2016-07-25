package com.beepcast.model.clientApi;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientApiService {

  static final DLogContext lctx = new SimpleContext( "ClientApiService" );

  private ClientApiDAO dao;

  public ClientApiService() {
    dao = new ClientApiDAO();
  }

  public boolean insert( ClientApiBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }
    if ( bean.getClientId() < 1 ) {
      DLog.warning( lctx , "Failed to insert , found zero client id" );
      return result;
    }

    String str = null;

    str = bean.getApiVersion();
    if ( StringUtils.isBlank( str ) ) {
      bean.setApiVersion( ClientApiBean.VERSION_01_00 );
    }

    str = bean.getMoDirection();
    if ( StringUtils.isBlank( str ) ) {
      bean.setMoDirection( ClientApiBean.DIRECTION_PULL );
    }
    str = bean.getMoFormat();
    if ( StringUtils.isBlank( str ) ) {
      bean.setMoFormat( ClientApiBean.FORMAT_XML );
    }
    str = bean.getMoApiVer();
    if ( StringUtils.isBlank( str ) ) {
      bean.setMoApiVer( ClientApiBean.VERSION_01_00 );
    }

    str = bean.getDnDirection();
    if ( StringUtils.isBlank( str ) ) {
      bean.setDnDirection( ClientApiBean.DIRECTION_PULL );
    }
    str = bean.getDnFormat();
    if ( StringUtils.isBlank( str ) ) {
      bean.setDnFormat( ClientApiBean.FORMAT_XML );
    }
    str = bean.getDnApiVer();
    if ( StringUtils.isBlank( str ) ) {
      bean.setDnApiVer( ClientApiBean.VERSION_01_00 );
    }

    result = dao.insert( bean );
    return result;
  }

  public ClientApiBean queryWhereClientId( int clientId ) {
    ClientApiBean bean = null;
    if ( clientId < 1 ) {
      return bean;
    }
    bean = dao.queryWhereClientId( clientId );
    return bean;
  }

  public boolean updateMoDnProfile( ClientApiBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }

    if ( bean.getClientId() < 1 ) {
      DLog.warning( lctx , "Failed to insert , found zero client id" );
      return result;
    }

    String str = null;

    str = bean.getApiVersion();
    if ( StringUtils.isBlank( str ) ) {
      bean.setApiVersion( ClientApiBean.VERSION_01_00 );
    }

    str = bean.getMoDirection();
    if ( StringUtils.isBlank( str ) ) {
      bean.setMoDirection( ClientApiBean.DIRECTION_PULL );
    }
    str = bean.getMoFormat();
    if ( StringUtils.isBlank( str ) ) {
      bean.setMoFormat( ClientApiBean.FORMAT_XML );
    }
    str = bean.getMoApiVer();
    if ( StringUtils.isBlank( str ) ) {
      bean.setMoApiVer( ClientApiBean.VERSION_01_00 );
    }

    str = bean.getDnDirection();
    if ( StringUtils.isBlank( str ) ) {
      bean.setDnDirection( ClientApiBean.DIRECTION_PULL );
    }
    str = bean.getDnFormat();
    if ( StringUtils.isBlank( str ) ) {
      bean.setDnFormat( ClientApiBean.FORMAT_XML );
    }
    str = bean.getDnApiVer();
    if ( StringUtils.isBlank( str ) ) {
      bean.setDnApiVer( ClientApiBean.VERSION_01_00 );
    }

    result = dao.updateMoDnProfile( bean.getId() , bean.getMoDirection() ,
        bean.getMoUri() , bean.getMoFormat() , bean.getDnDirection() ,
        bean.getDnUri() , bean.getDnFormat() );
    return result;
  }

  public boolean delete( ClientApiBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }
    result = dao.updateActive( bean.getId() , false );
    return result;
  }

}
