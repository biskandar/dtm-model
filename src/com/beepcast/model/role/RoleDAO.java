package com.beepcast.model.role;

import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.model.util.DateTimeFormat;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class RoleDAO {

  static final DLogContext lctx = new SimpleContext( "RoleDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public RoleDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public boolean insertRoleBean( RoleBean roleBean ) {
    boolean result = false;

    // read params

    String role = roleBean.getRole();
    String childRoles = roleBean.getChildRoles();
    String menus = roleBean.getMenus();
    boolean active = roleBean.isActive();
    Date dateInserted = roleBean.getDateInserted();
    Date dateUpdated = roleBean.getDateUpdated();

    // clean params

    role = ( role == null ) ? "" : role.trim();
    childRoles = ( childRoles == null ) ? "" : childRoles.trim();
    menus = ( menus == null ) ? "" : menus.trim();
    dateInserted = ( dateInserted == null ) ? new Date() : dateInserted;
    dateUpdated = ( dateUpdated == null ) ? new Date() : dateUpdated;

    // converted params

    int intActive = active ? 1 : 0;
    String strDateInserted = DateTimeFormat.convertToString( dateInserted );
    String strDateUpdated = DateTimeFormat.convertToString( dateUpdated );

    // compose sql

    String sqlInsert = "INSERT INTO `role` ( role , child_roles , menus ";
    sqlInsert += ", active , date_inserted , date_updated ) ";
    String sqlValues = "VALUES ('" + StringEscapeUtils.escapeSql( role )
        + "','" + StringEscapeUtils.escapeSql( childRoles ) + "','"
        + StringEscapeUtils.escapeSql( menus ) + "'," + intActive + ",'"
        + strDateInserted + "','" + strDateUpdated + "') ";
    String sql = sqlInsert + sqlValues;

    // debug

    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public RoleBean selectBasedOnRole( String role ) {
    RoleBean roleBean = null;

    // compose sql

    String sqlSelectFrom = RoleQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( role = '" + StringEscapeUtils.escapeSql( role ) + "' ) ";
    String sqlOrderBy = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrderBy + sqlLimit;

    // execute sql

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return roleBean;
    }

    // populate record

    Iterator iter = qr.iterator();
    if ( iter.hasNext() ) {
      roleBean = RoleQuery.populateRecord( (QueryItem) iter.next() );
    }

    return roleBean;
  }

  public boolean delete( String role ) {
    boolean result = false;

    // compose sql

    String sqlDelete = "UPDATE role ";
    String sqlSet = "SET active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( role = '" + StringEscapeUtils.escapeSql( role ) + "' ) ";
    String sql = sqlDelete + sqlSet + sqlWhere;

    // debug

    if ( opropsApp.getBoolean( "Model.DebugAllSqlDelete" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

}
