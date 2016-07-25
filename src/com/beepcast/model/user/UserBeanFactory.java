package com.beepcast.model.user;

public class UserBeanFactory {

  public static UserBean createUserBean( String userID , String password ,
      String[] roles , String name , String phone , String email ,
      long clientID , long proxyID ) {
    UserBean userBean = new UserBean();
    userBean.setUserID( userID );
    userBean.setPassword( password );
    userBean.setRoles( roles );
    userBean.setName( name );
    userBean.setPhone( phone );
    userBean.setEmail( email );
    userBean.setClientID( clientID );
    userBean.setProxyID( proxyID );
    return userBean;
  }

}
