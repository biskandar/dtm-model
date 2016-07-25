package com.beepcast.model.clientApi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToApisDAO {

  static final DLogContext lctx = new SimpleContext( "ClientToApisDAO" );

  private DatabaseLibrary dbLib;

  public ClientToApisDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public List listClientApis( int clientId ) {
    List listClientApis = new ArrayList();

    String sqlSelect = "SELECT id , client_id , client_api , active ";
    String sqlFrom = "FROM client_to_api ";
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    sqlWhere += "AND ( active = 1 ) ";
    String sqlOrder = "ORDER BY id ASC ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return listClientApis;
    }

    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi == null ) {
        continue;
      }
      ClientToApiBean bean = populateRecord( qi );
      if ( bean == null ) {
        continue;
      }
      listClientApis.add( bean.getClientApi() );
    }

    return listClientApis;
  }

  public int deleteClientApis( int clientId ) {
    int totalRecords = 0;

    String sqlDelete = "DELETE FROM client_to_api ";
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    String sql = sqlDelete + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );

    if ( irslt != null ) {
      totalRecords = irslt.intValue();
    }

    return totalRecords;
  }

  public int insertClientApis( int clientId , List listClientApis ) {
    int totalRecords = 0;

    if ( ( listClientApis == null ) || ( listClientApis.size() < 1 ) ) {
      return totalRecords;
    }

    String sqlInsert = "INSERT INTO client_to_api ";
    sqlInsert += "( client_id , client_api , active , date_inserted , date_updated ) ";

    StringBuffer sbValues = new StringBuffer();
    for ( int idx = 0 ; idx < listClientApis.size() ; idx++ ) {
      if ( idx > 0 ) {
        sbValues.append( ", " );
      }
      sbValues.append( "( " + clientId + " , '"
          + StringEscapeUtils.escapeSql( (String) listClientApis.get( idx ) )
          + "' , 1 , NOW() , NOW() ) " );
    }
    String sqlValues = "VALUES " + sbValues.toString();

    String sql = sqlInsert + sqlValues;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );

    if ( irslt != null ) {
      totalRecords = irslt.intValue();
    }

    return totalRecords;
  }

  public int updateClientApis( int clientId , List listClientApis ) {
    int totalRecords = 0;

    totalRecords = deleteClientApis( clientId );
    DLog.debug( lctx , "Deleted total " + totalRecords + " record(s)" );

    totalRecords = insertClientApis( clientId , listClientApis );
    DLog.debug( lctx , "Inserted total " + totalRecords + " record(s)" );

    return totalRecords;
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
