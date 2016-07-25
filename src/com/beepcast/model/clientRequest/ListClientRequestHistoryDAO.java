package com.beepcast.model.clientRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.beepcast.client.request.ClientRequestBean;
import com.beepcast.client.request.ResStatus;
import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.dbmanager.util.DateTimeFormat;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientRequestHistoryDAO {

  static final DLogContext lctx = new SimpleContext(
      "ListClientRequestHistoryDAO" );

  private DatabaseLibrary dbLib;

  public ListClientRequestHistoryDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalActiveRecords( int clientId , int type , int display ) {
    long totalRecords = 0;

    if ( clientId < 1 ) {
      return totalRecords;
    }

    // compose sql
    String sqlSelect = "SELECT COUNT(*) AS total ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( clientId , type , display );
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

  public List selectActiveRecords( int clientId , int type , int display ,
      int top , int limit , int orderby ) {
    List listRecords = new ArrayList();

    if ( clientId < 1 ) {
      return listRecords;
    }

    // compose sql
    String sqlSelect = "SELECT * ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( clientId , type , display );

    String sqlOrderBy = "";
    switch ( orderby ) {
    case ListClientRequestHistoryService.ORDERBY_ID_ASC :
      sqlOrderBy = "ORDER BY id ASC ";
      break;
    case ListClientRequestHistoryService.ORDERBY_ID_DESC :
      sqlOrderBy = "ORDER BY id DESC ";
      break;
    case ListClientRequestHistoryService.ORDERBY_MODIFY_ASC :
      sqlOrderBy = "ORDER BY res_date_updated ASC , req_date_updated ASC ";
      break;
    case ListClientRequestHistoryService.ORDERBY_MODIFY_DESC :
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
        ClientRequestBean clientRequestBean = populateBean( rs );
        listRecords.add( clientRequestBean );
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

  private String sqlWhere( int clientId , int type , int display ) {
    String sqlWhere = "WHERE ( active = 1 ) ";
    if ( clientId > 0 ) {
      sqlWhere += "AND ( client_id = " + clientId + " ) ";
    }
    if ( type == ListClientRequestHistoryService.TYPE_REQ ) {
      sqlWhere += "AND ( req_status IS NOT NULL ) AND ( ( ( res_status IS NULL ) || ( res_status = '"
          + ResStatus.ACCEPTED + "' ) ) ) ";
    }
    if ( type == ListClientRequestHistoryService.TYPE_RES ) {
      sqlWhere += "AND ( req_status IS NOT NULL ) AND ( ( ( res_status IS NOT NULL ) && ( res_status != '"
          + ResStatus.ACCEPTED + "' ) ) ) ";
    }
    if ( display == ListClientRequestHistoryService.DISPLAY_ON ) {
      sqlWhere += "AND ( display = 1 ) ";
    }
    if ( display == ListClientRequestHistoryService.DISPLAY_OFF ) {
      sqlWhere += "AND ( display = 0 ) ";
    }
    return sqlWhere;
  }

  private ClientRequestBean populateBean( ResultSet rs ) throws SQLException {
    ClientRequestBean bean = new ClientRequestBean();
    bean.setId( rs.getInt( "id" ) );
    bean.setTicketId( rs.getString( "ticket_id" ) );
    bean.setClientId( rs.getInt( "client_id" ) );
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
