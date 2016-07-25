package com.beepcast.model.client;

import java.util.ArrayList;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientCreditUnitInfoFactory {

  static final DLogContext lctx = new SimpleContext(
      "ListClientCreditUnitInfoFactory" );

  public static ListClientCreditUnitInfoBean createListClientCreditUnitInfoBean(
      int clientId ) {
    ListClientCreditUnitInfoBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to create bean , found zero clientId" );
      return bean;
    }
    bean = new ListClientCreditUnitInfoBean();
    bean.setClientId( clientId );
    bean.setTotalRecords( 0 );
    bean.setListRecords( new ArrayList() );
    return bean;
  }

}
