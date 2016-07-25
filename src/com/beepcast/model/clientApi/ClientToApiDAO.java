package com.beepcast.model.clientApi;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToApiDAO {

  static final DLogContext lctx = new SimpleContext( "ClientToApiDAO" );

  private DatabaseLibrary dbLib;

  public ClientToApiDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insert( int clientId , String clientApi ) {
    boolean result = false;

    String sqlInsert = "INSERT INTO client_to_api ";
    sqlInsert += "( client_id , client_api , active , date_inserted , date_updated ) ";

    String sqlValues = "VALUES ( " + clientId + " , '"
        + StringEscapeUtils.escapeSql( clientApi ) + "' , 1 , NOW() , NOW() ) ";

    String sql = sqlInsert + sqlValues;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean update( int id , int clientId , String clientApi ) {
    boolean result = false;

    String sqlUpdate = "UPDATE client_to_api ";
    String sqlSet = "SET client_id = " + clientId + " ";
    sqlSet += ", client_api = '" + StringEscapeUtils.escapeSql( clientApi )
        + "' ";
    sqlSet += ", date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean delete( int id ) {
    boolean result = false;

    String sqlUpdate = "UPDATE client_to_api ";
    String sqlSet = "SET active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public ClientToApiBean query( int clientId , String clientApi ) {
    ClientToApiBean bean = null;

    String sqlSelect = "SELECT id , client_id , client_api , active ";
    String sqlFrom = "FROM client_to_api ";
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    sqlWhere += "AND ( client_api = '"
        + StringEscapeUtils.escapeSql( clientApi ) + "' ) ";
    sqlWhere += "AND ( active = 1 ) ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }

    QueryItem qi = null;
    Iterator it = qr.iterator();
    if ( it.hasNext() ) {
      qi = (QueryItem) it.next();
    }
    if ( qi == null ) {
      return bean;
    }

    bean = populateRecord( qi );

    return bean;
  }

  private ClientToApiBean populateRecord( QueryItem queryItem ) {
    ClientToApiBean bean = new ClientToApiBean();

    String stemp = null;
    int itemp = 0;

    stemp = (String) queryItem.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) queryItem.get( 1 ); // client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setClientId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) queryItem.get( 2 ); // client_api
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setClientApi( stemp );
    }

    stemp = (String) queryItem.get( 3 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setActive( itemp > 0 );
      } catch ( NumberFormatException e ) {
      }
    }

    return bean;
  }

}
