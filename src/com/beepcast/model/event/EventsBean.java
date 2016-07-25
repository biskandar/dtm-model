package com.beepcast.model.event;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.beepcast.util.Util;

/*******************************************************************************
 * Events Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class EventsBean implements Serializable {

  private static final long serialVersionUID = -8988457852399594856L;

  private Vector eventNames;
  private Map events;
  private String newEventName;
  private Exception _exception;
  private long clientID;
  private boolean showInactiveEvents;
  private boolean channel;
  private int processType;

  public EventsBean() {
    showInactiveEvents = true;
    channel = false;
    processType = -1;
  }

  public void setEventNames( Vector eventNames ) {
    this.eventNames = eventNames;
  }

  public Vector getEventNames() {
    if ( eventNames != null ) {
      return eventNames;
    }

    // create vector of event names
    eventNames = new Vector();

    // refresh to load new events
    events = getEvents();
    if ( events == null ) {
      return eventNames;
    }

    // iterate and filter all events
    Iterator iter = events.keySet().iterator();
    while ( iter.hasNext() ) {
      EventBean eb = (EventBean) events.get( (String) iter.next() );
      if ( eb == null ) {
        continue;
      }
      try {
        CatagoryBean catagory = new CatagoryBean().select( eb.getCatagoryID() );

        // bypass for category beepid
        if ( catagory.getCatagoryName().equals( "BEEPID" ) ) {
          continue;
        }

        // is it include inactive events ~ invalid codes
        if ( !showInactiveEvents ) {
          String codes = eb.getCodes();
          if ( ( codes == null ) || codes.equals( "" ) ) {
            continue;
          }
        }

        // show only channel or event
        if ( channel ) {
          if ( !eb.getChannel() ) {
            continue;
          }
        } else {
          if ( eb.getChannel() )
            continue;
        }

        // bypass invalid / null process type
        if ( processType != -1 ) {
          if ( eb.getProcessType() != processType )
            continue;
        }

        // store event name
        eventNames.addElement( eb.getEventName() );

      } catch ( Exception e ) {
      }
    }

    // sort vector of event names
    Util.sortVector( eventNames );

    return eventNames;
  }

  public void setEvents( Hashtable events ) {
    this.events = events;
  }

  public Map getEvents() {
    if ( events != null ) {
      return events;
    }
    try {
      events = new EventDAO().selectAll( clientID , new Boolean( true ) , null ,
          null );
    } catch ( Exception e ) {
      _exception = e;
    }
    return events;
  }

  public void setNewEventName( String newEventName ) {
    this.newEventName = newEventName;
    events = null;
    eventNames = null;
  }

  public String getNewEventName() {
    String temp = newEventName;
    newEventName = null;
    return temp;
  }

  public void setException( Exception _exception ) {
    this._exception = _exception;
  }

  public Exception getException() {
    return _exception;
  }

  public void setClientID( long clientID ) {
    this.clientID = clientID;
    events = null;
    eventNames = null;
  }

  public long getClientID() {
    return clientID;
  }

  public void setShowInactiveEvents( boolean showInactiveEvents ) {
    this.showInactiveEvents = showInactiveEvents;
    events = null;
    eventNames = null;
  }

  public boolean getShowInactiveEvents() {
    return showInactiveEvents;
  }

  public void setChannel( boolean channel ) {
    this.channel = channel;
    events = null;
    eventNames = null;
  }

  public boolean getChannel() {
    return channel;
  }

  public void setProcessType( int processType ) {
    this.processType = processType;
    events = null;
    eventNames = null;
  }

  public boolean getProcessType() {
    return channel;
  }

} // eof
