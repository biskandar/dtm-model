package com.beepcast.model.user;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListUserFactory {

  static final DLogContext lctx = new SimpleContext( "ListUserFactory" );

  public static ListUserBeans createListUserBeans( int clientType ,
      int masterClientId ) {
    ListUserBeans bean = new ListUserBeans();
    bean.setClientType( clientType );
    bean.setMasterClientId( masterClientId );
    bean.setTotalRecords( 0 );
    bean.setUserBeans( null );
    return bean;
  }

}
