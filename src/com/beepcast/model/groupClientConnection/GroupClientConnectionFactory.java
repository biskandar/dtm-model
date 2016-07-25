package com.beepcast.model.groupClientConnection;

public class GroupClientConnectionFactory {

  public static GroupClientConnectionBean createGroupClientConnectionBean(
      String name ) {
    GroupClientConnectionBean bean = new GroupClientConnectionBean();
    bean.setName( name );
    bean.setActive( true );
    return bean;
  }

}
