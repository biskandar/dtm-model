package com.beepcast.model.client;

import java.util.Iterator;
import java.util.List;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientCreditUnitInfoService {

  static final DLogContext lctx = new SimpleContext(
      "ListClientCreditUnitInfoService" );

  private ListClientCreditUnitInfoDAO dao;

  public ListClientCreditUnitInfoService() {
    dao = new ListClientCreditUnitInfoDAO();
  }

  public ListClientCreditUnitInfoBean getListClientCreditUnitInfoBean(
      int clientId , int top , int limit ) {
    ListClientCreditUnitInfoBean bean = ListClientCreditUnitInfoFactory
        .createListClientCreditUnitInfoBean( clientId );
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to get list client credit unit info bean" );
      return bean;
    }
    loadTotalRecords( bean );
    if ( top < 0 ) {
      top = 0;
    }
    if ( limit > 100 ) {
      limit = 100;
    }
    loadListRecords( bean , top , limit );
    return bean;
  }

  public Iterator iterRecords( ListClientCreditUnitInfoBean bean ) {
    Iterator iter = null;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null list client credit unit info bean" );
      return iter;
    }
    List listRecords = bean.getListRecords();
    if ( listRecords == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null list records" );
      return iter;
    }
    iter = listRecords.iterator();
    return iter;
  }

  private void loadTotalRecords( ListClientCreditUnitInfoBean bean ) {
    bean.setTotalRecords( dao.totalBeans( bean.getClientId() ) );
  }

  private void loadListRecords( ListClientCreditUnitInfoBean bean , int top ,
      int limit ) {
    bean.setListRecords( dao.selectBeans( bean.getClientId() , top , limit ) );
  }

}
