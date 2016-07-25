package com.beepcast.model.client;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.model.util.DateTimeFormat;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientDAO {

  static final DLogContext lctx = new SimpleContext( "ClientDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public ClientDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public long insert( ClientBean clientBean ) {
    long clientId = 0;

    String companyName = clientBean.getCompanyName();

    ClientBean clientBeanTemp = selectBasedOnCompanyName( companyName );
    if ( clientBeanTemp != null ) {
      // to differentiate especially for verify company name existence
      clientId = -1;
      return clientId;
    }

    // read some parameters
    String notfEmAddrs = clientBean.getNotificationEmailAddresses();
    String notfMbNumbs = clientBean.getNotificationMobileNumbers();
    String fromEmAddrs = clientBean.getFromEmailAddresses();
    Date dateExpired = clientBean.getDateExpired();
    Date dateInserted = clientBean.getDateInserted();
    Date dateUpdated = clientBean.getDateUpdated();

    // clean some parameters
    notfEmAddrs = ( notfEmAddrs == null ) ? "" : notfEmAddrs.trim();
    notfMbNumbs = ( notfMbNumbs == null ) ? "" : notfMbNumbs.trim();
    fromEmAddrs = ( fromEmAddrs == null ) ? "" : fromEmAddrs.trim();
    dateInserted = ( dateInserted == null ) ? new Date() : dateInserted;
    dateUpdated = ( dateUpdated == null ) ? new Date() : dateUpdated;

    // build string date format parameters
    String strDateExpired = ( dateExpired == null ) ? "NULL" : "'"
        + DateTimeFormat.convertToString( dateExpired ) + "'";
    String strDateInserted = "'"
        + DateTimeFormat.convertToString( dateInserted ) + "'";
    String strDateUpdated = "'" + DateTimeFormat.convertToString( dateUpdated )
        + "'";

    // compose sql
    String sqlInsert = "INSERT INTO client "
        + "(company_name,company_size_id,address,company_city,company_state,"
        + "company_postcode,company_country_id,manager,phone,email,country,"
        + "discount,sms_charge,budget,payment_type,balance_threshold,"
        + "notification_email_addresses,notification_mobile_numbers,"
        + "from_email_addresses,charge_locked,beepme,bit_flags,office_phone,"
        + "web_address,url_link,state,master_client_id,group_connection_id,"
        + "band_id,client_level_id,enable_client_api,password_recycle_interval,"
        + "allow_ip_addresses,display,expired,active,date_expired,date_inserted,"
        + "date_updated) ";
    String sqlValues = "VALUES ('" + StringEscapeUtils.escapeSql( companyName )
        + "'," + clientBean.getCompanySizeId() + ",'"
        + StringEscapeUtils.escapeSql( clientBean.getAddress() ) + "','"
        + StringEscapeUtils.escapeSql( clientBean.getCompanyCity() ) + "','"
        + StringEscapeUtils.escapeSql( clientBean.getCompanyState() ) + "','"
        + StringEscapeUtils.escapeSql( clientBean.getCompanyPostcode() ) + "',"
        + clientBean.getCompanyCountryId() + ",'"
        + StringEscapeUtils.escapeSql( clientBean.getManager() ) + "','"
        + StringEscapeUtils.escapeSql( clientBean.getPhone() ) + "','"
        + StringEscapeUtils.escapeSql( clientBean.getEmail() ) + "','"
        + StringEscapeUtils.escapeSql( clientBean.getCountry() ) + "','"
        + StringEscapeUtils.escapeSql( clientBean.getDiscount() ) + "',"
        + clientBean.getSmsCharge() + "," + clientBean.getBudget() + ","
        + clientBean.getPaymentType() + "," + clientBean.getBalanceThreshold()
        + ",'" + StringEscapeUtils.escapeSql( notfEmAddrs ) + "','"
        + StringEscapeUtils.escapeSql( notfMbNumbs ) + "','"
        + StringEscapeUtils.escapeSql( fromEmAddrs ) + "',"
        + ( clientBean.isChargeLocked() ? 1 : 0 ) + ","
        + ( clientBean.isBeepme() ? 1 : 0 ) + "," + clientBean.getBitFlags()
        + ",'" + StringEscapeUtils.escapeSql( clientBean.getOfficePhone() )
        + "','" + StringEscapeUtils.escapeSql( clientBean.getWebAddress() )
        + "','" + StringEscapeUtils.escapeSql( clientBean.getUrlLink() )
        + "','" + StringEscapeUtils.escapeSql( clientBean.getState() ) + "',"
        + clientBean.getMasterClientID() + ","
        + clientBean.getGroupConnectionId() + "," + clientBean.getBandId()
        + "," + clientBean.getClientLevelId() + ","
        + ( clientBean.isEnableClientApi() ? 1 : 0 ) + ","
        + clientBean.getPasswordRecycleInterval() + ",'"
        + StringEscapeUtils.escapeSql( clientBean.getAllowIpAddresses() )
        + "'," + ( clientBean.isDisplay() ? 1 : 0 ) + ","
        + ( clientBean.isExpired() ? 1 : 0 ) + ","
        + ( clientBean.isActive() ? 1 : 0 ) + "," + strDateExpired + ","
        + strDateInserted + "," + strDateUpdated + ") ";
    String sql = sqlInsert + sqlValues;

    // log it
    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt == null ) || ( irslt.intValue() < 1 ) ) {
      return clientId;
    }

    // re-select bean
    clientBeanTemp = selectBasedOnCompanyName( companyName );
    if ( clientBeanTemp == null ) {
      return clientId;
    }

    // re-select new id
    clientId = clientBeanTemp.getClientID();

    return clientId;
  }

  public boolean update( ClientBean clientBean ) {
    boolean result = false;

    // validate must be params
    if ( clientBean == null ) {
      return result;
    }
    if ( clientBean.getClientID() < 1 ) {
      return result;
    }

    // read some parameters
    String notfEmAddrs = clientBean.getNotificationEmailAddresses();
    String notfMbNumbs = clientBean.getNotificationMobileNumbers();
    String fromEmAddrs = clientBean.getFromEmailAddresses();
    Date dateExpired = clientBean.getDateExpired();

    // clean some parameters
    notfEmAddrs = ( notfEmAddrs == null ) ? "" : notfEmAddrs.trim();
    notfMbNumbs = ( notfMbNumbs == null ) ? "" : notfMbNumbs.trim();
    fromEmAddrs = ( fromEmAddrs == null ) ? "" : fromEmAddrs.trim();

    // build string date format parameters
    String strDateExpired = ( dateExpired == null ) ? "NULL" : "'"
        + DateTimeFormat.convertToString( dateExpired ) + "'";
    String strDateUpdated = "'" + DateTimeFormat.convertToString( new Date() )
        + "'";

    // compose sql
    StringBuffer sql = new StringBuffer();
    sql.append( "UPDATE client SET " );
    sql.append( "company_name='"
        + StringEscapeUtils.escapeSql( clientBean.getCompanyName() ) + "' " );
    sql.append( ", address='"
        + StringEscapeUtils.escapeSql( clientBean.getAddress() ) + "' " );
    sql.append( ", company_country_id=" + clientBean.getCompanyCountryId()
        + " " );
    sql.append( ", manager='"
        + StringEscapeUtils.escapeSql( clientBean.getManager() ) + "' " );
    sql.append( ", phone='" + clientBean.getPhone() + "' " );
    sql.append( ", email='"
        + StringEscapeUtils.escapeSql( clientBean.getEmail() ) + "' " );
    sql.append( ", country='" + clientBean.getCountry() + "' " );
    sql.append( ", discount='" + clientBean.getDiscount() + "' " );
    sql.append( ", sms_charge='" + clientBean.getSmsCharge() + "' " );
    sql.append( ", budget='" + clientBean.getBudget() + "' " );
    sql.append( ", payment_type=" + clientBean.getPaymentType() + " " );
    sql.append( ", balance_threshold=" + clientBean.getBalanceThreshold() + " " );
    sql.append( ", notification_email_addresses = '"
        + StringEscapeUtils.escapeSql( notfEmAddrs ) + "' " );
    sql.append( ", notification_mobile_numbers = '"
        + StringEscapeUtils.escapeSql( notfMbNumbs ) + "' " );
    sql.append( ", from_email_addresses = '"
        + StringEscapeUtils.escapeSql( fromEmAddrs ) + "' " );
    sql.append( ", charge_locked=" + ( clientBean.isChargeLocked() ? 1 : 0 )
        + " " );
    sql.append( ", beepme=" + ( clientBean.isBeepme() ? 1 : 0 ) + " " );
    sql.append( ", bit_flags=" + clientBean.getBitFlags() + " " );
    sql.append( ", office_phone='"
        + StringEscapeUtils.escapeSql( clientBean.getOfficePhone() ) + "' " );
    sql.append( ", web_address='"
        + StringEscapeUtils.escapeSql( clientBean.getWebAddress() ) + "' " );
    sql.append( ", url_link='"
        + StringEscapeUtils.escapeSql( clientBean.getUrlLink() ) + "' " );
    sql.append( ", state='"
        + StringEscapeUtils.escapeSql( clientBean.getState() ) + "' " );
    sql.append( ", master_client_id=" + clientBean.getMasterClientID() + " " );
    sql.append( ", group_connection_id=" + clientBean.getGroupConnectionId()
        + " " );
    sql.append( ", band_id=" + clientBean.getBandId() + " " );
    sql.append( ", client_level_id=" + clientBean.getClientLevelId() + " " );
    sql.append( ", enable_client_api="
        + ( clientBean.isEnableClientApi() ? 1 : 0 ) + " " );
    sql.append( ", password_recycle_interval="
        + clientBean.getPasswordRecycleInterval() + " " );
    sql.append( ", allow_ip_addresses='"
        + StringEscapeUtils.escapeSql( clientBean.getAllowIpAddresses() )
        + "' " );
    sql.append( ", display=" + ( clientBean.isDisplay() ? 1 : 0 ) + " " );
    sql.append( ", expired=" + ( clientBean.isExpired() ? 1 : 0 ) + " " );
    sql.append( ", active=" + ( clientBean.isActive() ? 1 : 0 ) + " " );
    sql.append( ", date_expired=" + strDateExpired + " " );
    sql.append( ", date_updated=" + strDateUpdated + " " );
    sql.append( "WHERE ( client_id = " + clientBean.getClientID() + " ) " );

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "profiledb" , sql.toString() );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean updateState( long clientID , String state ) {
    boolean result = false;

    if ( clientID < 1 ) {
      return result;
    }

    if ( StringUtils.isBlank( state ) ) {
      return result;
    }

    Date dateUpdated = new Date();
    String strDateUpdated = "'" + DateTimeFormat.convertToString( dateUpdated )
        + "'";

    StringBuffer sql = new StringBuffer();
    sql.append( "UPDATE client " );
    sql.append( "SET state = '" + StringEscapeUtils.escapeSql( state ) + "' " );
    sql.append( ", date_updated = " + strDateUpdated + " " );
    sql.append( "WHERE ( client_id = " + clientID + " ) " );

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "profiledb" , sql.toString() );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean updateExpired( long clientID , boolean expired ,
      Date dateExpired ) {
    boolean result = false;

    if ( clientID < 1 ) {
      return result;
    }

    int expiredStatus = expired ? 1 : 0;

    String strDateExpired = ( dateExpired == null ) ? "NULL" : "'"
        + DateTimeFormat.convertToString( dateExpired ) + "'";

    Date dateUpdated = new Date();
    String strDateUpdated = "'" + DateTimeFormat.convertToString( dateUpdated )
        + "'";

    StringBuffer sql = new StringBuffer();
    sql.append( "UPDATE client " );
    sql.append( "SET expired = " + expiredStatus + " " );
    sql.append( ", date_expired = " + strDateExpired + " " );
    sql.append( ", date_updated = " + strDateUpdated + " " );
    sql.append( "WHERE ( client_id = " + clientID + " ) " );

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "profiledb" , sql.toString() );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean updateDisplay( long clientID , boolean display ) {
    boolean result = false;

    String sqlUpdate = "UPDATE client ";
    String sqlSet = "SET display = " + ( display ? 1 : 0 )
        + " , date_updated = NOW() ";
    String sqlWhere = "WHERE ( client_id = " + clientID + " ) ";

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

  public boolean updateActive( long clientID , boolean active ) {
    boolean result = false;

    String sqlUpdate = "UPDATE client ";
    String sqlSet = "SET active = " + ( active ? 1 : 0 )
        + " , date_updated = NOW() ";
    String sqlWhere = "WHERE ( client_id = " + clientID + " ) ";

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

  public ClientBean select( long clientID ) {
    ClientBean clientBean = null;
    String sqlSelectFrom = ClientQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientID + " ) ";
    String sqlOrder = "ORDER BY client_id DESC ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;
    clientBean = populateBean( sql );
    return clientBean;
  }

  public ClientBean selectBasedOnCompanyName( String companyName ) {
    ClientBean clientBean = null;
    String sqlSelectFrom = ClientQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( company_name = '"
        + StringEscapeUtils.escapeSql( companyName ) + "' ) ";
    String sqlOrder = "ORDER BY client_id DESC ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;
    clientBean = populateBean( sql );
    return clientBean;
  }

  private ClientBean populateBean( String sql ) {
    ClientBean clientBean = null;
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      // DLog.debug( lctx , "Perform " + sql );
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery( sql );
      if ( rs.next() ) {
        clientBean = ClientQuery.populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to load client bean , " + e );
    } finally {
      conn.disconnect( true );
    }
    return clientBean;
  }

}
