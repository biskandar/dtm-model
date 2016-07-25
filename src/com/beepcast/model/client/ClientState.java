package com.beepcast.model.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ClientState {

  public static final String TRIAL = "TRIAL";
  public static final String TEST = "TEST";
  public static final String PRIVATE = "PRIVATE";
  public static final String PUBLIC = "PUBLIC";
  public static final String DISABLE = "DISABLE";
  public static final String SUSPEND = "SUSPEND";
  public static final String SUSPEND_LOGIN = "SUSPEND_LOGIN";
  public static final String SUSPEND_TRAFFIC = "SUSPEND_TRAFFIC";

  private List listStates;

  public ClientState() {
    listStates = new ArrayList();
    listStates.add( DISABLE );
    listStates.add( PRIVATE );
    listStates.add( PUBLIC );
    listStates.add( SUSPEND );
    listStates.add( SUSPEND_LOGIN );
    listStates.add( SUSPEND_TRAFFIC );
    listStates.add( TEST );
    listStates.add( TRIAL );
  }

  public boolean isValidState( String state ) {
    int idx = listStates.indexOf( state );
    return ( idx > -1 );
  }

  public Iterator iterStates() {
    return listStates.iterator();
  }

  public static String strStates( String[] arrStates ) {
    String states = null;
    if ( ( arrStates == null ) || ( arrStates.length < 1 ) ) {
      return states;
    }

    StringBuffer sb = null;

    for ( int idx = 0 ; idx < arrStates.length ; idx++ ) {
      String state = arrStates[idx];
      if ( StringUtils.isBlank( state ) ) {
        continue;
      }
      if ( sb == null ) {
        sb = new StringBuffer();
      } else {
        sb.append( "," );
      }
      sb.append( "'" );
      sb.append( state.trim() );
      sb.append( "'" );
    }

    if ( sb != null ) {
      states = sb.toString();
    }

    return states;
  }

  public static String strStates( List listStates ) {
    String states = null;
    if ( listStates == null ) {
      return states;
    }

    StringBuffer sb = null;

    Iterator iterStates = listStates.iterator();
    while ( iterStates.hasNext() ) {
      String state = (String) iterStates.next();
      if ( StringUtils.isBlank( state ) ) {
        continue;
      }
      if ( sb == null ) {
        sb = new StringBuffer();
      } else {
        sb.append( "," );
      }
      sb.append( "'" );
      sb.append( state.trim() );
      sb.append( "'" );
    }

    if ( sb != null ) {
      states = sb.toString();
    }

    return states;
  }

}
