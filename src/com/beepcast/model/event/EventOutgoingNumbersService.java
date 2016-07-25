package com.beepcast.model.event;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EventOutgoingNumbersService {

  static final DLogContext lctx = new SimpleContext(
      "EventOutgoingNumbersService" );

  private EventOutgoingNumbersDAO dao;

  public EventOutgoingNumbersService() {
    dao = new EventOutgoingNumbersDAO();
  }

  public EventOutgoingNumbersBean selectByEventId( int eventId ) {
    EventOutgoingNumbersBean bean = new EventOutgoingNumbersBean();
    bean.listOutgoingNumbers().addAll( dao.selectByEventId( eventId ) );
    return bean;
  }

  public EventOutgoingNumbersBean selectByClientId( int clientId ) {
    EventOutgoingNumbersBean bean = new EventOutgoingNumbersBean();
    bean.listOutgoingNumbers().addAll( dao.selectByClientId( clientId ) );
    return bean;
  }

  public EventOutgoingNumbersBean selectByGroupClientConnId(
      int groupClientConnId ) {
    EventOutgoingNumbersBean bean = new EventOutgoingNumbersBean();
    bean.listOutgoingNumbers().addAll(
        dao.selectByGroupClientConnId( groupClientConnId ) );
    return bean;
  }

}
