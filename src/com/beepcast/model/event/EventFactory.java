package com.beepcast.model.event;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EventFactory {

  static final DLogContext lctx = new SimpleContext( "EventFactory" );

  public static EventBean createBasicEventBean( String eventName ,
      int categoryId , int clientId , int numberCodes , int lengthCodes ,
      String response , String comment , boolean channel ,
      String outgoingNumber , String senderId ) {

    // build array of process bean
    ProcessBean firstProcessBean = firstProcessBean( response );
    ProcessBean[] arrProcessBeans = new ProcessBean[] { firstProcessBean };

    // setup process type
    int processType = EventBean.ADVANCED_TYPE;

    // setup bit flags
    int bitFlags = 0;

    return createEventBean( eventName , categoryId , clientId , numberCodes ,
        lengthCodes , arrProcessBeans , comment , processType , channel ,
        bitFlags , outgoingNumber , senderId );
  }

  public static EventBean createEventBean( String eventName , int categoryId ,
      int clientId , int numberCodes , int lengthCodes ,
      ProcessBean[] arrProcessBeans , String comment , int processType ,
      boolean channel , int bitFlags , String outgoingNumber , String senderId ) {

    // now
    Calendar startCal = Calendar.getInstance();

    // now + 6 months
    Calendar endCal = Calendar.getInstance();
    endCal.add( Calendar.MONTH , 6 );

    return createEventBean( eventName , startCal.getTime() , endCal.getTime() ,
        categoryId , clientId , numberCodes , lengthCodes , arrProcessBeans ,
        comment , processType , channel , bitFlags , outgoingNumber , senderId );
  }

  public static EventBean createEventBean( String eventName , Date startDate ,
      Date endDate , int categoryId , int clientId , int numberCodes ,
      int lengthCodes , ProcessBean[] arrProcessBeans , String comment ,
      int processType , boolean channel , int bitFlags , String outgoingNumber ,
      String senderId ) {
    EventBean eventBean = null;

    // validate must be params

    if ( StringUtils.isBlank( eventName ) ) {
      DLog.warning( lctx , "Failed to create event bean "
          + ", found blank event name" );
      return eventBean;
    }
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to create event bean "
          + ", found zero client id" );
      return eventBean;
    }
    if ( numberCodes < 1 ) {
      DLog.warning( lctx , "Failed to create event bean "
          + ", found zero number codes" );
      return eventBean;
    }
    if ( ( arrProcessBeans == null ) || ( arrProcessBeans.length < 1 ) ) {
      DLog.warning( lctx , "Failed to create event bean "
          + ", found empty array of process beans" );
      return eventBean;
    }
    if ( StringUtils.isBlank( outgoingNumber ) ) {
      DLog.warning( lctx , "Failed to create event bean "
          + ", found blank outgoing number" );
      return eventBean;
    }

    // clean params

    comment = StringUtils.trimToEmpty( comment );
    senderId = StringUtils.trimToEmpty( senderId );

    // compose event bean

    eventBean = new EventBean();
    eventBean.setEventName( eventName );
    eventBean.setStartDate( startDate );
    eventBean.setEndDate( endDate );
    eventBean.setRemindDate( new Date() );
    eventBean.setRemindFreq( "NEVER" );
    eventBean.setCatagoryID( categoryId );
    eventBean.setClientID( clientId );
    eventBean.setNumCodes( numberCodes );
    eventBean.setCodeLength( lengthCodes );
    eventBean.setComment( comment );
    eventBean.setProcessType( processType );
    eventBean.setBudget( 0 );
    eventBean.setUsedBudget( 0 );
    eventBean.setOverbudgetDate( new Date( 0L ) );
    eventBean.setChannel( channel );
    eventBean.setMobileMenuEnabled( false );
    eventBean.setMobileMenuName( "" );
    eventBean.setUnsubscribeImmediate( false );
    eventBean.setBitFlags( bitFlags );
    eventBean.setMobileMenuBrandName( "" );
    eventBean.setUnsubscribeResponse( "" );
    eventBean.setOutgoingNumber( outgoingNumber );
    eventBean.setClientEventID( "" );
    eventBean.setSenderID( senderId );
    eventBean.setDisplay( true );
    eventBean.setSuspend( false );
    eventBean.setActive( true );

    // build string process steps

    ProcessService processService = new ProcessService();
    eventBean.setProcess( processService.encode( arrProcessBeans ) );

    return eventBean;
  }

  private static ProcessBean firstProcessBean( String response ) {
    ProcessBean processBean = new ProcessBean();
    processBean.setStep( "1" );
    processBean.setType( "CODE" );
    processBean.setParamLabel( "(N/A)" );
    processBean.setNames( new String[] { "(N/A)" } );
    processBean.setResponse( response );
    processBean.setNextStep( "END" );
    processBean.setRfa( "" );
    return processBean;
  }

}
