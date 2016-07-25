package com.beepcast.model.signup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.model.util.DateTimeFormat;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListNewClientUserDAO {

  static final DLogContext lctx = new SimpleContext( "ListNewClientUserDAO" );

  private DatabaseLibrary dbLib;

  public ListNewClientUserDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalRecords() {
    long totalRecords = 0;

    // compose sql
    String sqlSelect = "SELECT COUNT(*) AS total ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere();
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

  public List queryRecords( int top , int limit ) {
    List listBeans = new ArrayList();

    // compose sql
    String sqlSelect = "SELECT * ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere();
    String sqlOrderBy = "ORDER BY date_updated DESC ";

    String sqlLimit = "LIMIT " + top + " , " + limit + " ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrderBy + sqlLimit;

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        listBeans.add( populateBean( rs ) );
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to select active events , " + e );
    } finally {
      conn.disconnect( true );
    }

    return listBeans;
  }

  private String sqlFrom() {
    return NewClientUserDAO.FROM_BEAN;
  }

  private String sqlWhere() {
    String sqlWhere = "WHERE ( active = 1 ) ";
    return sqlWhere;
  }

  private NewClientUserBean populateBean( ResultSet rs ) throws SQLException {
    NewClientUserBean bean = new NewClientUserBean();
    bean.setId( rs.getInt( "id" ) );
    bean.setUserFirstName( rs.getString( "user_first_name" ) );
    bean.setUserLastName( rs.getString( "user_last_name" ) );
    bean.setUserPhone( rs.getString( "user_phone" ) );
    bean.setUserEmail( rs.getString( "user_email" ) );
    bean.setCompanyName( rs.getString( "company_name" ) );
    bean.setCompanySizeId( rs.getInt( "company_size_id" ) );
    bean.setCompanyAddr( rs.getString( "company_addr" ) );
    bean.setCompanyCity( rs.getString( "company_city" ) );
    bean.setCompanyState( rs.getString( "company_state" ) );
    bean.setCompanyPostcode( rs.getString( "company_postcode" ) );
    bean.setCompanyCountryId( rs.getInt( "company_country_id" ) );
    bean.setCompanyWww( rs.getString( "company_www" ) );
    bean.setFeatureBand( rs.getString( "feature_band" ) );
    bean.setFeatureApi( rs.getInt( "feature_api" ) > 0 );
    bean.setLoginUsr( rs.getString( "login_usr" ) );
    bean.setLoginPwd( rs.getString( "login_pwd" ) );
    bean.setActivationKey( rs.getString( "activation_key" ) );
    bean.setSentEmail( rs.getBoolean( "sent_email" ) );
    bean.setActive( rs.getBoolean( "active" ) );
    bean.setDateInserted( DateTimeFormat.convertToDate( rs
        .getString( "date_inserted" ) ) );
    bean.setDateUpdated( DateTimeFormat.convertToDate( rs
        .getString( "date_updated" ) ) );
    return bean;
  }

}
