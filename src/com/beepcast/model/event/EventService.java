package com.beepcast.model.event;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;

import com.beepcast.model.beepcode.BeepcodeSupport;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EventService {

  static final DLogContext lctx = new SimpleContext( "EventService" );

  private EventDAO dao;

  public EventService() {
    dao = new EventDAO();
  }

  public boolean persist( EventBean eventBean ) {
    boolean result = false;

    // validate parameters

    if ( eventBean == null ) {
      DLog.warning( lctx , "Failed to persist , found null event bean" );
      return result;
    }

    // insert event bean

    try {
      eventBean.setEventID( insert( eventBean ) );
    } catch ( IOException e ) {
      DLog.warning( lctx , "Failed to persist "
          + ", found failed insert into event table , " + e );
      return result;
    }

    // verify event bean after insert

    if ( eventBean.getEventID() < 1 ) {
      DLog.warning( lctx , "Failed to persist "
          + ", found failed insert into event table" );
      return result;
    }

    // build event codes
    BeepcodeSupport beepcodeSupport = new BeepcodeSupport();
    String codes = beepcodeSupport.allocateCodes( eventBean );
    if ( StringUtils.isBlank( codes ) ) {
      DLog.warning( lctx , "Failed to persist "
          + ", found failed to build event codes " );
      try {
        delete( eventBean );
      } catch ( IOException e ) {
      }
      return result;
    }

    // allocate event codes

    eventBean.setCodes( codes );
    if ( !updateChannelCodes( eventBean.getEventID() , eventBean.getCodes() ) ) {
      DLog.warning( lctx , "Failed to persist "
          + ", found failed to allocate event codes" );
      try {
        delete( eventBean );
      } catch ( IOException e ) {
      }
      return result;
    }

    result = true;
    return result;
  }

  public long insert( EventBean eventBean ) throws IOException {
    return dao.insert( eventBean );
  }

  public EventBean select( long eventID ) throws IOException {
    return dao.select( eventID );
  }

  public EventBean select( String eventName , long clientID )
      throws IOException {
    return dao.select( eventName , clientID , false );
  }

  public EventBean selectChannel( String channelName , long clientID )
      throws IOException {
    return dao.select( channelName , clientID , true );
  }

  public LinkedHashMap selectAll( EventBean eventBean ) throws IOException {
    return dao.selectAll( eventBean.getClientID() , null , null , null );
  }

  public LinkedHashMap selectAllActive( EventBean eventBean )
      throws IOException {
    return dao.selectAll( eventBean.getClientID() , new Boolean( true ) , null ,
        null );
  }

  public LinkedHashMap selectAllDisplay( EventBean eventBean )
      throws IOException {
    return dao.selectAll( eventBean.getClientID() , new Boolean( true ) ,
        new Boolean( true ) , null );
  }

  public LinkedHashMap selectAllDisplayOnly( EventBean eventBean )
      throws IOException {
    return dao.selectAll( eventBean.getClientID() , null , new Boolean( true ) ,
        null );
  }

  public LinkedHashMap selectAllDisplayIncoming( EventBean eventBean )
      throws IOException {
    return dao.selectAll( eventBean.getClientID() , new Boolean( true ) ,
        new Boolean( true ) , new Boolean( false ) );
  }

  public LinkedHashMap selectAllDisplayOutgoing( EventBean eventBean )
      throws IOException {
    return dao.selectAll( eventBean.getClientID() , new Boolean( true ) ,
        new Boolean( true ) , new Boolean( true ) );
  }

  public String[] selectChannelIDs( java.util.Date dateTm ) throws IOException {
    return dao.selectChannelIDs( dateTm );
  }

  public int totalActiveEvents( int clientId ) {
    return dao.totalActiveEvents( clientId );
  }

  public boolean updateChannelCodes( long eventID , String codes ) {
    boolean result = false;

    if ( eventID < 1 ) {
      DLog.warning( lctx , "Failed to update channel codes "
          + ", found zero eventID" );
      return result;
    }

    if ( ( codes == null ) || ( codes.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to update channel codes "
          + ", found empty codes" );
      return result;
    }

    result = dao.updateChannelCodes( eventID , codes );

    return result;
  }

  public void updatePingCount( EventBean eventBean ) throws IOException {
    dao.updatePingCount( eventBean );
  }

  public boolean update( EventBean eventBean ) throws IOException {
    return dao.update( eventBean );
  }

  public void setActive( EventBean eventBean ) throws IOException {
    dao.setActive( eventBean );
  }

  public void setInactive( EventBean eventBean ) throws IOException {
    dao.setInactive( eventBean );
  }

  public boolean updateSuspend( EventBean eventBean , boolean suspend ) {
    boolean result = false;
    if ( eventBean == null ) {
      DLog.warning( lctx , "Failed to update suspend , found null event bean" );
      return result;
    }
    long eventId = eventBean.getEventID();
    if ( eventId < 1 ) {
      DLog.warning( lctx , "Failed to update suspend , found zero event id" );
      return result;
    }
    result = dao.updateSuspend( eventId , suspend );
    return result;
  }

  public void setHidden( EventBean eventBean ) throws IOException {
    dao.setHidden( eventBean );
  }

  public void setShown( EventBean eventBean ) throws IOException {
    dao.setShown( eventBean );
  }

  public void delete( EventBean eventBean ) throws IOException {
    dao.delete( eventBean );
  }

}
