package com.beepcast.model.clientRequest;

import java.util.Iterator;
import java.util.List;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientRequestHistoryService {

  static final DLogContext lctx = new SimpleContext(
      "ListClientRequestHistoryService" );

  public static final int TYPE_REQ = 0;
  public static final int TYPE_RES = 1;

  public static final int DISPLAY_ALL = 0;
  public static final int DISPLAY_ON = 1;
  public static final int DISPLAY_OFF = 2;

  public static final int ORDERBY_ID_ASC = 0;
  public static final int ORDERBY_ID_DESC = 1;
  public static final int ORDERBY_MODIFY_ASC = 2;
  public static final int ORDERBY_MODIFY_DESC = 3;

  private ListClientRequestHistoryDAO dao;

  public ListClientRequestHistoryService() {
    dao = new ListClientRequestHistoryDAO();
  }

  public ListClientRequestHistoryBean listBean( int clientId , int type ,
      int display , int top , int limit , int orderby ) {
    ListClientRequestHistoryBean bean = ListClientRequestHistoryFactory
        .createListClientRequestHistoryBean( clientId );
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to create list "
          + "client request history bean" );
      return bean;
    }
    loadTotalRecords( bean , type , display );
    if ( top < 0 ) {
      top = 0;
    }
    if ( limit > 100 ) {
      limit = 100;
    }
    loadEvents( bean , type , display , top , limit , orderby );
    return bean;
  }

  public Iterator iterBean( ListClientRequestHistoryBean bean ) {
    Iterator iter = null;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null list client request history bean" );
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

  private void loadTotalRecords( ListClientRequestHistoryBean bean , int type ,
      int display ) {
    bean.setTotalRecords( dao.totalActiveRecords( bean.getClientId() , type ,
        display ) );
  }

  private void loadEvents( ListClientRequestHistoryBean bean , int type ,
      int display , int top , int limit , int orderby ) {
    bean.setListRecords( dao.selectActiveRecords( bean.getClientId() , type ,
        display , top , limit , orderby ) );
  }

}
