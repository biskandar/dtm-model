package com.beepcast.model.userLog;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.beepcast.model.client.ClientType;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UserLogViewService {

  static final DLogContext lctx = new SimpleContext( "UserLogViewService" );

  private UserLogViewDAO dao;

  public UserLogViewService() {
    dao = new UserLogViewDAO();
  }

  public UserLogViewBeans listBeans( int clientId , String findUserId ,
      String findActionText , Date dateStarted , Date dateFinished , int top ,
      int limit ) {
    return listBeans( ClientType.USER , clientId , findUserId , findActionText ,
        dateStarted , dateFinished , top , limit );
  }

  public UserLogViewBeans listBeans( int clientType , int masterClientId ,
      String findUserId , String findActionText , Date dateStarted ,
      Date dateFinished , int top , int limit ) {
    DLog.debug( lctx ,
        "List beans with : clientType = " + ClientType.toString( clientType )
            + " , masterClientId = " + masterClientId + " , findUserId = "
            + findUserId + " , findActionText = " + findActionText
            + " , dateStarted = " + dateStarted + " , dateFinished = "
            + dateFinished + " , top = " + top + " , limit = " + limit );
    UserLogViewBeans bean = new UserLogViewBeans();
    bean.setTotalRecords( dao.totalRecords( clientType , masterClientId ,
        findUserId , findActionText , dateStarted , dateFinished ) );
    bean.setListRecords( dao.listRecords( clientType , masterClientId ,
        findUserId , findActionText , dateStarted , dateFinished , top , limit ) );
    return bean;
  }

  public Iterator iterBeans( UserLogViewBeans bean ) {
    Iterator iter = null;
    if ( bean == null ) {
      return iter;
    }
    List list = bean.getListRecords();
    if ( list == null ) {
      return iter;
    }
    iter = list.iterator();
    return iter;
  }

}
