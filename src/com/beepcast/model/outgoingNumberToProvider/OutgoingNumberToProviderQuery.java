package com.beepcast.model.outgoingNumberToProvider;

import com.beepcast.database.DatabaseLibrary.QueryItem;

public class OutgoingNumberToProviderQuery {

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT id , outgoing_number , `level` ";
    sqlSelect += ", country_code , prefix_number , telco_code ";
    sqlSelect += ", provider_id , group_connection_id , masked ";
    sqlSelect += ", description , priority , active , suspend ";
    String sqlFrom = "FROM outgoing_number_to_provider ";
    String sql = sqlSelect + sqlFrom;
    return sql;
  }

  public static OutgoingNumberToProviderBean populateRecord( QueryItem qi ) {
    OutgoingNumberToProviderBean bean = null;

    if ( qi == null ) {
      return bean;
    }

    String stemp = null;

    bean = new OutgoingNumberToProviderBean();

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // outgoing_number
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setOutgoingNumber( stemp );
    }

    stemp = (String) qi.get( 2 ); // level
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setLevel( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 3 ); // country_code
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setCountryCode( stemp );
    }

    stemp = (String) qi.get( 4 ); // prefix_number
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setPrefixNumber( stemp );
    }

    stemp = (String) qi.get( 5 ); // telco_code
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setTelcoCode( stemp );
    }

    stemp = (String) qi.get( 6 ); // provider_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setProviderId( stemp );
    }

    stemp = (String) qi.get( 7 ); // group_connection_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setGroupConnectionId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 8 ); // masked
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setMasked( stemp );
    }

    stemp = (String) qi.get( 9 ); // description
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDescription( stemp );
    }

    stemp = (String) qi.get( 10 ); // priority
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setPriority( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 11 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setActive( stemp.equals( "1" ) );
    }

    stemp = (String) qi.get( 12 ); // suspend
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setSuspend( stemp.equals( "1" ) );
    }

    return bean;
  }

}
