package com.beepcast.model.clientApi;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToApiFactory {

  static final DLogContext lctx = new SimpleContext( "ClientToApiFactory" );

  public static ClientToApiBean createClientToApiBean( int clientId ,
      String clientApi ) {
    ClientToApiBean clientToApiBean = null;

    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to create client to api bean "
          + ", found zero client id " );
      return clientToApiBean;
    }

    if ( StringUtils.isBlank( clientApi ) ) {
      DLog.warning( lctx , "Failed to create client to api bean "
          + ", found blank client api " );
      return clientToApiBean;
    }

    clientToApiBean = new ClientToApiBean();
    clientToApiBean.setClientId( clientId );
    clientToApiBean.setClientApi( clientApi );
    clientToApiBean.setActive( true );

    return clientToApiBean;
  }

}
