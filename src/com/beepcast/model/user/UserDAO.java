package com.beepcast.model.user;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.model.util.DateTimeFormat;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UserDAO {

  static final DLogContext lctx = new SimpleContext( "UserDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public UserDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public int insertUserBean( UserBean userBean ) {
    int result = 0;

    String userID = userBean.getUserID();

    UserBean userBeanTemp = selectBasedOnUserID( userID );
    if ( userBeanTemp != null ) {
      DLog.warning( lctx , "Failed to create new user bean "
          + ", found user id is already exist" );
      result = -1;
      return result;
    }

    String roles = StringUtils.trimToEmpty( StringUtils.join(
        userBean.getRoles() , "," ) );

    Date dateInserted = userBean.getDateInserted();
    Date dateUpdated = userBean.getDateUpdated();
    if ( dateInserted == null ) {
      dateInserted = new Date();
    }
    if ( dateUpdated == null ) {
      dateUpdated = new Date();
    }
    String strDateInserted = DateTimeFormat.convertToString( dateInserted );
    String strDateUpdated = DateTimeFormat.convertToString( dateUpdated );

    String sqlInsert = "INSERT INTO user ( user_id , password "
        + ", roles , name , phone , email , client_id , proxy_id "
        + ", display , active , date_inserted , date_updated ) ";

    String sqlValues = "VALUES ('" + StringEscapeUtils.escapeSql( userID )
        + "',PASSWORD('" + StringEscapeUtils.escapeSql( userBean.getPassword() )
        + "'),'" + StringEscapeUtils.escapeSql( roles ) + "','"
        + StringEscapeUtils.escapeSql( userBean.getName() ) + "','"
        + StringEscapeUtils.escapeSql( userBean.getPhone() ) + "','"
        + StringEscapeUtils.escapeSql( userBean.getEmail() ) + "',"
        + userBean.getClientID() + "," + userBean.getProxyID() + ","
        + ( userBean.isDisplay() ? 1 : 0 ) + ","
        + ( userBean.isActive() ? 1 : 0 ) + ",'" + strDateInserted + "','"
        + strDateUpdated + "') ";

    String sql = sqlInsert + sqlValues;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt == null ) || ( irslt.intValue() < 1 ) ) {
      return result;
    }

    userBeanTemp = selectBasedOnUserID( userID );
    if ( userBeanTemp != null ) {
      result = userBeanTemp.getId();
    }

    return result;
  }

  public boolean updateDisplay( int id , boolean display ) {
    boolean result = false;

    String sqlUpdate = "UPDATE user ";
    String sqlSet = "SET display = " + ( display ? 1 : 0 )
        + " , date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean updateActive( int id , boolean active ) {
    boolean result = false;

    String sqlUpdate = "UPDATE user ";
    String sqlSet = "SET active = " + ( active ? 1 : 0 )
        + " , date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean update( UserBean userBean ) {
    boolean result = false;

    String roles = StringUtils.trimToEmpty( StringUtils.join(
        userBean.getRoles() , "," ) );

    String sqlUpdate = "UPDATE user ";
    String sqlSet = "SET proxy_id = " + userBean.getProxyID() + " ";
    sqlSet += ", client_id = " + userBean.getClientID() + " ";
    sqlSet += ", email = '" + StringEscapeUtils.escapeSql( userBean.getEmail() )
        + "' ";
    sqlSet += ", phone = '" + StringEscapeUtils.escapeSql( userBean.getPhone() )
        + "' ";
    sqlSet += ", name = '" + StringEscapeUtils.escapeSql( userBean.getName() )
        + "' ";
    sqlSet += ", user_id = '"
        + StringEscapeUtils.escapeSql( userBean.getUserID() ) + "' ";
    sqlSet += ", roles = '" + roles + "' ";
    sqlSet += ", display = " + ( userBean.isDisplay() ? 1 : 0 ) + " ";
    sqlSet += ", active = " + ( userBean.isActive() ? 1 : 0 ) + " ";
    sqlSet += ", date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + userBean.getId() + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public UserBean selectBasedOnId( int id ) {
    UserBean bean = null;
    if ( id < 1 ) {
      return bean;
    }

    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;

    bean = selectFirstRow( sql );
    return bean;
  }

  public UserBean selectBasedOnUserID( String userID ) {
    UserBean bean = null;
    if ( userID == null ) {
      return bean;
    }

    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( user_id = '" + StringEscapeUtils.escapeSql( userID )
        + "' ) ";
    String sqlOrder = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder + sqlLimit;

    bean = selectFirstRow( sql );
    return bean;
  }

  public UserBean selectBasedOnEmail( String email ) {
    UserBean bean = null;
    if ( email == null ) {
      return bean;
    }

    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( email = '" + StringEscapeUtils.escapeSql( email )
        + "' ) ";
    String sqlOrder = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder + sqlLimit;

    bean = selectFirstRow( sql );
    return bean;
  }

  public UserBean selectBasedOnPhone( String phone ) {
    UserBean bean = null;
    if ( phone == null ) {
      return bean;
    }

    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( phone = '" + StringEscapeUtils.escapeSql( phone )
        + "' ) ";
    String sqlOrder = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder + sqlLimit;

    bean = selectFirstRow( sql );
    return bean;
  }

  public boolean updatePasswordFromUserId( String userId , String oldPassword ,
      String newPassword ) {
    boolean result = false;

    String sqlUpdate = "UPDATE user ";
    String sqlSet = "SET `password` = PASSWORD( '"
        + StringEscapeUtils.escapeSql( newPassword )
        + "' ) , date_updated = NOW() , date_password_updated = NOW() ";
    String sqlWhere = "WHERE ( user_id = '"
        + StringEscapeUtils.escapeSql( userId ) + "' ) ";
    if ( oldPassword != null ) {
      sqlWhere += "AND ( `password` = PASSWORD( '"
          + StringEscapeUtils.escapeSql( oldPassword ) + "' ) ) ";
    }

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean verifyPasswordFromUserId( String userId , String password ) {
    boolean result = false;

    String sqlSelect = "SELECT id ";
    String sqlFrom = "FROM user ";
    String sqlWhere = "WHERE ( user_id = '"
        + StringEscapeUtils.escapeSql( userId ) + "' ) ";
    sqlWhere += "AND ( `password` = PASSWORD('"
        + StringEscapeUtils.escapeSql( password ) + "') ) ";
    sqlWhere += "AND ( active = 1 ) ";
    String sqlOrder = "ORDER BY id ASC ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder + sqlLimit;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlSelect" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      result = true;
    }

    return result;
  }

  private UserBean selectFirstRow( String sql ) {
    UserBean bean = null;
    if ( sql == null ) {
      return bean;
    }
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      if ( rs.next() ) {
        bean = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to query user bean , " + e );
    } finally {
      conn.disconnect( true );
    }
    return bean;
  }

  private UserBean populateBean( ResultSet rs ) {
    UserBean userBean = new UserBean();
    try {

      userBean.setId( rs.getInt( "id" ) );
      userBean.setUserID( rs.getString( "user_id" ) );
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
      userBean.setDatePasswordUpdated( DateTimeFormat.convertToDate( rs
          .getString( "date_password_updated" ) ) );

    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to populate user bean , " + e );
    }

    return userBean;
  }

}
