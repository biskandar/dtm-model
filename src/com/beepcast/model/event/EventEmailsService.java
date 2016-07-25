package com.beepcast.model.event;

import java.util.List;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EventEmailsService {

  static final DLogContext lctx = new SimpleContext( "EventEmailsService" );

  private EventEmailsDAO dao;

  public EventEmailsService() {
    dao = new EventEmailsDAO();
  }

  public List selectByEventId( long eventId ) {
    List list = null;
    if ( eventId < 1 ) {
      DLog.warning( lctx , "Failed to select by event id "
          + ", found zero event id " );
      return list;
    }
    list = dao.selectByEventId( eventId );
    return list;
  }

}
