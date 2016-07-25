package com.beepcast.model.provider;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ProvidersService {

  static final DLogContext lctx = new SimpleContext( "ProvidersService" );

  private ProvidersDAO dao;

  public ProvidersService() {
    dao = new ProvidersDAO();
  }

  public ProvidersBean select( boolean active , String type , String direction ,
      int top , int limit ) {
    ProvidersBean bean = new ProvidersBean();
    totalRecords( bean , active , type , direction );
    loadRecords( bean , active , type , direction , top , limit );
    return bean;
  }

  private void totalRecords( ProvidersBean bean , boolean active , String type ,
      String direction ) {
    bean.setTotalRecords( dao.totalRecords( active , type , direction ) );
  }

  private void loadRecords( ProvidersBean bean , boolean active , String type ,
      String direction , int top , int limit ) {
    bean.setListRecords( dao.selectRecords( active , type , direction , top ,
        limit ) );
  }

}
