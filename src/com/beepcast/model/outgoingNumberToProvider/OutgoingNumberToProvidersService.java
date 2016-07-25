package com.beepcast.model.outgoingNumberToProvider;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class OutgoingNumberToProvidersService {

  static final DLogContext lctx = new SimpleContext(
      "OutgoingNumberToProvidersService" );

  private OutgoingNumberToProvidersDAO dao;

  public OutgoingNumberToProvidersService() {
    dao = new OutgoingNumberToProvidersDAO();
  }

  public OutgoingNumberToProvidersBean select( boolean active ,
      int groupConnectionId , String outgoingNumber , int level , int top ,
      int limit ) {
    OutgoingNumberToProvidersBean bean = new OutgoingNumberToProvidersBean();
    totalRecords( bean , active , groupConnectionId , outgoingNumber , level );
    loadRecords( bean , active , groupConnectionId , outgoingNumber , level ,
        top , limit );
    return bean;
  }

  private void totalRecords( OutgoingNumberToProvidersBean bean ,
      boolean active , int groupConnectionId , String outgoingNumber , int level ) {
    bean.setTotalRecords( dao.totalRecords( active , groupConnectionId ,
        outgoingNumber , level ) );
  }

  private void loadRecords( OutgoingNumberToProvidersBean bean ,
      boolean active , int groupConnectionId , String outgoingNumber ,
      int level , int top , int limit ) {
    bean.setListRecords( dao.selectRecords( active , groupConnectionId ,
        outgoingNumber , level , top , limit ) );
  }

}
