package com.beepcast.model.clientFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientFileDAO {

  static final DLogContext lctx = new SimpleContext( "ListClientFileDAO" );

  private DatabaseLibrary dbLib;

  public ListClientFileDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalActiveRecords( int clientId , String fileType ) {
    long totalRecords = 0;

    if ( clientId < 1 ) {
      return totalRecords;
    }

    String sqlSelect = "SELECT COUNT(id) AS total ";
    String sqlFrom = ClientFileQuery.sqlFrom();
    String sqlWhere = sqlWhere( clientId , fileType );
    String sql = sqlSelect + sqlFrom + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

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

  public List selectActiveRecords( int clientId , String fileType , int top ,
      int limit ) {
    List listRecords = new ArrayList();

    String sqlSelectFrom = ClientFileQuery.sqlSelectFrom();
    String sqlWhere = sqlWhere( clientId , fileType );
    String sqlOrder = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT " + top + " , " + limit + " ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "transactiondb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return listRecords;
    }

    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi == null ) {
        continue;
      }
      ClientFileBean bean = ClientFileQuery.populateRecord( qi );
      if ( bean == null ) {
        continue;
      }
      listRecords.add( bean );
    }

    return listRecords;
  }

  private String sqlWhere( int clientId , String fileType ) {
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    sqlWhere += "AND ( active = 1 ) ";
    if ( !StringUtils.isBlank( fileType ) ) {
      sqlWhere += "AND ( file_type = '"
          + StringEscapeUtils.escapeSql( fileType ) + "' ) ";
    }
    return sqlWhere;
  }

}
