package com.beepcast.model.userLog;

import java.util.Date;

public class UserLogViewFactory {

  public static UserLogViewBean createUserLogViewBean( int userLogId ,
      String companyName , String userId , String actionText , Date actionDate ) {
    UserLogViewBean bean = new UserLogViewBean();
    bean.setUserLogId( userLogId );
    bean.setCompanyName( companyName );
    bean.setUserId( userId );
    bean.setActionText( actionText );
    bean.setActionDate( actionDate );
    return bean;
  }

}
