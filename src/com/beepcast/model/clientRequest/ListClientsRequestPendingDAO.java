package com.beepcast.model.clientRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.beepcast.client.request.ClientRequestInfoBean;
import com.beepcast.client.request.ResStatus;
import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.dbmanager.common.ClientCommon;
import com.beepcast.dbmanager.table.TClient;
import com.beepcast.dbmanager.util.DateTimeFormat;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientsRequestPendingDAO {

  static final DLogContext lctx = new SimpleContext(
      "ListClientsRequestPendingDAO" );

  private DatabaseLibrary dbLib;

  public ListClientsRequestPendingDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalActiveRecords( String memberClientIds , int display ) {
    long totalRecords = 0;

    // compose sql
    String sqlSelect = "SELECT COUNT(*) AS total ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( memberClientIds , display );
    String sql = sqlSelect + sqlFrom + sqlWhere;

    // execute and fetch query
    QueryResult qr = dbLib.simpleQuery( "transactiondb" , sql );
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

  public List selectActiveRecords( String memberClientIds , int display ,
      int top , int limit , int orderby ) {
    List listRecords = new ArrayList();

    // compose sql
    String sqlSelect = "SELECT * ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( memberClientIds , display );

    String sqlOrderBy = "";
    switch ( orderby ) {
    case ListClientsRequestPendingService.ORDERBY_ID_ASC :
      sqlOrderBy = "ORDER BY id ASC ";
      break;
    case ListClientsRequestPendingService.ORDERBY_ID_DESC :
      sqlOrderBy = "ORDER BY id DESC ";
      break;
    case ListClientsRequestPendingService.ORDERBY_MODIFY_ASC :
      sqlOrderBy = "ORDER BY res_date_updated ASC , req_date_updated ASC ";
      break;
    case ListClientsRequestPendingService.ORDERBY_MODIFY_DESC :
      sqlOrderBy = "ORDER BY res_date_updated DESC , req_date_updated DESC ";
      break;
    }

    String sqlLimit = "LIMIT " + top + " , " + limit + " ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrderBy + sqlLimit;

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        ClientRequestInfoBean clientRequestInfoBean = populateBean( rs );
        if ( clientRequestInfoBean != null ) {
          listRecords.add( clientRequestInfoBean );
        }
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to select active events , " + e );
    } finally {
      conn.disconnect( true );
    }

    return listRecords;
  }

  private String sqlFrom() {
    String sqlFrom = "FROM client_request ";
    return sqlFrom;
  }

  private String sqlWhere( String memberClientIds , int display ) {
    String sqlWhere = "WHERE ( active = 1 ) ";
    if ( !StringUtils.isBlank( memberClientIds ) ) {
      sqlWhere += "AND ( client_id IN ( " + memberClientIds + " ) ) ";
    }
    sqlWhere += "AND ( req_status IS NOT NULL ) ";
    sqlWhere += "AND ( ( res_status IS NULL ) OR ( res_status = '"
        + ResStatus.ACCEPTED + "' ) ) ";
    if ( display == ListClientRequestHistoryService.DISPLAY_ON ) {
      sqlWhere += "AND ( display = 1 ) ";
    }
    if ( display == ListClientRequestHistoryService.DISPLAY_OFF ) {
      sqlWhere += "AND ( display = 0 ) ";
    }
    return sqlWhere;
  }

  private ClientRequestInfoBean populateBean( ResultSet rs )
      throws SQLException {
    ClientRequestInfoBean bean = null;

    // verify client
    int clientId = rs.getInt( "client_id" );
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to populate client request info bean "
          + ", Found invalid zero client id " );
      return bean;
    }
    TClient clientBean = ClientCommon.getClient( clientId );
    if ( clientBean == null ) {
      DLog.warning( lctx , "Failed to populate client request info bean "
          + ", Found invalid client id = " + clientId );
      return bean;
    }
    String companyName = clientBean.getCompanyName();

    bean = new ClientRequestInfoBean();
    bean.setId( rs.getInt( "id" ) );
    bean.setTicketId( rs.getString( "ticket_id" ) );
    bean.setClientId( clientId );
    bean.setCompanyName( companyName );
    bean.setContactName( rs.getString( "contact_name" ) );
    bean.setNotfContactEmail( rs.getBoolean( "notf_contact_email" ) );
    bean.setContactEmail( rs.getString( "contact_email" ) );
    bean.setNotfContactNumber( rs.getBoolean( "notf_contact_number" ) );
    bean.setContactNumber( rs.getString( "contact_number" ) );
    bean.setCommand( rs.getString( "command" ) );
    bean.setValue( rs.getString( "value" ) );
    bean.setReqStatus( rs.getString( "req_status" ) );
    bean.setResStatus( rs.getString( "res_status" ) );
    bean.setReqDescription( rs.getString( "req_description" ) );
    bean.setResDescription( rs.getString( "res_description" ) );
    bean.setDisplay( rs.getBoolean( "display" ) );
    bean.setActive( rs.getBoolean( "active" ) );
    bean.setDateInserted( DateTimeFormat.convertToDate( rs
        .getString( "date_inserted" ) ) );
    bean.setReqDateUpdated( DateTimeFormat.convertToDate( rs
        .getString( "req_date_updated" ) ) );
    bean.setResDateUpdated( DateTimeFormat.convertToDate( rs
        .getString( "res_date_updated" ) ) );

    return bean;
  }

}
