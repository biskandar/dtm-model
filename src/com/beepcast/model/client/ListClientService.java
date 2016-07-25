package com.beepcast.model.client;

import java.util.Iterator;
import java.util.List;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientService {

  static final DLogContext lctx = new SimpleContext( "ListClientService" );

  public static final int ORDERBY_CLIENTID_ASC = 0;
  public static final int ORDERBY_CLIENTID_DESC = 1;
  public static final int ORDERBY_COMPANYNAME_ASC = 2;
  public static final int ORDERBY_COMPANYNAME_DESC = 3;

  public static final int DISPLAY_ALL = 0;
  public static final int DISPLAY_ON = 1;
  public static final int DISPLAY_OFF = 2;

  private ListClientDAO dao;

  public ListClientService() {
    dao = new ListClientDAO();
  }

  public ListClientBeans listClientBeans( int clientType , int masterClientId ,
      String keywordCompanyName , int display , int top , int limit ,
      int orderby ) {
    ListClientBeans bean = null;
    if ( clientType == ClientType.SUPER ) {
      masterClientId = 0;
    }
    if ( clientType == ClientType.MASTER ) {
      if ( masterClientId < 1 ) {
        DLog.warning( lctx , "Failed to list client beans "
            + ", found empty master client id" );
        return bean;
      }
    }
    bean = ListClientFactory.createListClientBeans( masterClientId );
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to create list client bean" );
      return bean;
    }
    loadTotalRecords( bean , masterClientId , keywordCompanyName , display );
    if ( top < 0 ) {
      top = 0;
    }
    if ( limit > 100 ) {
      limit = 100;
    }
    loadClients( bean , masterClientId , keywordCompanyName , display , top ,
        limit , orderby );
    return bean;
  }

  public Iterator iterClientBeans( ListClientBeans bean ) {
    Iterator iter = null;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null list client beans" );
      return iter;
    }
    List clientBeans = bean.getClientBeans();
    if ( clientBeans == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null client beans" );
      return iter;
    }
    iter = clientBeans.iterator();
    return iter;
  }

  private void loadTotalRecords( ListClientBeans bean , int masterClientId ,
      String keywordCompanyName , int display ) {
    bean.setTotalRecords( dao.totalActiveClients( bean.getMasterClientId() ,
        keywordCompanyName , display ) );
  }

  private void loadClients( ListClientBeans bean , int masterClientId ,
      String keywordCompanyName , int display , int top , int limit ,
      int orderby ) {
    bean.setClientBeans( dao.selectActiveClients( bean.getMasterClientId() ,
        keywordCompanyName , display , top , limit , orderby ) );
  }

}
