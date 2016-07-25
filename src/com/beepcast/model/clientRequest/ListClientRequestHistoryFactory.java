package com.beepcast.model.clientRequest;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientRequestHistoryFactory {

  static final DLogContext lctx = new SimpleContext(
      "ListClientRequestHistoryFactory" );

  public static ListClientRequestHistoryBean createListClientRequestHistoryBean(
      int clientId ) {
    ListClientRequestHistoryBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to create client "
          + "request history bean , found zero client id" );
      return bean;
    }
    bean = new ListClientRequestHistoryBean();
    bean.setClientId( clientId );
    bean.setTotalRecords( 0 );
    bean.setListRecords( null );
    return bean;
  }

}
