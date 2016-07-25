package com.beepcast.model.user;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.model.client.ClientType;
import com.beepcast.model.event.ListEventService;
import com.beepcast.model.util.DateTimeFormat;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListUserDAO {

  static final DLogContext lctx = new SimpleContext( "ListUserDAO" );

  private DatabaseLibrary dbLib;

  public ListUserDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalActiveUsers( int clientType , int masterClientId ,
      String keywordUserId , String keywordUserName , int display ) {
    long totalRecords = 0;

    // compose sql
    String sqlSelect = "SELECT COUNT(u.id) AS total ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( clientType , masterClientId , keywordUserId ,
        keywordUserName , display );
    String sql = sqlSelect + sqlFrom + sqlWhere;

    // execute and fetch query
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      Iterator iqr = qr.iterator();
      QueryItem qi = null;
      if ( iqr.hasNext() ) {
        qi = (QueryItem) iqr.next();
      }
      if ( qi != null ) {
        try {
          totalRecords = Long.parseLong( qi.getFirstValue() );
        } catch ( NumberFormatException e ) {
        }
      }
    }

    return totalRecords;
  }

  public List selectActiveUsers( int clientType , int masterClientId ,
      String keywordUserId , String keywordUserName , int display , int top ,
      int limit , int orderby ) {
    List userBeans = new ArrayList();

    // compose sql
    String sqlSelect = "SELECT u.*,c.company_name ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( clientType , masterClientId , keywordUserId ,
        keywordUserName , display );

    String sqlOrderBy = "";
    switch ( orderby ) {
    case ListUserService.ORDERBY_ID_ASC :
      sqlOrderBy = "ORDER BY u.id ASC ";
      break;
    case ListUserService.ORDERBY_ID_DESC :
      sqlOrderBy = "ORDER BY u.id DESC ";
      break;
    case ListUserService.ORDERBY_USERID_ASC :
      sqlOrderBy = "ORDER BY u.user_id ASC ";
      break;
    case ListUserService.ORDERBY_USERID_DESC :
      sqlOrderBy = "ORDER BY u.user_id DESC ";
      break;
    }

    String sqlLimit = "LIMIT " + top + " , " + limit + " ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrderBy + sqlLimit;

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        UserBean userBean = populateBean( rs );
        userBeans.add( userBean );
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to select active users , " + e );
    } finally {
      conn.disconnect( true );
    }

    return userBeans;
  }

  private String sqlFrom() {
    String sqlFrom = "FROM user u ";
    sqlFrom += "INNER JOIN client c ON ( c.client_id = u.proxy_id ) "
        + "OR ( ( c.client_id = u.client_id ) AND ( u.proxy_id = 0 ) )";
    return sqlFrom;
  }

  private String sqlWhere( int clientType , int masterClientId ,
      String keywordUserId , String keywordUserName , int display ) {
    String sqlWhere = "WHERE ( u.active = 1 ) ";
    if ( clientType != ClientType.SUPER ) {
      if ( clientType == ClientType.MASTER ) {
        sqlWhere += "AND ( c.master_client_id = " + masterClientId + " ) ";
      } else {
        sqlWhere += "AND ( c.client_id = " + masterClientId + " ) ";
      }
    }
    if ( !StringUtils.isBlank( keywordUserId ) ) {
      sqlWhere += "AND ( u.user_id LIKE '%"
          + StringEscapeUtils.escapeSql( keywordUserId ) + "%' ) ";
    }
    if ( !StringUtils.isBlank( keywordUserName ) ) {
      sqlWhere += "AND ( u.name LIKE '%"
          + StringEscapeUtils.escapeSql( keywordUserName ) + "%' ) ";
    }
    switch ( display ) {
    case ListEventService.DISPLAY_ON :
      sqlWhere += "AND ( u.display = 1 ) ";
      break;
    case ListEventService.DISPLAY_OFF :
      sqlWhere += "AND ( u.display = 0 ) ";
      break;
    }
    return sqlWhere;
  }

  private UserBean populateBean( ResultSet rs ) {
    UserBean userBean = new UserBean();
    try {

      userBean.setId( rs.getInt( "u.id" ) );
      userBean.setUserID( rs.getString( "u.user_id" ) );
      userBean.setPassword( rs.getString( "u.password" ) );
      userBean.setRoles( StringUtils.split( rs.getString( "u.roles" ) , "," ) );
      userBean.setName( rs.getString( "u.name" ) );
      userBean.setPhone( rs.getString( "u.phone" ) );
      userBean.setEmail( rs.getString( "u.email" ) );
      userBean.setClientID( (long) rs.getDouble( "u.client_id" ) );
      userBean.setProxyID( (long) rs.getDouble( "u.proxy_id" ) );

      userBean.setDisplay( rs.getBoolean( "u.display" ) );
      userBean.setActive( rs.getBoolean( "u.active" ) );
      userBean.setDateInserted( DateTimeFormat.convertToDate( rs
          .getString( "u.date_inserted" ) ) );
      userBean.setDateUpdated( DateTimeFormat.convertToDate( rs
          .getString( "u.date_updated" ) ) );

      userBean.setCompanyName( rs.getString( "c.company_name" ) );

    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to populate user bean , " + e );
    }

    return userBean;
  }

}
