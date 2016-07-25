package com.beepcast.model.event;

import java.util.Iterator;
import java.util.List;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListEventService {

  static final DLogContext lctx = new SimpleContext( "ListEventService" );

  public static final int ORDERBY_EVENTID_ASC = 0;
  public static final int ORDERBY_EVENTID_DESC = 1;
  public static final int ORDERBY_EVENTNAME_ASC = 2;
  public static final int ORDERBY_EVENTNAME_DESC = 3;
  public static final int ORDERBY_DATEUPDATED_ASC = 4;
  public static final int ORDERBY_DATEUPDATED_DESC = 5;

  public static final int DISPLAY_ALL = 0;
  public static final int DISPLAY_ON = 1;
  public static final int DISPLAY_OFF = 2;

  private ListEventDAO dao;

  public ListEventService() {
    dao = new ListEventDAO();
  }

  public ListEventBeans listEventBeans( int clientId , int type ,
      String inKeywordEventNames , String exKeywordEventNames , int display ,
      int top , int limit , int orderby ) {
    ListEventBeans bean = ListEventFactory.createListEventBeans( clientId );
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to create list event bean" );
      return bean;
    }
    totalRecords( bean , type , inKeywordEventNames , exKeywordEventNames ,
        display );
    if ( top < 0 ) {
      top = 0;
    }
    if ( limit > 100 ) {
      limit = 100;
    }
    loadRecords( bean , type , inKeywordEventNames , exKeywordEventNames ,
        display , top , limit , orderby );
    return bean;
  }

  public Iterator iterEventBeans( ListEventBeans bean ) {
    Iterator iter = null;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null list event beans" );
      return iter;
    }
    List eventBeans = bean.getEventBeans();
    if ( eventBeans == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null event beans" );
      return iter;
    }
    iter = eventBeans.iterator();
    return iter;
  }

  private void totalRecords( ListEventBeans bean , int type ,
      String inKeywordEventNames , String exKeywordEventNames , int display ) {
    bean.setTotalRecords( dao.totalActiveEvents( bean.getClientId() , type ,
        inKeywordEventNames , exKeywordEventNames , display ) );
  }

  private void loadRecords( ListEventBeans bean , int type ,
      String inKeywordEventNames , String exKeywordEventNames , int display ,
      int top , int limit , int orderby ) {
    bean.setEventBeans( dao.selectActiveEvents( bean.getClientId() , type ,
        inKeywordEventNames , exKeywordEventNames , display , top , limit ,
        orderby ) );
  }

}
