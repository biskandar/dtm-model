package com.beepcast.model.client;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientsDAO {

  static final DLogContext lctx = new SimpleContext( "ClientsDAO" );

  private DatabaseLibrary dbLib;

  public ClientsDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public ClientsBean generateClientsBean( int masterClientId , int clientId ) {
    String sqlSelectFrom = ClientQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( display = 1 ) ";
    if ( masterClientId > 0 ) {
      sqlWhere += "AND ( master_client_id = " + masterClientId + " ) ";
    }
    if ( clientId > 0 ) {
      sqlWhere += "AND ( client_id = " + clientId + " ) ";
    }
    String sql = sqlSelectFrom + sqlWhere;
    ClientsBean bean = populateBean( sql );
    return bean;
  }

  public ClientsBean generateClientsBeanByPaymentType( int paymentType ) {
    String sqlSelectFrom = ClientQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( payment_type = " + paymentType + " ) ";
    String sql = sqlSelectFrom + sqlWhere;
    ClientsBean bean = populateBean( sql );
    return bean;
  }

  public ClientsBean generateClientsBeanByClientIds( String strClientIds ) {
    String sqlSelectFrom = ClientQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( client_id IN ( "
        + StringEscapeUtils.escapeSql( strClientIds ) + " ) ) ";
    String sql = sqlSelectFrom + sqlWhere;
    ClientsBean bean = populateBean( sql );
    return bean;
  }

  private ClientsBean populateBean( String sql ) {
    ClientsBean bean = new ClientsBean();
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      // DLog.debug( lctx , "Perform " + sql );
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        bean.addClientBean( ClientQuery.populateBean( rs ) );
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to load clients bean , " + e );
    } finally {
      conn.disconnect( true );
    }
    return bean;
  }

}
