package com.beepcast.model.clientApi;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientApiDAO {

  static final DLogContext lctx = new SimpleContext( "ClientApiDAO" );

  private DatabaseLibrary dbLib;

  public ClientApiDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insert( ClientApiBean bean ) {
    boolean result = false;

    // read params

    int clientId = bean.getClientId();
    String apiVersion = bean.getApiVersion();
    String moDirection = bean.getMoDirection();
    String moUri = bean.getMoUri();
    String moFormat = bean.getMoFormat();
    String moApiVer = bean.getMoApiVer();
    String dnDirection = bean.getDnDirection();
    String dnUri = bean.getDnUri();
    String dnFormat = bean.getDnFormat();
    String dnApiVer = bean.getDnApiVer();
    String description = bean.getDescription();

    // clean params

    apiVersion = ( apiVersion == null ) ? "" : apiVersion.trim();
    moDirection = ( moDirection == null ) ? "" : moDirection.trim();
    moUri = ( moUri == null ) ? "" : moUri.trim();
    moFormat = ( moFormat == null ) ? "" : moFormat.trim();
    moApiVer = ( moApiVer == null ) ? "" : moApiVer.trim();
    dnDirection = ( dnDirection == null ) ? "" : dnDirection.trim();
    dnUri = ( dnUri == null ) ? "" : dnUri.trim();
    dnFormat = ( dnFormat == null ) ? "" : dnFormat.trim();
    dnApiVer = ( dnApiVer == null ) ? "" : dnApiVer.trim();
    description = ( description == null ) ? "" : description.trim();

    // compose sql

    String sqlInsert = "INSERT INTO client_api ";
    sqlInsert += "(client_id,api_version,mo_direction,mo_uri,mo_format";
    sqlInsert += ",mo_apiver,dn_direction,dn_uri,dn_format,dn_apiver,active";
    sqlInsert += ",description,date_inserted,date_updated) ";
    String sqlValues = "VALUES(" + clientId + ",'"
        + StringEscapeUtils.escapeSql( apiVersion ) + "','"
        + StringEscapeUtils.escapeSql( moDirection ) + "','"
        + StringEscapeUtils.escapeSql( moUri ) + "','"
        + StringEscapeUtils.escapeSql( moFormat ) + "','"
        + StringEscapeUtils.escapeSql( moApiVer ) + "','"
        + StringEscapeUtils.escapeSql( dnDirection ) + "','"
        + StringEscapeUtils.escapeSql( dnUri ) + "','"
        + StringEscapeUtils.escapeSql( dnFormat ) + "','"
        + StringEscapeUtils.escapeSql( dnApiVer ) + "',1,'"
        + StringEscapeUtils.escapeSql( description ) + "',NOW(),NOW()) ";
    String sql = sqlInsert + sqlValues;

    // execute sql

    DLog.debug( lctx , "Perform " + sql );
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public ClientApiBean queryWhereClientId( int clientId ) {
    ClientApiBean bean = null;

    if ( clientId < 1 ) {
      return bean;
    }

    String sqlSelect = "SELECT id , client_id , api_version , mo_direction ";
    sqlSelect += ", mo_uri , mo_format , mo_apiver , dn_direction ";
    sqlSelect += ", dn_uri , dn_format , dn_apiver , active , description ";
    String sqlFrom = "FROM client_api ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";
    String sqlOrder = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder + sqlLimit;

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }
    Iterator iter = qr.iterator();
    if ( iter.hasNext() ) {
      bean = populateRecord( (QueryItem) iter.next() );
    }

    return bean;
  }

  public boolean updateMoDnProfile( int id , String moDirection , String moUri ,
      String moFormat , String dnDirection , String dnUri , String dnFormat ) {
    boolean result = false;

    if ( id < 1 ) {
      return result;
    }

    String sqlUpdate = "UPDATE client_api ";
    String sqlSet = "SET mo_direction = '"
        + StringEscapeUtils.escapeSql( moDirection ) + "' ";
    sqlSet += ", mo_uri = '" + StringEscapeUtils.escapeSql( moUri ) + "' ";
    sqlSet += ", mo_format = '" + StringEscapeUtils.escapeSql( moFormat )
        + "' ";
    sqlSet += ", dn_direction = '" + StringEscapeUtils.escapeSql( dnDirection )
        + "' ";
    sqlSet += ", dn_uri = '" + StringEscapeUtils.escapeSql( dnUri ) + "' ";
    sqlSet += ", dn_format = '" + StringEscapeUtils.escapeSql( dnFormat )
        + "' ";
    sqlSet += ", date_updated = NOW() ";
    String sqlWhere = "WHERE id = " + id + " ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean updateActive( int id , boolean active ) {
    boolean result = false;

    if ( id < 1 ) {
      return result;
    }

    int iactive = active ? 1 : 0;

    String sqlUpdate = "UPDATE client_api ";
    String sqlSet = "SET active = " + iactive + " , date_updated = NOW() ";
    String sqlWhere = "WHERE id = " + id + " ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  private ClientApiBean populateRecord( QueryItem qi ) {
    ClientApiBean bean = new ClientApiBean();
    if ( qi == null ) {
      return bean;
    }

    String stemp;
    int itemp;

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setClientId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 2 ); // api_version
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setApiVersion( stemp );
    }

    stemp = (String) qi.get( 3 ); // mo_direction
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setMoDirection( stemp );
    }

    stemp = (String) qi.get( 4 ); // mo_uri
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setMoUri( stemp );
    }

    stemp = (String) qi.get( 5 ); // mo_format
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setMoFormat( stemp );
    }

    stemp = (String) qi.get( 6 ); // mo_apiver
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setMoApiVer( stemp );
    }

    stemp = (String) qi.get( 7 ); // dn_direction
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDnDirection( stemp );
    }

    stemp = (String) qi.get( 8 ); // dn_uri
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDnUri( stemp );
    }

    stemp = (String) qi.get( 9 ); // dn_format
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDnFormat( stemp );
    }

    stemp = (String) qi.get( 10 ); // dn_apiver
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDnApiVer( stemp );
    }

    stemp = (String) qi.get( 11 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setActive( itemp > 0 );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 12 ); // description
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDescription( stemp );
    }

    return bean;
  }

}
