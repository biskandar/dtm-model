package com.beepcast.model.clientSenderId;

import com.beepcast.database.DatabaseLibrary.QueryItem;

public class ClientToSenderIdInfoQuery {

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT ctsi.id , ctsi.client_id , c.company_name ";
    sqlSelect += ", ctsi.outgoing_number , ctsi.senderID , ctsi.description ";
    sqlSelect += ", ctsi.active ";
    String sqlFrom = "FROM client_to_senderid ctsi ";
    sqlFrom += "INNER JOIN client c ON ( c.client_id = ctsi.client_id ) ";
    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  public static ClientToSenderIdInfoBean populateRecord( QueryItem qi ) {
    ClientToSenderIdInfoBean bean = null;
    if ( qi == null ) {
      return bean;
    }

    String stemp = null;

    bean = new ClientToSenderIdInfoBean();

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

    stemp = (String) qi.get( 2 ); // company_name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setCompanyName( stemp );
    }

    stemp = (String) qi.get( 3 ); // outgoing_number
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setOutgoingNumber( stemp );
    }

    stemp = (String) qi.get( 4 ); // senderID
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setSenderId( stemp );
    }

    stemp = (String) qi.get( 5 ); // description
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDescription( stemp );
    }

    stemp = (String) qi.get( 6 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setActive( stemp.equals( "1" ) );
    }

    return bean;
  }

}
