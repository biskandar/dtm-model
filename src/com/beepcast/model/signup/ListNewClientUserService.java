package com.beepcast.model.signup;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListNewClientUserService {

  static final DLogContext lctx = new SimpleContext( "ListNewClientUserService" );

  private ListNewClientUserDAO dao;

  public ListNewClientUserService() {
    dao = new ListNewClientUserDAO();
  }

  public ListNewClientUserBean listRecords( int top , int limit ) {
    ListNewClientUserBean bean = new ListNewClientUserBean();
    totalRecords( bean );
    if ( top < 0 ) {
      top = 0;
    }
    if ( limit > 100 ) {
      limit = 100;
    }
    queryRecords( bean , top , limit );
    return bean;
  }

  private void totalRecords( ListNewClientUserBean bean ) {
    bean.setTotalRecords( dao.totalRecords() );
  }

  private void queryRecords( ListNewClientUserBean bean , int top , int limit ) {
    bean.setListBeans( dao.queryRecords( top , limit ) );
  }

}
