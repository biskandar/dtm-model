package com.beepcast.model.event;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ProcessCommon {

  static final DLogContext lctx = new SimpleContext( "ProcessCommon" );

  // ---------------------------------------------------------------------------

  public static String getFirstProcessBeanResponse( int eventId ) {
    return getFirstProcessBeanResponse( getEventBean( eventId ) );
  }

  public static ProcessBean getFirstProcessBean( int eventId ) {
    return getFirstProcessBean( getEventBean( eventId ) );
  }

  public static ProcessBean[] getProcessBeans( int eventId ) {
    return getProcessBeans( getEventBean( eventId ) );
  }

  public static String getFirstProcessBeanResponse( EventBean eventBean ) {
    String response = null;
    if ( eventBean == null ) {
      return response;
    }
    ProcessBean processBean = getFirstProcessBean( eventBean );
    if ( processBean == null ) {
      return response;
    }
    response = processBean.getResponse();
    return response;
  }

  public static ProcessBean getFirstProcessBean( EventBean eventBean ) {
    ProcessBean processBean = null;
    if ( eventBean == null ) {
      return processBean;
    }
    ProcessBean[] processBeans = getProcessBeans( eventBean );
    if ( ( processBeans == null ) || ( processBeans.length < 1 ) ) {
      return processBean;
    }
    processBean = processBeans[0];
    return processBean;
  }

  // ---------------------------------------------------------------------------

  public static ProcessBean[] getProcessBeans( EventBean eventBean ) {
    ProcessBean[] processBeans = null;
    if ( eventBean == null ) {
      return processBeans;
    }
    String processString = eventBean.getProcess();
    if ( StringUtils.isBlank( processString ) ) {
      return processBeans;
    }
    ProcessService processService = new ProcessService();
    processBeans = processService.decode( processString );
    return processBeans;
  }

  // ---------------------------------------------------------------------------

  public static ProcessBean nextProcessBean( ProcessBean processBean ,
      ProcessBean[] processBeans ) {
    ProcessBean nextProcessBean = null;
    if ( processBean == null ) {
      return nextProcessBean;
    }
    if ( ( processBeans == null ) || ( processBeans.length < 1 ) ) {
      return nextProcessBean;
    }
    String nextStep = processBean.getNextStep();
    if ( StringUtils.equalsIgnoreCase( nextStep , "END" ) ) {
      return nextProcessBean;
    }
    for ( int idx = 0 ; idx < processBeans.length ; idx++ ) {
      ProcessBean processBeanTemp = processBeans[idx];
      if ( processBeanTemp == null ) {
        continue;
      }
      String curStep = processBeanTemp.getStep();
      if ( curStep == null ) {
        continue;
      }
      if ( !StringUtils.equalsIgnoreCase( curStep , nextStep ) ) {
        continue;
      }
      nextProcessBean = processBeanTemp;
      break;
    }
    return nextProcessBean;
  }

  // ---------------------------------------------------------------------------

  private static EventBean getEventBean( int eventId ) {
    EventBean eventBean = null;
    try {
      EventService eventService = new EventService();
      eventBean = eventService.select( eventId );
    } catch ( Exception e ) {
    }
    return eventBean;
  }

}
