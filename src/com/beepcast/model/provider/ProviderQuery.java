package com.beepcast.model.provider;

import com.beepcast.database.DatabaseLibrary.QueryItem;

public class ProviderQuery {

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT id , master_id , provider_id , provider_name ";
    sqlSelect += ", direction , `type` , short_code , country_code , access_url ";
    sqlSelect += ", access_username , access_password , listener_url , in_credit_cost ";
    sqlSelect += ", ou_credit_cost , description , active ";
    String sqlFrom = "FROM provider ";
    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  public static ProviderBean populateRecord( QueryItem qi ) {
    ProviderBean providerBean = null;

    if ( qi == null ) {
      return providerBean;
    }

    String stemp;

    providerBean = new ProviderBean();

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        providerBean.setId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // master_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        providerBean.setMasterId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 2 ); // provider_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setProviderId( stemp );
    }

    stemp = (String) qi.get( 3 ); // provider_name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setProviderName( stemp );
    }

    stemp = (String) qi.get( 4 ); // direction
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setDirection( stemp );
    }

    stemp = (String) qi.get( 5 ); // type
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setType( stemp );
    }

    stemp = (String) qi.get( 6 ); // short_code
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setShortCode( stemp );
    }

    stemp = (String) qi.get( 7 ); // country_code
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setCountryCode( stemp );
    }

    stemp = (String) qi.get( 8 ); // access_url
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setAccessUrl( stemp );
    }

    stemp = (String) qi.get( 9 ); // access_username
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setAccessUsername( stemp );
    }

    stemp = (String) qi.get( 10 ); // access_password
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setAccessPassword( stemp );
    }

    stemp = (String) qi.get( 11 ); // listener_url
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setListenerUrl( stemp );
    }

    stemp = (String) qi.get( 12 ); // in_credit_cost
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        providerBean.setInCreditCost( Double.parseDouble( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 13 ); // ou_credit_cost
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        providerBean.setOuCreditCost( Double.parseDouble( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 14 ); // description
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setDescription( stemp );
    }

    stemp = (String) qi.get( 15 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      providerBean.setActive( stemp.equals( "1" ) );
    }

    return providerBean;
  }

}
