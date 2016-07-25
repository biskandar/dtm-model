package com.beepcast.model.event;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListEventFactory {

  static final DLogContext lctx = new SimpleContext( "ListEventFactory" );

  public static ListEventBeans createListEventBeans( int clientId ) {
    ListEventBeans bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to create list event bean "
          + ", found zero client id" );
      return bean;
    }
    bean = new ListEventBeans();
    bean.setClientId( clientId );
    bean.setTotalRecords( 0 );
    bean.setEventBeans( null );
    return bean;
  }

}
