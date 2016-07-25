package com.beepcast.model.user;

import java.util.Iterator;
import java.util.List;

import com.beepcast.model.client.ClientType;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListUserService {

  static final DLogContext lctx = new SimpleContext( "ListUserService" );

  public static final int ORDERBY_ID_ASC = 0;
  public static final int ORDERBY_ID_DESC = 1;
  public static final int ORDERBY_USERID_ASC = 2;
  public static final int ORDERBY_USERID_DESC = 3;

  public static final int DISPLAY_ALL = 0;
  public static final int DISPLAY_ON = 1;
  public static final int DISPLAY_OFF = 2;

  private ListUserDAO dao;

  public ListUserService() {
    dao = new ListUserDAO();
  }

  public ListUserBeans listUserBeans( int clientId , String keywordUserId ,
      String keywordName , int display , int top , int limit , int orderby ) {
    return listUserBeans( ClientType.USER , clientId , keywordUserId ,
        keywordName , display , top , limit , orderby );
  }

  public ListUserBeans listUserBeans( int clientType , int masterClientId ,
      String keywordUserId , String keywordName , int display , int top ,
      int limit , int orderby ) {
    ListUserBeans bean = null;
    bean = ListUserFactory.createListUserBeans( clientType , masterClientId );
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to create list user bean" );
      return bean;
    }
    totalRecords( bean , keywordUserId , keywordName , display );
    if ( top < 0 ) {
      top = 0;
    }
    if ( limit > 100 ) {
      limit = 100;
    }
    loadRecords( bean , keywordUserId , keywordName , display , top , limit ,
        orderby );
    return bean;
  }

  public Iterator iterUserBeans( ListUserBeans bean ) {
    Iterator iter = null;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null list user beans" );
      return iter;
    }
    List userBeans = bean.getUserBeans();
    if ( userBeans == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null user beans" );
      return iter;
    }
    iter = userBeans.iterator();
    return iter;
  }

  private void totalRecords( ListUserBeans bean , String keywordUserId ,
      String keywordUserName , int display ) {
    bean.setTotalRecords( dao.totalActiveUsers( bean.getClientType() ,
        bean.getMasterClientId() , keywordUserId , keywordUserName , display ) );
  }

  private void loadRecords( ListUserBeans bean , String keywordUserId ,
      String keywordUserName , int display , int top , int limit , int orderby ) {
    bean.setUserBeans( dao.selectActiveUsers( bean.getClientType() ,
        bean.getMasterClientId() , keywordUserId , keywordUserName , display ,
        top , limit , orderby ) );
  }

}
