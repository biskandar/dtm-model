package com.beepcast.model.userLog;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UserLogCommon {

  static final DLogContext lctx = new SimpleContext( "UserLogCommon" );

  public static boolean insertUserLog( int userId , String visitId ,
      String action ) {
    boolean result = false;

    UserLogBean bean = UserLogFactory.createUserLogBean( userId , visitId ,
        action );
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert user log "
          + ", found failed to create user log bean" );
      return result;
    }

    UserLogService service = new UserLogService();
    if ( !service.insert( bean ) ) {
      DLog.warning( lctx , "Failed to insert user log "
          + ", found failed to insert into table" );
      return result;
    }

    result = true;
    return result;
  }

  public static boolean deleteUserLog( int userId , String visitId ) {
    boolean result = false;
    UserLogService service = new UserLogService();
    if ( !service.delete( userId , visitId ) ) {
      DLog.warning( lctx , "Failed to delete user log "
          + ", found failed to delete from table" );
      return result;
    }
    result = true;
    return result;
  }

  public static boolean deleteUserLog( int userId ) {
    boolean result = false;
    UserLogService service = new UserLogService();
    if ( !service.delete( userId ) ) {
      DLog.warning( lctx , "Failed to delete user log "
          + ", found failed to delete from table" );
      return result;
    }
    result = true;
    return result;
  }

  public static boolean isExist( int userId , String visitId ) {
    boolean result = false;
    UserLogService service = new UserLogService();
    result = service.isExist( userId , visitId );
    return result;
  }

}
