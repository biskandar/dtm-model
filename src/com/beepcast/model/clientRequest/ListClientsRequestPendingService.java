package com.beepcast.model.clientRequest;

import java.util.Iterator;
import java.util.List;

import com.beepcast.model.client.MasterClientsInfoBean;
import com.beepcast.model.client.MasterClientsInfoService;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientsRequestPendingService {

  static final DLogContext lctx = new SimpleContext(
      "ListClientsRequestPendingService" );

  public static final int DISPLAY_ALL = 0;
  public static final int DISPLAY_ON = 1;
  public static final int DISPLAY_OFF = 2;

  public static final int ORDERBY_ID_ASC = 0;
  public static final int ORDERBY_ID_DESC = 1;
  public static final int ORDERBY_MODIFY_ASC = 2;
  public static final int ORDERBY_MODIFY_DESC = 3;

  private ListClientsRequestPendingDAO dao;

  public ListClientsRequestPendingService() {
    dao = new ListClientsRequestPendingDAO();
  }

  public ListClientsRequestPendingBean listBean( int clientType ,
      int masterClientId , int display , int top , int limit , int orderby ) {
    ListClientsRequestPendingBean bean = null;

    MasterClientsInfoService service = new MasterClientsInfoService();

    List listClientsInfo = service.getClientsBasedOnMasterClientId( clientType ,
        masterClientId );
    if ( listClientsInfo == null ) {
      DLog.warning( lctx , "Failed to create list "
          + "clients request pending bean , found null member" );
      return bean;
    }

    StringBuffer memberClientIds = null;
    Iterator iterClientsInfo = listClientsInfo.iterator();
    while ( iterClientsInfo.hasNext() ) {
      MasterClientsInfoBean c = (MasterClientsInfoBean) iterClientsInfo.next();
      if ( c == null ) {
        continue;
      }
      if ( memberClientIds == null ) {
        memberClientIds = new StringBuffer();
      } else {
        memberClientIds.append( "," );
      }
      memberClientIds.append( c.getClientId() );
    }

    if ( memberClientIds == null ) {
      DLog.warning( lctx , "Failed to create list "
          + "clients request pending bean , found empty member" );
      return bean;
    }

    bean = listBean( memberClientIds.toString() , display , top , limit ,
        orderby );

    return bean;
  }

  public ListClientsRequestPendingBean listBean( String memberClientIds ,
      int display , int top , int limit , int orderby ) {
    ListClientsRequestPendingBean bean = ListClientsRequestPendingFactory
        .createListClientsRequestPendingBean( memberClientIds );
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to create list "
          + "clients request pending bean" );
      return bean;
    }
    loadTotalRecords( bean , display );
    if ( top < 0 ) {
      top = 0;
    }
    if ( limit > 100 ) {
      limit = 100;
    }
    loadEvents( bean , display , top , limit , orderby );
    return bean;
  }

  public Iterator iterBean( ListClientsRequestPendingBean bean ) {
    Iterator iter = null;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null list clients request pending bean" );
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

  private void loadTotalRecords( ListClientsRequestPendingBean bean ,
      int display ) {
    bean.setTotalRecords( dao.totalActiveRecords( bean.getMemberClientIds() ,
        display ) );
  }

  private void loadEvents( ListClientsRequestPendingBean bean , int display ,
      int top , int limit , int orderby ) {
    bean.setListRecords( dao.selectActiveRecords( bean.getMemberClientIds() ,
        display , top , limit , orderby ) );
  }

}
