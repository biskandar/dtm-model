package com.beepcast.model.clientNumber;

import java.util.Date;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToNumbersService {

  static final DLogContext lctx = new SimpleContext( "ClientToNumbersService" );

  private ClientToNumbersDAO dao;

  public ClientToNumbersService() {
    dao = new ClientToNumbersDAO();
  }

  public ClientToNumbersBean generateBean( int clientId ) {
    ClientToNumbersBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to generate bean "
          + ", found zero client id" );
      return bean;
    }
    bean = dao.generateBean( clientId );
    return bean;
  }

  public int synchBeanNumbers( int clientId , int groupClientConnectionId ,
      Date activeStarted , Date activeStopped ) {
    int result = 0;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to synch client to numbers "
          + ", found zero client id" );
      return result;
    }
    if ( groupClientConnectionId < 1 ) {
      DLog.warning( lctx , "Failed to synch client to numbers "
          + ", found zero group client connection id" );
      return result;
    }
    if ( activeStarted == null ) {
      DLog.warning( lctx , "Failed to synch client to numbers "
          + ", found null active started" );
      return result;
    }
    if ( activeStopped == null ) {
      DLog.warning( lctx , "Failed to synch client to numbers "
          + ", found null active stopped" );
      return result;
    }
    result = dao.synchBeanNumbers( clientId , groupClientConnectionId ,
        activeStarted , activeStopped );
    return result;
  }

  public int cleanBeans( Date currentDate ) {
    if ( currentDate == null ) {
      currentDate = new Date();
    }
    return dao.cleanBeans( currentDate );
  }

}
