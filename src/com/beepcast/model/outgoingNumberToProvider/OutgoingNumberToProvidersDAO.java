package com.beepcast.model.outgoingNumberToProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class OutgoingNumberToProvidersDAO {

  static final DLogContext lctx = new SimpleContext(
      "OutgoingNumberToProvidersDAO" );

  private DatabaseLibrary dbLib;

  public OutgoingNumberToProvidersDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalRecords( boolean active , int groupConnectionId ,
      String outgoingNumber , int level ) {
    long totalRecords = 0;

    // compose sql
    String sqlSelect = "SELECT COUNT(t.id) AS total ";
    String sqlFrom = "FROM ( "
        + sql( active , groupConnectionId , outgoingNumber , level ) + " ) t ";
    String sql = sqlSelect + sqlFrom;

    // execute sql
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return totalRecords;
    }

    // populate record
    try {
      Iterator it = qr.iterator();
      if ( !it.hasNext() ) {
        return totalRecords;
      }
      QueryItem qi = (QueryItem) it.next();
      if ( qi == null ) {
        return totalRecords;
      }
      totalRecords = Long.parseLong( qi.getFirstValue() );
    } catch ( Exception e ) {
    }

    return totalRecords;
  }

  public List selectRecords( boolean active , int groupConnectionId ,
      String outgoingNumber , int level , int top , int limit ) {
    List listRecords = new ArrayList();

    // compose sql
    String sql = sql( active , groupConnectionId , outgoingNumber , level );
    sql += "LIMIT " + top + " , " + limit + " ";

    // execute sql
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return listRecords;
    }

    // populate records
    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi == null ) {
        continue;
      }
      OutgoingNumberToProviderBean bean = OutgoingNumberToProviderQuery
          .populateRecord( qi );
      if ( bean == null ) {
        continue;
      }
      listRecords.add( bean );
    }

    return listRecords;
  }

  private String sql( boolean active , int groupConnectionId ,
      String outgoingNumber , int level ) {
    String sqlSelectFrom = OutgoingNumberToProviderQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = " + ( active ? 1 : 0 ) + " ) ";
    if ( groupConnectionId > 0 ) {
      sqlWhere += "AND ( group_connection_id = " + groupConnectionId + " ) ";
    }
    if ( !StringUtils.isBlank( outgoingNumber ) ) {
      sqlWhere += "AND ( outgoing_number = '"
          + StringEscapeUtils.escapeSql( outgoingNumber ) + "' ) ";
    }
    if ( level > -1 ) {
      sqlWhere += "AND ( level = " + level + " ) ";
    }
    String sqlOrder = "ORDER BY group_connection_id ASC ";
    sqlOrder += ", outgoing_number ASC , level ASC , country_code ASC ";
    sqlOrder += ", telco_code ASC , provider_id ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder;
    return sql;
  }

}
