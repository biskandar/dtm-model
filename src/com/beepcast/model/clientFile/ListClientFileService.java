package com.beepcast.model.clientFile;

import java.util.Iterator;
import java.util.List;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientFileService {

  static final DLogContext lctx = new SimpleContext( "ListClientFileService" );

  private ListClientFileDAO dao;

  public ListClientFileService() {
    dao = new ListClientFileDAO();
  }

  public ListClientFileBeans listClientFileBeans( int clientId ,
      String fileType , int top , int limit ) {
    ListClientFileBeans bean = ListClientFileFactory
        .createListClientFileBeans( clientId );
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to create list client file bean" );
      return bean;
    }
    loadTotalRecords( bean , fileType );
    if ( top < 0 ) {
      top = 0;
    }
    if ( limit > 100 ) {
      limit = 100;
    }
    loadListRecords( bean , fileType , top , limit );
    return bean;
  }

  public Iterator iterClientFileBeans( ListClientFileBeans beans ) {
    Iterator iter = null;
    if ( beans == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null list client file beans" );
      return iter;
    }
    List listRecords = beans.getListRecords();
    if ( listRecords == null ) {
      DLog.warning( lctx , "Failed to generate iterator "
          + ", found null list records" );
      return iter;
    }
    iter = listRecords.iterator();
    return iter;
  }

  private void loadTotalRecords( ListClientFileBeans bean , String fileType ) {
    bean.setTotalRecords( dao.totalActiveRecords( bean.getClientId() , fileType ) );
  }

  private void loadListRecords( ListClientFileBeans bean , String fileType ,
      int top , int limit ) {
    bean.setListRecords( dao.selectActiveRecords( bean.getClientId() ,
        fileType , top , limit ) );
  }

}
