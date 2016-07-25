package com.beepcast.model.clientSenderId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToSenderIdsDAO {

  static final DLogContext lctx = new SimpleContext( "ClientToSenderIdsDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public ClientToSenderIdsDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public int totalRecords( int clientId , String outgoingNumber ,
      String senderId ) {
    int totalRecords = 0;

    String sqlSelect = "SELECT COUNT(t.id) ";
    String sqlFrom = "FROM ( "
        + sqlSelectFromWhere( clientId , outgoingNumber , senderId ) + " ) t ";
    String sql = sqlSelect + sqlFrom;

    // execute and fetch query
    // DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return totalRecords;
    }
    Iterator it = qr.iterator();
    if ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi != null ) {
        try {
          totalRecords = Integer.parseInt( qi.getFirstValue() );
        } catch ( NumberFormatException e ) {
        }
      }
    }

    return totalRecords;
  }

  public List listRecords( int clientId , String outgoingNumber ,
      String senderId , int top , int limit ) {
    List listRecords = new ArrayList();

    String sqlSelectFromWhere = sqlSelectFromWhere( clientId , outgoingNumber ,
        senderId );
    String sqlOrder = "ORDER BY id ASC ";
    String sqlLimit = "LIMIT " + top + " , " + limit;
    String sql = sqlSelectFromWhere + sqlOrder + sqlLimit;

    // execute sql
    // DLog.debug( lctx , "Perform " + sql );
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
      ClientToSenderIdBean bean = ClientToSenderIdQuery.populateRecord( qi );
      if ( bean == null ) {
        continue;
      }
      listRecords.add( bean );
    }

    return listRecords;
  }

  private String sqlSelectFromWhere( int clientId , String outgoingNumber ,
      String senderId ) {
    String sqlSelectFrom = ClientToSenderIdQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    if ( clientId > 0 ) {
      sqlWhere += "AND ( client_id = " + clientId + " ) ";
    }
    if ( outgoingNumber != null ) {
      sqlWhere += "AND ( outgoing_number = '"
          + StringEscapeUtils.escapeSql( outgoingNumber ) + "' ) ";
    }
    if ( senderId != null ) {
      sqlWhere += "AND ( senderID = '" + StringEscapeUtils.escapeSql( senderId )
          + "' ) ";
    }
    String sqlSelectFromWhere = sqlSelectFrom + sqlWhere;
    return sqlSelectFromWhere;
  }

}
