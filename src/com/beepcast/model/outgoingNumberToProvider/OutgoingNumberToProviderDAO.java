package com.beepcast.model.outgoingNumberToProvider;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class OutgoingNumberToProviderDAO {

  static final DLogContext lctx = new SimpleContext(
      "OutgoingNumberToProviderDAO" );

  private OnlinePropertiesApp opropsApp;
  private DatabaseLibrary dbLib;

  public OutgoingNumberToProviderDAO() {
    opropsApp = OnlinePropertiesApp.getInstance();
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insert( OutgoingNumberToProviderBean bean ) {
    boolean result = false;

    // read params
    String outgoingNumber = bean.getOutgoingNumber();
    String countryCode = bean.getCountryCode();
    String prefixNumber = bean.getPrefixNumber();
    String telcoCode = bean.getTelcoCode();
    String providerId = bean.getProviderId();
    String masked = bean.getMasked();
    String description = bean.getDescription();
    boolean suspend = bean.isSuspend();

    // clean params
    outgoingNumber = ( outgoingNumber == null ) ? "" : outgoingNumber.trim();
    countryCode = ( countryCode == null ) ? "*" : countryCode.trim();
    prefixNumber = ( prefixNumber == null ) ? "*" : prefixNumber.trim();
    telcoCode = ( telcoCode == null ) ? "*" : telcoCode.trim();
    providerId = ( providerId == null ) ? "" : providerId.trim();
    masked = ( masked == null ) ? "" : masked.trim();
    description = ( description == null ) ? "" : description.trim();

    // compose sql
    String sqlInsert = "INSERT INTO outgoing_number_to_provider ";
    sqlInsert += "(outgoing_number,level,country_code,prefix_number";
    sqlInsert += ",telco_code,provider_id,group_connection_id,masked";
    sqlInsert += ",description,priority,active,suspend,date_inserted";
    sqlInsert += ",date_updated) ";
    String sqlValues = "VALUES('"
        + StringEscapeUtils.escapeSql( outgoingNumber ) + "',"
        + bean.getLevel() + ",'" + StringEscapeUtils.escapeSql( countryCode )
        + "','" + StringEscapeUtils.escapeSql( prefixNumber ) + "','"
        + StringEscapeUtils.escapeSql( telcoCode ) + "','"
        + StringEscapeUtils.escapeSql( providerId ) + "',"
        + bean.getGroupConnectionId() + ",'"
        + StringEscapeUtils.escapeSql( masked ) + "','"
        + StringEscapeUtils.escapeSql( description ) + "',"
        + bean.getPriority() + ",1," + ( suspend ? 1 : 0 ) + ",NOW(),NOW()) ";
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

  public OutgoingNumberToProviderBean selectById( int id ) {
    OutgoingNumberToProviderBean bean = null;

    // compose sql
    String sqlSelectFrom = OutgoingNumberToProviderQuery.sqlSelectFrom();
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
        bean = OutgoingNumberToProviderQuery.populateRecord( qi );
      }
    }

    return bean;
  }

  public boolean update( OutgoingNumberToProviderBean bean ) {
    boolean result = false;

    // read params
    String outgoingNumber = bean.getOutgoingNumber();
    String countryCode = bean.getCountryCode();
    String prefixNumber = bean.getPrefixNumber();
    String telcoCode = bean.getTelcoCode();
    String providerId = bean.getProviderId();
    String masked = bean.getMasked();
    String description = bean.getDescription();
    boolean suspend = bean.isSuspend();

    // clean params
    outgoingNumber = ( outgoingNumber == null ) ? "" : outgoingNumber.trim();
    countryCode = ( countryCode == null ) ? "*" : countryCode.trim();
    prefixNumber = ( prefixNumber == null ) ? "*" : prefixNumber.trim();
    telcoCode = ( telcoCode == null ) ? "*" : telcoCode.trim();
    providerId = ( providerId == null ) ? "" : providerId.trim();
    masked = ( masked == null ) ? "" : masked.trim();
    description = ( description == null ) ? "" : description.trim();

    // compose sql
    String sqlUpdate = "UPDATE outgoing_number_to_provider ";
    String sqlSet = "SET outgoing_number = '"
        + StringEscapeUtils.escapeSql( outgoingNumber ) + "' ";
    sqlSet += ", level = " + bean.getLevel() + " ";
    sqlSet += ", country_code = '" + StringEscapeUtils.escapeSql( countryCode )
        + "' ";
    sqlSet += ", prefix_number = '"
        + StringEscapeUtils.escapeSql( prefixNumber ) + "' ";
    sqlSet += ", telco_code = '" + StringEscapeUtils.escapeSql( telcoCode )
        + "' ";
    sqlSet += ", provider_id = '" + StringEscapeUtils.escapeSql( providerId )
        + "' ";
    sqlSet += ", group_connection_id = " + bean.getGroupConnectionId() + " ";
    sqlSet += ", masked = '" + StringEscapeUtils.escapeSql( masked ) + "' ";
    sqlSet += ", description = '" + StringEscapeUtils.escapeSql( description )
        + "' ";
    sqlSet += ", priority = " + bean.getPriority() + " ";
    sqlSet += ", suspend = " + ( suspend ? 1 : 0 ) + " ";
    sqlSet += ", date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + bean.getId() + " ) ";
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

  public boolean updateSuspendById( int id , boolean suspend ) {
    boolean result = false;

    // compose sql
    String sqlUpdate = "UPDATE outgoing_number_to_provider ";
    String sqlSet = "SET suspend = " + ( suspend ? 1 : 0 ) + " ";
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
    String sqlUpdate = "UPDATE outgoing_number_to_provider ";
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
