package com.beepcast.model.groupClientConnection;

import com.beepcast.database.DatabaseLibrary.QueryItem;

public class GroupClientConnectionQuery {

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT id , name , active ";
    String sqlFrom = "FROM group_client_connection ";
    String sql = sqlSelect + sqlFrom;
    return sql;
  }

  public static GroupClientConnectionBean populateRecord( QueryItem qi ) {
    GroupClientConnectionBean groupClientConnectionBean = null;

    if ( qi == null ) {
      return groupClientConnectionBean;
    }

    String stemp = null;

    groupClientConnectionBean = new GroupClientConnectionBean();

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        groupClientConnectionBean.setId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      groupClientConnectionBean.setName( stemp );
    }

    stemp = (String) qi.get( 2 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      groupClientConnectionBean.setActive( stemp.equals( "1" ) );
    }

    return groupClientConnectionBean;
  }

}
