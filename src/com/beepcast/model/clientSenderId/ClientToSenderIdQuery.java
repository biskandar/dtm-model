package com.beepcast.model.clientSenderId;

import com.beepcast.database.DatabaseLibrary.QueryItem;

public class ClientToSenderIdQuery {

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT id , client_id , outgoing_number ";
    sqlSelect += ", senderID , description , active ";
    String sqlFrom = "FROM client_to_senderid ";
    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  public static ClientToSenderIdBean populateRecord( QueryItem qi ) {
    ClientToSenderIdBean bean = null;
    if ( qi == null ) {
      return bean;
    }

    String stemp = null;

    bean = new ClientToSenderIdBean();

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setClientId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 2 ); // outgoing_number
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setOutgoingNumber( stemp );
    }

    stemp = (String) qi.get( 3 ); // senderID
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setSenderId( stemp );
    }

    stemp = (String) qi.get( 4 ); // description
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDescription( stemp );
    }

    stemp = (String) qi.get( 5 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setActive( stemp.equals( "1" ) );
    }

    return bean;
  }

}
