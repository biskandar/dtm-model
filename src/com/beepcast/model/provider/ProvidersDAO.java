package com.beepcast.model.provider;

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

public class ProvidersDAO {

  static final DLogContext lctx = new SimpleContext( "ProvidersDAO" );

  private DatabaseLibrary dbLib;

  public ProvidersDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalRecords( boolean active , String type , String direction ) {
    long totalRecords = 0;

    // compose sql
    String sqlSelect = "SELECT COUNT(t.id) AS total ";
    String sqlFrom = "FROM ( " + sql( active , type , direction ) + " ) t ";
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

  public List selectRecords( boolean active , String type , String direction ,
      int top , int limit ) {
    List listRecords = new ArrayList();

    // compose sql
    String sql = sql( active , type , direction );
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
      ProviderBean bean = ProviderQuery.populateRecord( qi );
      if ( bean == null ) {
        continue;
      }
      listRecords.add( bean );
    }

    return listRecords;
  }

  private String sql( boolean active , String type , String direction ) {
    String sqlSelectFrom = ProviderQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = " + ( active ? 1 : 0 ) + " ) ";
    if ( !StringUtils.isBlank( type ) ) {
      sqlWhere += "AND ( `type` = '" + StringEscapeUtils.escapeSql( type )
          + "' ) ";
    }
    if ( !StringUtils.isBlank( direction ) ) {
      sqlWhere += "AND ( direction = '"
          + StringEscapeUtils.escapeSql( direction ) + "' ) ";
    }
    String sqlOrder = "ORDER BY provider_id ASC , master_id ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder;
    return sql;
  }

}
