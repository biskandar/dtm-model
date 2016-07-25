package com.beepcast.model.clientNumber;

import java.util.Date;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToNumberFactory {

  static final DLogContext lctx = new SimpleContext( "ClientToNumberFactory" );

  public static ClientToNumberBean createClientToNumberBean( int clientId ,
      String number , Date activeStarted , Date activeStopped ) {
    ClientToNumberBean bean = new ClientToNumberBean();
    bean.setClientId( clientId );
    bean.setNumber( number );
    bean.setActiveStarted( activeStarted );
    bean.setActiveStopped( activeStopped );
    bean.setActive( true );
    return bean;
  }

}
