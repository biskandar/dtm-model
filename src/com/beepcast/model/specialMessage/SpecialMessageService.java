package com.beepcast.model.specialMessage;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class SpecialMessageService {

  static final DLogContext lctx = new SimpleContext( "SpecialMessageService" );

  private SpecialMessageDAO dao;

  public SpecialMessageService() {
    dao = new SpecialMessageDAO();
  }

  public boolean persist( SpecialMessageBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to persist , found null bean" );
      return result;
    }
    SpecialMessageBean selectBean = select( bean.getType() , bean.getName() );
    if ( selectBean == null ) {
      result = insert( bean );
      return result;
    }
    selectBean.setContent( bean.getContent() );
    selectBean.setDescription( bean.getDescription() );
    result = updateContent( selectBean );
    return result;
  }

  public boolean insert( SpecialMessageBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert , found null bean" );
      return result;
    }
    if ( StringUtils.isBlank( bean.getType() ) ) {
      DLog.warning( lctx , "Failed to insert , found blank type" );
      return result;
    }
    if ( StringUtils.isBlank( bean.getName() ) ) {
      DLog.warning( lctx , "Failed to insert , found blank name" );
      return result;
    }
    result = dao.insert( bean );
    return result;
  }

  public SpecialMessageBean select( String type , String name ) {
    SpecialMessageBean bean = null;
    if ( StringUtils.isBlank( type ) ) {
      DLog.warning( lctx , "Failed to select , found blank type" );
      return bean;
    }
    if ( StringUtils.isBlank( name ) ) {
      DLog.warning( lctx , "Failed to select , found blank name" );
      return bean;
    }
    bean = dao.select( type , name );
    return bean;
  }

  public boolean updateContent( SpecialMessageBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to update content , found null bean" );
      return result;
    }
    if ( bean.getId() < 1 ) {
      DLog.warning( lctx , "Failed to update content , found zero id" );
      return result;
    }
    result = dao.updateContent( bean );
    return result;
  }

}
