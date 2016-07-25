package com.beepcast.model.userLog;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UserLogService {

  static final DLogContext lctx = new SimpleContext( "UserLogService" );

  private UserLogDAO dao;

  public UserLogService() {
    dao = new UserLogDAO();
  }

  public boolean insert( UserLogBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert , found null bean" );
      return result;
    }
    if ( !dao.insert( bean ) ) {
      DLog.warning( lctx , "Failed to insert "
          + ", found failed to insert into table" );
      return result;
    }
    result = true;
    return result;
  }

  public boolean delete( int userId , String visitId ) {
    boolean result = false;
    if ( userId < 1 ) {
      DLog.warning( lctx , "Failed to delete , found zero user id" );
      return result;
    }
    if ( StringUtils.isBlank( visitId ) ) {
      DLog.warning( lctx , "Failed to delete , found blank visit id" );
      return result;
    }
    if ( !dao.delete( userId , visitId ) ) {
      DLog.warning( lctx , "Failed to delete "
          + ", found failed delete from table" );
      return result;
    }
    result = true;
    return result;
  }

  public boolean delete( int userId ) {
    boolean result = false;
    if ( userId < 1 ) {
      DLog.warning( lctx , "Failed to delete , found zero user id" );
      return result;
    }
    if ( !dao.delete( userId , null ) ) {
      DLog.warning( lctx , "Failed to delete "
          + ", found failed delete from table" );
      return result;
    }
    result = true;
    return result;
  }

  public boolean isExist( int userId , String visitId ) {
    boolean result = false;
    if ( userId < 1 ) {
      DLog.warning( lctx , "Failed to find exist , found zero user id" );
      return result;
    }
    if ( StringUtils.isBlank( visitId ) ) {
      DLog.warning( lctx , "Failed to find exist , found blank visit id" );
      return result;
    }
    result = dao.isExist( userId , visitId );
    return result;
  }

}
