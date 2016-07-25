package com.beepcast.model.role;

import java.util.Iterator;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class RolesDAO {

  static final DLogContext lctx = new SimpleContext( "RolesDAO" );

  private DatabaseLibrary dbLib;

  public RolesDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public RolesBean generateActiveRoleBeans() {
    RolesBean bean = null;

    // compose sql

    String sqlSelectFrom = RoleQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    String sqlOrderBy = "ORDER BY role ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrderBy;

    // execute and populate records

    bean = populateRecords( sql );
    return bean;
  }

  public RolesBean generateActiveRoleBeansByRoles( String strRoles ) {
    RolesBean bean = null;

    // compose sql

    String sqlSelectFrom = RoleQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    if ( ( strRoles != null ) && ( !strRoles.equals( "" ) ) ) {
      sqlWhere += "AND ( role IN ( " + strRoles + " ) ) ";
    }
    String sqlOrderBy = "ORDER BY role ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrderBy;

    // execute and populate records

    bean = populateRecords( sql );
    return bean;
  }

  public RolesBean generateActiveRoleBeansByNotInRoles( String notInRoles ) {
    RolesBean bean = null;

    // compose sql

    String sqlSelectFrom = RoleQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    if ( ( notInRoles != null ) && ( !notInRoles.equals( "" ) ) ) {
      sqlWhere += "AND ( role NOT IN ( " + notInRoles + " ) ) ";
    }
    String sqlOrderBy = "ORDER BY role ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrderBy;

    // execute and populate records

    bean = populateRecords( sql );
    return bean;
  }

  private RolesBean populateRecords( String sql ) {
    RolesBean rolesBean = new RolesBean();

    if ( sql == null ) {
      return rolesBean;
    }

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return rolesBean;
    }

    Iterator iter = qr.iterator();
    while ( iter.hasNext() ) {
      RoleBean roleBean = RoleQuery.populateRecord( (QueryItem) iter.next() );
      if ( roleBean == null ) {
        continue;
      }
      rolesBean.addRoleBean( roleBean );
    }

    return rolesBean;
  }

}
