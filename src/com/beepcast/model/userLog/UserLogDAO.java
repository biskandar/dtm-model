package com.beepcast.model.userLog;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UserLogDAO {

  static final DLogContext lctx = new SimpleContext( "UserLogDAO" );

  private DatabaseLibrary dbLib;

  public UserLogDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insert( UserLogBean bean ) {
    boolean result = false;

    int userId = bean.getUserId();
    String visitId = bean.getVisitId();
    String action = bean.getAction();
    int active = bean.isActive() ? 1 : 0;

    String sqlInsert = "INSERT INTO user_log(user_id,visit_id,action,active,date_inserted,date_updated) ";
    String sqlValues = "VALUES (" + userId + ",'"
        + StringEscapeUtils.escapeSql( visitId ) + "','"
        + StringEscapeUtils.escapeSql( action ) + "'," + active
        + ",NOW(),NOW()) ";

    String sql = sqlInsert + sqlValues;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt == null ) || ( irslt.intValue() < 1 ) ) {
      return result;
    }

    result = true;
    return result;
  }

  public boolean delete( int userId , String visitId ) {
    boolean result = false;

    String sqlUpdate = "UPDATE user_log ";
    String sqlSet = "SET active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( user_id = " + userId + " ) ";
    if ( !StringUtils.isBlank( visitId ) ) {
      sqlWhere += "AND ( visit_id = '" + StringEscapeUtils.escapeSql( visitId )
          + "' ) ";
    }

    String sql = sqlUpdate + sqlSet + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt == null ) || ( irslt.intValue() < 1 ) ) {
      return result;
    }

    result = true;
    return result;
  }

  public boolean isExist( int userId , String visitId ) {
    boolean result = false;

    String sqlSelect = "SELECT id ";
    String sqlFrom = "FROM user_log ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( user_id = " + userId + " ) ";
    if ( !StringUtils.isBlank( visitId ) ) {
      sqlWhere += "AND ( visit_id = '" + StringEscapeUtils.escapeSql( visitId )
          + "' ) ";
    }
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;

    QueryResult qr = dbLib.simpleQuery( "transactiondb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return result;
    }

    result = true;
    return result;
  }

}
