package com.beepcast.model.userLog;

public class UserLogFactory {

  public static UserLogBean createUserLogBean( int userId , String visitId ,
      String action ) {
    UserLogBean bean = new UserLogBean();

    visitId = ( visitId == null ) ? "" : visitId;
    action = ( action == null ) ? "" : action;

    bean.setUserId( userId );
    bean.setVisitId( visitId );
    bean.setAction( action );
    bean.setActive( true );

    return bean;
  }

}
