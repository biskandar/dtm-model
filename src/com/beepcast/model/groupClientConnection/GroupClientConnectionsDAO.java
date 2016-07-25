package com.beepcast.model.groupClientConnection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GroupClientConnectionsDAO {

  static final DLogContext lctx = new SimpleContext(
      "GroupClientConnectionsDAO" );

  private DatabaseLibrary dbLib;

  public GroupClientConnectionsDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalRecords( boolean active ) {
    long totalRecords = 0;

    // compose sql
    String sqlSelect = "SELECT COUNT(t.id) AS total ";
    String sqlFrom = "FROM ( " + sql( active ) + " ) t ";
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

  public List selectRecords( boolean active , int top , int limit ) {
    List listRecords = new ArrayList();

    // compose sql
    String sql = sql( active );
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
      GroupClientConnectionBean bean = GroupClientConnectionQuery
          .populateRecord( qi );
      if ( bean == null ) {
        continue;
      }
      listRecords.add( bean );
    }

    return listRecords;
  }

  private String sql( boolean active ) {
    String sqlSelectFrom = GroupClientConnectionQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = " + ( active ? 1 : 0 ) + " ) ";
    String sqlOrder = "ORDER BY name ASC , id ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder;
    return sql;
  }

}
