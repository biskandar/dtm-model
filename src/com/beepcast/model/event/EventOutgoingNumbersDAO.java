package com.beepcast.model.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EventOutgoingNumbersDAO {

  static final DLogContext lctx = new SimpleContext( "EventOutgoingNumbersDAO" );

  private DatabaseLibrary dbLib;

  public EventOutgoingNumbersDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public List selectByEventId( int eventId ) {
    String sqlSelect = "SELECT DISTINCT o.outgoing_number ";
    String sqlFrom = "FROM outgoing_number_to_provider o ";
    sqlFrom += "INNER JOIN `client` c ON c.group_connection_id = o.group_connection_id ";
    sqlFrom += "INNER JOIN `event`  e ON e.client_id = c.client_id ";
    String sqlWhere = "WHERE ( o.active = 1 ) AND ( c.active = 1 ) AND ( e.active = 1 ) ";
    sqlWhere += "AND ( e.event_id = " + eventId + " ) ";
    String sql = sqlSelect + sqlFrom + sqlWhere;
    return populateRecords( sql );
  }

  public List selectByClientId( int clientId ) {
    String sqlSelect = "SELECT DISTINCT o.outgoing_number ";
    String sqlFrom = "FROM outgoing_number_to_provider o ";
    sqlFrom += "INNER JOIN `client` c ON c.group_connection_id = o.group_connection_id ";
    String sqlWhere = "WHERE ( o.active = 1 ) AND ( c.active = 1 ) ";
    sqlWhere += "AND ( c.client_id = " + clientId + " ) ";
    String sql = sqlSelect + sqlFrom + sqlWhere;
    return populateRecords( sql );
  }

  public List selectByGroupClientConnId( int groupClientConnId ) {
    String sqlSelect = "SELECT DISTINCT o.outgoing_number ";
    String sqlFrom = "FROM outgoing_number_to_provider o ";
    String sqlWhere = "WHERE ( o.active = 1 ) ";
    sqlWhere += "AND ( o.group_connection_id = " + groupClientConnId + " ) ";
    String sql = sqlSelect + sqlFrom + sqlWhere;
    return populateRecords( sql );
  }

  private List populateRecords( String sql ) {
    List list = new ArrayList();

    if ( StringUtils.isBlank( sql ) ) {
      return list;
    }

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return list;
    }

    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi == null ) {
        continue;
      }
      String number = qi.getFirstValue();
      if ( StringUtils.isBlank( number ) ) {
        continue;
      }
      list.add( number );
    }

    return list;
  }

}
