package com.beepcast.model.client;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientFactory {

  static final DLogContext lctx = new SimpleContext( "ListClientFactory" );

  public static ListClientBeans createListClientBeans( int masterClientId ) {
    ListClientBeans bean = new ListClientBeans();
    bean.setMasterClientId( masterClientId );
    bean.setTotalRecords( 0 );
    bean.setClientBeans( null );
    return bean;
  }

}
