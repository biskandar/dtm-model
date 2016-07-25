package com.beepcast.model.groupClientConnection;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GroupClientConnectionDAO {

  static final DLogContext lctx = new SimpleContext( "GroupClientConnectionDAO" );

  private OnlinePropertiesApp opropsApp;
  private DatabaseLibrary dbLib;

  public GroupClientConnectionDAO() {
    opropsApp = OnlinePropertiesApp.getInstance();
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insert( GroupClientConnectionBean bean ) {
    boolean result = false;

    // read params
    String name = bean.getName();

    // clean params
    name = ( name == null ) ? "" : name.trim();

    // compose sql
    String sqlInsert = "INSERT INTO group_client_connection ";
    sqlInsert += "(name,active,date_inserted,date_updated) ";
    String sqlValues = "VALUES('" + StringEscapeUtils.escapeSql( name )
        + "',1,NOW(),NOW()) ";
    String sql = sqlInsert + sqlValues;

    // log it
    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt == null ) || ( irslt.intValue() < 1 ) ) {
      return result;
    }

    result = true;
    return result;
  }

  public GroupClientConnectionBean selectById( int id ) {
    GroupClientConnectionBean bean = null;

    // compose sql
    String sqlSelectFrom = GroupClientConnectionQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( id = " + id + " ) ";
    String sql = sqlSelectFrom + sqlWhere;

    // execute sql
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }

    // populate record
    Iterator it = qr.iterator();
    if ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi != null ) {
        bean = GroupClientConnectionQuery.populateRecord( qi );
      }
    }

    return bean;
  }

  public boolean update( GroupClientConnectionBean bean ) {
    boolean result = false;

    // read params
    int id = bean.getId();
    String name = bean.getName();

    // clean params
    name = ( name == null ) ? "" : name.trim();

    // compose sql
    String sqlUpdate = "UPDATE group_client_connection ";
    String sqlSet = "SET name = '" + StringEscapeUtils.escapeSql( name ) + "' ";
    sqlSet += ", date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    // log it
    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt == null ) || ( irslt.intValue() < 1 ) ) {
      return result;
    }

    result = true;
    return result;
  }

  public boolean deleteById( int id ) {
    boolean result = false;

    // compose sql
    String sqlUpdate = "UPDATE group_client_connection ";
    String sqlSet = "SET active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    // log it
    if ( opropsApp.getBoolean( "Model.DebugAllSqlDelete" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt == null ) || ( irslt.intValue() < 1 ) ) {
      return result;
    }

    result = true;
    return result;
  }

}
