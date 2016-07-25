package com.beepcast.model.event;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EventEmailService {

  static final DLogContext lctx = new SimpleContext( "EventEmailService" );

  private EventEmailDAO dao;

  public EventEmailService() {
    dao = new EventEmailDAO();
  }

  public boolean insert( EventEmailBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert "
          + ", found null event email bean" );
      return result;
    }
    result = dao.insert( bean );
    return result;
  }

  public boolean updateEmailClob( EventEmailBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to update email clob "
          + ", found null event email bean" );
      return result;
    }
    result = dao.updateEmailClob( bean );
    return result;
  }

  public boolean delete( EventEmailBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to delete "
          + ", found null event email bean" );
      return result;
    }
    result = dao.delete( bean );
    return result;
  }

  public EventEmailBean select( int eventId , int processStep ) {
    EventEmailBean bean = null;
    if ( eventId < 1 ) {
      DLog.warning( lctx , "Failed to select , found zero event id" );
      return bean;
    }
    bean = dao.select( eventId , processStep );
    return bean;
  }

}
