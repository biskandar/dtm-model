package com.beepcast.model.specialMessage;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class SpecialMessagesService {

  static final DLogContext lctx = new SimpleContext( "SpecialMessageService" );

  private SpecialMessagesDAO dao;

  public SpecialMessagesService() {
    dao = new SpecialMessagesDAO();
  }

  public List select() {
    List list = dao.select( null );
    return list;
  }

  public List select( String type ) {
    List list = null;
    if ( StringUtils.isBlank( type ) ) {
      DLog.warning( lctx , "Failed to select , found blank type" );
      return list;
    }
    list = dao.select( type );
    return list;
  }

  public List selectActiveTypes() {
    return dao.selectActiveTypes();
  }

  public List selectActiveNames() {
    return dao.selectActiveNames();
  }

}
