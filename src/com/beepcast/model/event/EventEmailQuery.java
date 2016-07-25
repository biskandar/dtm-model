package com.beepcast.model.event;

import com.beepcast.database.DatabaseLibrary.QueryItem;

public class EventEmailQuery {

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT id , event_id , process_step , email_clob ";
    String sqlFrom = "FROM event_email ";
    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  public static EventEmailBean populateRecord( QueryItem qi ) {
    EventEmailBean bean = null;

    if ( qi == null ) {
      return bean;
    }

    bean = new EventEmailBean();

    String stemp = null;

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // event_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setEventId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 2 ); // process_step
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setProcessStep( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 3 ); // email_clob
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setEmailClob( stemp );
    }

    return bean;
  }

}
