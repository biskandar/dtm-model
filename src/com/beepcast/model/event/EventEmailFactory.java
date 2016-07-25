package com.beepcast.model.event;

public class EventEmailFactory {

  public static EventEmailBean createEventEmailBean( int eventId ,
      int processStep , String emailClob ) {
    EventEmailBean bean = new EventEmailBean();
    bean.setEventId( eventId );
    bean.setProcessStep( processStep );
    bean.setEmailClob( emailClob );
    return bean;
  }

}
