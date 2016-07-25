package com.beepcast.model.user;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.model.util.DateTimeFormat;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UsersDAO {

  static final DLogContext lctx = new SimpleContext( "UsersDAO" );

  private DatabaseLibrary dbLib;

  public UsersDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public UsersBean selectActiveUsersBean() {
    UsersBean usersBean = null;

    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    String sqlOrder = "ORDER BY id ASC ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    usersBean = populateBeans( sql );
    return usersBean;
  }

  public UsersBean selectBasedOnEmail( String email ) {
    UsersBean usersBean = null;

    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( email = '" + StringEscapeUtils.escapeSql( email )
        + "' ) ";
    String sqlOrder = "ORDER BY id ASC ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    usersBean = populateBeans( sql );
    return usersBean;
  }

  public UsersBean selectBasedOnPhone( String phone ) {
    UsersBean usersBean = null;

    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( phone = '" + StringEscapeUtils.escapeSql( phone )
        + "' ) ";
    String sqlOrder = "ORDER BY id ASC ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    usersBean = populateBeans( sql );
    return usersBean;
  }

  public UsersBean selectBasedOnUserIds( String strUserIds ) {
    UsersBean usersBean = null;

    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( id IN ( " + strUserIds + " ) ) ";
    String sqlOrder = "ORDER BY id ASC ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    usersBean = populateBeans( sql );
    return usersBean;
  }

  public UsersBean selectBasedOnClientId( int clientId ) {
    UsersBean usersBean = null;

    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";
    String sqlOrder = "ORDER BY id ASC ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    usersBean = populateBeans( sql );
    return usersBean;
  }

  private UsersBean populateBeans( String sql ) {
    UsersBean usersBean = new UsersBean();

    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        UserBean userBean = populateBean( rs );
        if ( userBean != null ) {
          usersBean.addUserBean( userBean );
        }
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to query user bean , " + e );
    } finally {
      conn.disconnect( true );
    }

    return usersBean;
  }

  private UserBean populateBean( ResultSet rs ) {
    UserBean userBean = new UserBean();
    try {

      userBean.setId( rs.getInt( "id" ) );
      userBean.setUserID( rs.getString( "user_id" ) );
      userBean.setPassword( rs.getString( "password" ) );
      userBean.setRoles( StringUtils.split( rs.getString( "roles" ) , "," ) );
      userBean.setName( rs.getString( "name" ) );
      userBean.setPhone( rs.getString( "phone" ) );
      userBean.setEmail( rs.getString( "email" ) );
      userBean.setClientID( (long) rs.getDouble( "client_id" ) );
      userBean.setProxyID( (long) rs.getDouble( "proxy_id" ) );

      userBean.setDisplay( rs.getBoolean( "display" ) );
      userBean.setActive( rs.getBoolean( "active" ) );
      userBean.setDateInserted( DateTimeFormat.convertToDate( rs
          .getString( "date_inserted" ) ) );
      userBean.setDateUpdated( DateTimeFormat.convertToDate( rs
          .getString( "date_updated" ) ) );

    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to populate user bean , " + e );
    }

    return userBean;
  }

}
