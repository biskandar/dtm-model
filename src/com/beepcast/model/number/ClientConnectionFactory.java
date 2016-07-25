package com.beepcast.model.number;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientConnectionFactory {

  static final DLogContext lctx = new SimpleContext( "ClientConnectionFactory" );

  public static ClientConnectionBean createClientConnectionBean(
      int groupClientConnectionId , String number ) {
    ClientConnectionBean bean = new ClientConnectionBean();
    bean.setGroupClientConnectionId( groupClientConnectionId );
    bean.setNumber( number );
    bean.setActive( true );
    return bean;
  }

}
