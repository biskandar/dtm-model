package com.beepcast.model.groupClientConnection;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GroupClientConnectionsService {

  static final DLogContext lctx = new SimpleContext(
      "GroupClientConnectionsService" );

  private GroupClientConnectionsDAO dao;

  public GroupClientConnectionsService() {
    dao = new GroupClientConnectionsDAO();
  }

  public GroupClientConnectionsBean select( boolean active , int top , int limit ) {
    GroupClientConnectionsBean bean = new GroupClientConnectionsBean();
    totalRecords( bean , active );
    loadRecords( bean , active , top , limit );
    return bean;
  }

  private void totalRecords( GroupClientConnectionsBean bean , boolean active ) {
    bean.setTotalRecords( dao.totalRecords( active ) );
  }

  private void loadRecords( GroupClientConnectionsBean bean , boolean active ,
      int top , int limit ) {
    bean.setListRecords( dao.selectRecords( active , top , limit ) );
  }

}
