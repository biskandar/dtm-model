package com.beepcast.model.clientFile;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientFileFactory {

  static final DLogContext lctx = new SimpleContext( "ListClientFileFactory" );

  public static ListClientFileBeans createListClientFileBeans( int clientId ) {
    ListClientFileBeans bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to create list client file beans "
          + ", found zero client id" );
      return bean;
    }
    bean = new ListClientFileBeans();
    bean.setClientId( clientId );
    bean.setTotalRecords( 0 );
    bean.setListRecords( null );
    return bean;
  }

}
