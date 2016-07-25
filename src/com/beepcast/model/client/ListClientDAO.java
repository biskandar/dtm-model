package com.beepcast.model.client;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.beepcast.model.event.ListEventService;
import com.beepcast.model.util.DateTimeFormat;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientDAO {

  static final DLogContext lctx = new SimpleContext( "ListClientDAO" );

  private DatabaseLibrary dbLib;

  public ListClientDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalActiveClients( int masterClientId ,
      String keywordCompanyName , int display ) {
    long totalRecords = 0;

    // compose sql
    String sqlSelect = "SELECT COUNT(`client`.client_id) AS total ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( masterClientId , keywordCompanyName , display );
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

  public List selectActiveClients( int masterClientId ,
      String keywordCompanyName , int display , int top , int limit ,
      int orderby ) {
    List clientBeans = new ArrayList();

    // compose sql
    String sqlSelect = "SELECT `client`.* ";
    sqlSelect += ", country.name AS 'company_country_name' ";
    sqlSelect += ", group_client_connection.name AS 'group_connection_name' ";
    sqlSelect += ", band.name AS 'band_name' ";
    sqlSelect += ", client_level.name AS 'client_level_name' ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( masterClientId , keywordCompanyName , display );

    String sqlOrderBy = "";
    switch ( orderby ) {
    case ListClientService.ORDERBY_CLIENTID_ASC :
      sqlOrderBy = "ORDER BY `client`.client_id ASC ";
      break;
    case ListClientService.ORDERBY_CLIENTID_DESC :
      sqlOrderBy = "ORDER BY `client`.client_id DESC ";
      break;
    case ListClientService.ORDERBY_COMPANYNAME_ASC :
      sqlOrderBy = "ORDER BY `client`.company_name ASC ";
      break;
    case ListClientService.ORDERBY_COMPANYNAME_DESC :
      sqlOrderBy = "ORDER BY `client`.company_name DESC ";
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
        ClientBean clientBean = populateBean( rs );
        clientBeans.add( clientBean );
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to select active clients , " + e );
    } finally {
      conn.disconnect( true );
    }

    return clientBeans;
  }

  private String sqlFrom() {
    String sqlFrom = "FROM `client` ";
    sqlFrom += "LEFT OUTER JOIN country ON country.id = `client`.company_country_id ";
    sqlFrom += "LEFT OUTER JOIN group_client_connection ON group_client_connection.id = `client`.group_connection_id ";
    sqlFrom += "LEFT OUTER JOIN band ON band.id = `client`.band_id ";
    sqlFrom += "LEFT OUTER JOIN client_level ON client_level.id = `client`.client_level_id ";
    return sqlFrom;
  }

  private String sqlWhere( int masterClientId , String keywordCompanyName ,
      int display ) {
    String sqlWhere = "WHERE ( `client`.active = 1 ) ";
    if ( masterClientId > 0 ) {
      sqlWhere += "AND ( `client`.master_client_id = " + masterClientId + " ) ";
    }
    if ( !StringUtils.isBlank( keywordCompanyName ) ) {
      sqlWhere += "AND ( `client`.company_name LIKE '%"
          + StringEscapeUtils.escapeSql( keywordCompanyName ) + "%' ) ";
    }
    switch ( display ) {
    case ListEventService.DISPLAY_ON :
      sqlWhere += "AND ( `client`.display = 1 ) ";
      break;
    case ListEventService.DISPLAY_OFF :
      sqlWhere += "AND ( `client`.display = 0 ) ";
      break;
    }
    return sqlWhere;
  }

  private ClientBean populateBean( ResultSet rs ) throws SQLException {
    ClientBean clientBean = new ClientBean();

    clientBean.setClientID( rs.getLong( "client_id" ) );

    clientBean.setCompanyName( rs.getString( "company_name" ) );

    clientBean.setCompanySizeId( rs.getInt( "company_size_id" ) );
    clientBean.setAddress( rs.getString( "address" ) );
    clientBean.setCompanyCity( rs.getString( "company_city" ) );
    clientBean.setCompanyState( rs.getString( "company_state" ) );
    clientBean.setCompanyPostcode( rs.getString( "company_postcode" ) );
    clientBean.setCompanyCountryId( rs.getInt( "company_country_id" ) );
    clientBean.setCompanyCountryName( rs.getString( "company_country_name" ) );
    clientBean.setOfficePhone( rs.getString( "office_phone" ) );
    clientBean.setWebAddress( rs.getString( "web_address" ) );

    clientBean.setManager( rs.getString( "manager" ) );
    clientBean.setPhone( rs.getString( "phone" ) );
    clientBean.setEmail( rs.getString( "email" ) );

    clientBean.setCountry( rs.getString( "country" ) );
    clientBean.setDiscount( rs.getString( "discount" ) );
    clientBean.setSmsCharge( new Double( rs.getString( "sms_charge" ) )
        .doubleValue() );
    clientBean.setBudget( new Double( rs.getString( "budget" ) ).doubleValue() );
    clientBean.setPaymentType( rs.getInt( "payment_type" ) );
    clientBean.setBalanceThreshold( rs.getInt( "balance_threshold" ) );
    clientBean.setChargeLocked( rs.getDouble( "charge_locked" ) == 1 );

    clientBean.setBeepme( rs.getDouble( "beepme" ) == 1 );
    clientBean.setBitFlags( rs.getLong( "bit_flags" ) );
    clientBean.setMasterClientID( rs.getLong( "master_client_id" ) );
    clientBean.setGroupConnectionId( rs.getInt( "group_connection_id" ) );
    clientBean.setGroupConnectionName( rs.getString( "group_connection_name" ) );
    clientBean.setState( rs.getString( "state" ) );
    clientBean.setBandId( rs.getInt( "band_id" ) );
    clientBean.setBandName( rs.getString( "band_name" ) );
    clientBean.setClientLevelId( rs.getInt( "client_level_id" ) );
    clientBean.setClientLevelName( rs.getString( "client_level_name" ) );
    clientBean.setEnableClientApi( rs.getBoolean( "enable_client_api" ) );
    clientBean.setDisplay( rs.getBoolean( "display" ) );
    clientBean.setActive( rs.getBoolean( "active" ) );

    clientBean.setDateInserted( DateTimeFormat.convertToDate( rs
        .getString( "date_inserted" ) ) );
    clientBean.setDateUpdated( DateTimeFormat.convertToDate( rs
        .getString( "date_updated" ) ) );
    return clientBean;
  }
}
