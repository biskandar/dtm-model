package com.beepcast.model.user;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UserService {

  static final DLogContext lctx = new SimpleContext( "UserService" );

  private UserDAO dao;

  public UserService() {
    dao = new UserDAO();
  }

  public boolean verifyUserBean( UserBean userBean ) {
    boolean result = false;
    if ( userBean == null ) {
      return result;
    }
    String userID = userBean.getUserID();
    if ( StringUtils.isBlank( userID ) ) {
      return result;
    }
    result = true;
    return result;
  }

  public int insertUserBean( UserBean userBean ) {
    int result = 0;
    if ( !verifyUserBean( userBean ) ) {
      return result;
    }
    result = dao.insertUserBean( userBean );
    return result;
  }

  public boolean setHidden( int id ) {
    return dao.updateDisplay( id , false );
  }

  public boolean setShown( int id ) {
    return dao.updateDisplay( id , true );
  }

  public boolean delete( int id ) {
    return dao.updateActive( id , false );
  }

  public boolean update( UserBean userBean ) {
    boolean result = false;
    if ( userBean == null ) {
      DLog.warning( lctx , "Failed to update user bean , found null bean" );
      return result;
    }
    result = dao.update( userBean );
    return result;
  }

  public UserBean selectBasedOnId( int id ) {
    UserBean bean = null;
    if ( id < 1 ) {
      return bean;
    }
    bean = dao.selectBasedOnId( id );
    return bean;
  }

  public UserBean selectBasedOnUserID( String userID ) {
    UserBean bean = null;
    if ( StringUtils.isBlank( userID ) ) {
      return bean;
    }
    bean = dao.selectBasedOnUserID( userID );
    return bean;
  }

  public UserBean selectBasedOnEmail( String email ) {
    UserBean bean = null;
    if ( StringUtils.isBlank( email ) ) {
      return bean;
    }
    bean = dao.selectBasedOnEmail( email );
    return bean;
  }

  public UserBean selectBasedOnPhone( String phone ) {
    UserBean bean = null;
    if ( StringUtils.isBlank( phone ) ) {
      return bean;
    }
    bean = dao.selectBasedOnPhone( phone );
    return bean;
  }

  public boolean updatePasswordFromUserId( String userId , String newPassword ) {
    boolean result = false;
    if ( StringUtils.isBlank( userId ) ) {
      DLog.warning( lctx , "Failed to update password "
          + ", found blank user id" );
      return result;
    }
    if ( StringUtils.isBlank( newPassword ) ) {
      DLog.warning( lctx , "Failed to update password "
          + ", found blank new password" );
      return result;
    }
    result = dao.updatePasswordFromUserId( userId , null , newPassword );
    return result;
  }

  public boolean updatePasswordFromUserId( String userId , String oldPassword ,
      String newPassword ) {
    boolean result = false;
    if ( StringUtils.isBlank( userId ) ) {
      DLog.warning( lctx , "Failed to update password "
          + ", found blank user id" );
      return result;
    }
    if ( StringUtils.isBlank( oldPassword ) ) {
      DLog.warning( lctx , "Failed to update password "
          + ", found blank old password" );
      return result;
    }
    if ( StringUtils.isBlank( newPassword ) ) {
      DLog.warning( lctx , "Failed to update password "
          + ", found blank new password" );
      return result;
    }
    result = dao.updatePasswordFromUserId( userId , oldPassword , newPassword );
    return result;
  }

  public boolean verifyPasswordFromUserId( String userId , String password ) {
    boolean result = false;
    if ( StringUtils.isBlank( userId ) ) {
      DLog.warning( lctx , "Failed to verify password "
          + ", found blank user id" );
      return result;
    }
    if ( StringUtils.isBlank( password ) ) {
      DLog.warning( lctx , "Failed to verify password "
          + ", found blank password" );
      return result;
    }
    result = dao.verifyPasswordFromUserId( userId , password );
    return result;
  }

}
