package com.beepcast.model.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ProviderDAO {

  static final DLogContext lctx = new SimpleContext( "ProviderDAO" );

  private OnlinePropertiesApp opropsApp;
  private DatabaseLibrary dbLib;

  public ProviderDAO() {
    opropsApp = OnlinePropertiesApp.getInstance();
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insert( ProviderBean bean ) {
    boolean result = false;

    // read params
    String providerId = bean.getProviderId();
    String providerName = bean.getProviderName();
    String direction = bean.getDirection();
    String type = bean.getType();
    String shortCode = bean.getShortCode();
    String countryCode = bean.getCountryCode();
    String accessUrl = bean.getAccessUrl();
    String accessUsername = bean.getAccessUsername();
    String accessPassword = bean.getAccessPassword();
    String listenerUrl = bean.getListenerUrl();
    String description = bean.getDescription();

    // clean params
    providerId = ( providerId == null ) ? "" : providerId.trim();
    providerName = ( providerName == null ) ? "" : providerName.trim();
    direction = ( direction == null ) ? "" : direction.trim();
    type = ( type == null ) ? "" : type.trim();
    shortCode = ( shortCode == null ) ? "" : shortCode.trim();
    countryCode = ( countryCode == null ) ? "" : countryCode.trim();
    accessUrl = ( accessUrl == null ) ? "" : accessUrl.trim();
    accessUsername = ( accessUsername == null ) ? "" : accessUsername.trim();
    accessPassword = ( accessPassword == null ) ? "" : accessPassword.trim();
    listenerUrl = ( listenerUrl == null ) ? "" : listenerUrl.trim();
    description = ( description == null ) ? "" : description.trim();

    // compose sql
    String sqlInsert = "INSERT INTO provider (master_id,provider_id";
    sqlInsert += ",provider_name,direction,`type`,short_code";
    sqlInsert += ",country_code,access_url,access_username";
    sqlInsert += ",access_password,listener_url,in_credit_cost";
    sqlInsert += ",ou_credit_cost,description,active,date_inserted";
    sqlInsert += ",date_updated) ";
    String sqlValues = "VALUES(" + bean.getMasterId() + ",'"
        + StringEscapeUtils.escapeSql( providerId ) + "','"
        + StringEscapeUtils.escapeSql( providerName ) + "','"
        + StringEscapeUtils.escapeSql( direction ) + "','"
        + StringEscapeUtils.escapeSql( type ) + "','"
        + StringEscapeUtils.escapeSql( shortCode ) + "','"
        + StringEscapeUtils.escapeSql( countryCode ) + "','"
        + StringEscapeUtils.escapeSql( accessUrl ) + "','"
        + StringEscapeUtils.escapeSql( accessUsername ) + "','"
        + StringEscapeUtils.escapeSql( accessPassword ) + "','"
        + StringEscapeUtils.escapeSql( listenerUrl ) + "',"
        + bean.getInCreditCost() + "," + bean.getOuCreditCost() + ",'"
        + StringEscapeUtils.escapeSql( description ) + "',1,NOW(),NOW()) ";
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

  public ProviderBean selectById( int id ) {
    ProviderBean providerBean = null;

    if ( id < 1 ) {
      return providerBean;
    }

    // compose sql
    String sqlSelectFrom = ProviderQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( id = " + id + " ) ";
    String sql = sqlSelectFrom + sqlWhere;

    // execute sql
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return providerBean;
    }

    // populate record
    Iterator it = qr.iterator();
    if ( it.hasNext() ) {
      providerBean = ProviderQuery.populateRecord( (QueryItem) it.next() );
    }

    return providerBean;
  }

  public ProviderBean selectActiveBeanFromProviderId( String providerId ) {
    ProviderBean providerBean = null;

    if ( providerId == null ) {
      return providerBean;
    }

    // compose sql
    String sqlSelectFrom = ProviderQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( provider_id = '"
        + StringEscapeUtils.escapeSql( providerId ) + "' ) ";
    String sqlOrder = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    // execute sql
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return providerBean;
    }

    // populate record
    Iterator it = qr.iterator();
    if ( it.hasNext() ) {
      providerBean = ProviderQuery.populateRecord( (QueryItem) it.next() );
    }

    return providerBean;
  }

  public boolean update( ProviderBean bean ) {
    boolean result = false;

    if ( bean == null ) {
      return result;
    }

    // read params
    String providerId = bean.getProviderId();
    String providerName = bean.getProviderName();
    String direction = bean.getDirection();
    String type = bean.getType();
    String shortCode = bean.getShortCode();
    String countryCode = bean.getCountryCode();
    String accessUrl = bean.getAccessUrl();
    String accessUsername = bean.getAccessUsername();
    String accessPassword = bean.getAccessPassword();
    String listenerUrl = bean.getListenerUrl();
    String description = bean.getDescription();

    // clean params
    providerId = ( providerId == null ) ? "" : providerId.trim();
    providerName = ( providerName == null ) ? "" : providerName.trim();
    direction = ( direction == null ) ? "" : direction.trim();
    type = ( type == null ) ? "" : type.trim();
    shortCode = ( shortCode == null ) ? "" : shortCode.trim();
    countryCode = ( countryCode == null ) ? "" : countryCode.trim();
    accessUrl = ( accessUrl == null ) ? "" : accessUrl.trim();
    accessUsername = ( accessUsername == null ) ? "" : accessUsername.trim();
    accessPassword = ( accessPassword == null ) ? "" : accessPassword.trim();
    listenerUrl = ( listenerUrl == null ) ? "" : listenerUrl.trim();
    description = ( description == null ) ? "" : description.trim();

    // compose sql
    String sqlUpdate = "UPDATE provider ";
    String sqlSet = "SET master_id = " + bean.getMasterId() + " ";
    sqlSet += ", provider_id = '" + StringEscapeUtils.escapeSql( providerId )
        + "' ";
    sqlSet += ", provider_name = '"
        + StringEscapeUtils.escapeSql( providerName ) + "' ";
    sqlSet += ", direction = '" + StringEscapeUtils.escapeSql( direction )
        + "' ";
    sqlSet += ", `type` = '" + StringEscapeUtils.escapeSql( type ) + "' ";
    sqlSet += ", short_code = '" + StringEscapeUtils.escapeSql( shortCode )
        + "' ";
    sqlSet += ", country_code = '" + StringEscapeUtils.escapeSql( countryCode )
        + "' ";
    sqlSet += ", access_url = '" + StringEscapeUtils.escapeSql( accessUrl )
        + "' ";
    sqlSet += ", access_username = '"
        + StringEscapeUtils.escapeSql( accessUsername ) + "' ";
    sqlSet += ", access_password = '"
        + StringEscapeUtils.escapeSql( accessPassword ) + "' ";
    sqlSet += ", listener_url = '" + StringEscapeUtils.escapeSql( listenerUrl )
        + "' ";
    sqlSet += ", in_credit_cost = " + bean.getInCreditCost() + " ";
    sqlSet += ", ou_credit_cost = " + bean.getOuCreditCost() + " ";
    sqlSet += ", description = '" + StringEscapeUtils.escapeSql( description )
        + "' ";
    sqlSet += ", date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + bean.getId() + " ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    // log it
    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql
    Integer intr = dbLib.executeQuery( "profiledb" , sql );
    if ( ( intr != null ) && ( intr.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean updateActiveFromId( int id , boolean active ) {
    boolean result = false;

    if ( id < 1 ) {
      return result;
    }

    // compose sql
    String sqlUpdate = "UPDATE provider ";
    String sqlSet = "SET active = " + ( active ? 1 : 0 ) + " ";
    sqlSet += ", date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    // log it
    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql
    Integer intr = dbLib.executeQuery( "profiledb" , sql );
    if ( ( intr != null ) && ( intr.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public List selectActiveBeans( String direction , String type ) {
    List list = new ArrayList();

    // compose sql
    String sqlSelectFrom = ProviderQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    if ( !StringUtils.isBlank( direction ) ) {
      sqlWhere += "AND ( ( direction = '"
          + StringEscapeUtils.escapeSql( direction ) + "' ) OR ( direction = '"
          + ProviderDirection.DIRECTION_IO + "' ) ) ";
    }
    if ( !StringUtils.isBlank( type ) ) {
      sqlWhere += "AND ( type = '" + StringEscapeUtils.escapeSql( type )
          + "' ) ";
    }
    String sqlOrder = "ORDER BY provider_id ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder;

    // populate records
    list = populateRecords( sql );

    return list;
  }

  public List selectActiveBeansFromShortCode() {
    List list = new ArrayList();

    // compose sql
    String sqlSelectFrom = ProviderQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( short_code IS NOT NULL ) ";
    sqlWhere += "AND ( short_code != '' ) ";
    String sqlOrder = "ORDER BY id ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder;

    // populate records
    list = populateRecords( sql );

    return list;
  }

  public List selectActiveMasterBeans() {
    List list = new ArrayList();

    // compose sql
    String sqlSelectFrom = ProviderQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( master_id = 0 ) ";
    String sqlOrder = "ORDER BY id ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder;

    // populate records
    list = populateRecords( sql );

    return list;
  }

  private List populateRecords( String sql ) {
    List list = new ArrayList();

    // execute sql
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return list;
    }

    // iterate and fetch record
    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi == null ) {
        continue;
      }
      ProviderBean providerBean = ProviderQuery.populateRecord( qi );
      if ( providerBean == null ) {
        continue;
      }
      list.add( providerBean );
    }

    return list;
  }

}
