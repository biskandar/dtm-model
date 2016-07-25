package com.beepcast.model.user;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UsersService {

  static final DLogContext lctx = new SimpleContext( "UsersService" );

  private UsersDAO dao;

  public UsersService() {
    dao = new UsersDAO();
  }

  public UsersBean selectActiveUsersBean() {
    return dao.selectActiveUsersBean();
  }

  public UsersBean selectBasedOnEmail( String email ) {
    UsersBean bean = null;
    if ( StringUtils.isBlank( email ) ) {
      return bean;
    }
    bean = dao.selectBasedOnEmail( email );
    return bean;
  }

  public UsersBean selectBasedOnPhone( String phone ) {
    UsersBean bean = null;
    if ( StringUtils.isBlank( phone ) ) {
      return bean;
    }
    bean = dao.selectBasedOnPhone( phone );
    return bean;
  }

  public UsersBean selectBasedOnUserIds( String strUserIds ) {
    UsersBean bean = null;
    if ( StringUtils.isBlank( strUserIds ) ) {
      return bean;
    }
    bean = dao.selectBasedOnUserIds( strUserIds );
    return bean;
  }

  public UsersBean selectBasedOnClientId( int clientId ) {
    UsersBean bean = null;
    if ( clientId < 1 ) {
      return bean;
    }
    bean = dao.selectBasedOnClientId( clientId );
    return bean;
  }

}
