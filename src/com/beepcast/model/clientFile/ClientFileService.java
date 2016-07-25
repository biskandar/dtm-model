package com.beepcast.model.clientFile;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientFileService {

  static final DLogContext lctx = new SimpleContext( "ClientFileService" );

  private ClientFileDAO dao;

  public ClientFileService() {
    dao = new ClientFileDAO();
  }

  public boolean insertBean( ClientFileBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert bean , found null bean" );
      return result;
    }
    if ( bean.getClientId() < 1 ) {
      DLog.warning( lctx , "Failed to insert bean , found zero client id" );
      return result;
    }
    if ( StringUtils.isBlank( bean.getCaption() ) ) {
      DLog.warning( lctx , "Failed to insert bean , found blank caption" );
      return result;
    }
    result = dao.insertBean( bean );
    return result;
  }

  public ClientFileBean selectBean( int clientFileId ) {
    ClientFileBean bean = null;
    if ( clientFileId < 1 ) {
      DLog.warning( lctx , "Failed to select bean , found zero client file id" );
      return bean;
    }
    bean = dao.selectBean( clientFileId );
    return bean;
  }

  public ClientFileBean selectBeanByCaption( int clientId , String caption ) {
    ClientFileBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to select bean , found zero client id" );
      return bean;
    }
    if ( StringUtils.isBlank( caption ) ) {
      DLog.warning( lctx , "Failed to select bean , found blank caption" );
      return bean;
    }
    bean = dao.selectBeanByCaption( clientId , caption );
    return bean;
  }

  public ClientFileBean selectBeanByWebLink( int clientId , String webLink ) {
    ClientFileBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to select bean , found zero client id" );
      return bean;
    }
    if ( StringUtils.isBlank( webLink ) ) {
      DLog.warning( lctx , "Failed to select bean , found blank caption" );
      return bean;
    }
    bean = dao.selectBeanByWebLink( clientId , webLink );
    return bean;
  }

  public boolean updateBean( ClientFileBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to update bean , found null bean" );
      return result;
    }
    if ( bean.getClientId() < 1 ) {
      DLog.warning( lctx , "Failed to update bean , found zero client id" );
      return result;
    }
    if ( StringUtils.isBlank( bean.getCaption() ) ) {
      DLog.warning( lctx , "Failed to update bean , found blank caption" );
      return result;
    }
    result = dao.updateBean( bean );
    return result;
  }

}
