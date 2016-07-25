package com.beepcast.model.clientFile;

import com.beepcast.database.DatabaseLibrary.QueryItem;

public class ClientFileQuery {

  public static String sqlSelectFrom() {
    String sqlSelectFrom = sqlSelect() + sqlFrom();
    return sqlSelectFrom;
  }

  public static String sqlSelect() {
    String sqlSelect = "SELECT id , client_id , caption ";
    sqlSelect += ", file_name , file_type , web_link ";
    sqlSelect += ", size_bytes , length , width , active ";
    return sqlSelect;
  }

  public static String sqlFrom() {
    String sqlFrom = "FROM client_file ";
    return sqlFrom;
  }

  public static ClientFileBean populateRecord( QueryItem qi ) {
    ClientFileBean bean = new ClientFileBean();

    String stemp;
    int itemp;

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setClientId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 2 ); // caption
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setCaption( stemp );
    }

    stemp = (String) qi.get( 3 ); // file_name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setFileName( stemp );
    }

    stemp = (String) qi.get( 4 ); // file_type
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setFileType( stemp );
    }

    stemp = (String) qi.get( 5 ); // web_link
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setWebLink( stemp );
    }

    stemp = (String) qi.get( 6 ); // size_bytes
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setSizeBytes( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 7 ); // length
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setLength( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 8 ); // width
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setWidth( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 9 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setActive( stemp.equals( "1" ) );
    }

    return bean;
  }

}
