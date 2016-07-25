package com.beepcast.model.specialMessage;

import com.beepcast.database.DatabaseLibrary.QueryItem;

public class SpecialMessageQuery {

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT id , `type` , name , content , description ";
    String sqlFrom = "FROM special_message ";
    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  public static SpecialMessageBean populateRecord( QueryItem qi ) {
    SpecialMessageBean bean = null;

    if ( qi == null ) {
      return bean;
    }

    bean = new SpecialMessageBean();

    String stemp;

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // type
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setType( stemp );
    }

    stemp = (String) qi.get( 2 ); // name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setName( stemp );
    }

    stemp = (String) qi.get( 3 ); // content
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setContent( stemp );
    }

    stemp = (String) qi.get( 4 ); // description
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDescription( stemp );
    }

    return bean;
  }

}
