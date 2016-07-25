package com.beepcast.model.role;

import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.model.util.DateTimeFormat;

public class RoleQuery {

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT id , role , child_roles , menus ";
    sqlSelect += ", active , date_inserted , date_updated ";
    String sqlFrom = "FROM `role` ";
    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  public static RoleBean populateRecord( QueryItem qi ) {
    RoleBean roleBean = null;

    if ( qi == null ) {
      return roleBean;
    }

    roleBean = new RoleBean();

    String stemp = null;

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        roleBean.setId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // role
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      roleBean.setRole( stemp );
    }

    stemp = (String) qi.get( 2 ); // child_roles
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      roleBean.setChildRoles( stemp );
    }

    stemp = (String) qi.get( 3 ); // menus
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      roleBean.setMenus( stemp );
    }

    stemp = (String) qi.get( 4 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      roleBean.setActive( stemp.equals( "1" ) );
    }

    stemp = (String) qi.get( 5 ); // date_inserted
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      roleBean.setDateInserted( DateTimeFormat.convertToDate( stemp ) );
    }

    stemp = (String) qi.get( 6 ); // date_updated
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      roleBean.setDateUpdated( DateTimeFormat.convertToDate( stemp ) );
    }

    return roleBean;
  }

}
