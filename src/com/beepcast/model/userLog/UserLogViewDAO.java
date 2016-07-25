package com.beepcast.model.userLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UserLogViewDAO {

  static final DLogContext lctx = new SimpleContext( "UserLogViewDAO" );

  private DatabaseLibrary dbLib;

  public UserLogViewDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public int totalRecords( int clientType , int masterClientId ,
      String findUserId , String findActionText , Date dateStarted ,
      Date dateFinished ) {
    int totalRecords = 0;

    // compose sql

    String sqlSelect = "SELECT COUNT(*) AS total ";
    String sqlFrom = "FROM ( "
        + UserLogViewQuery.sql( clientType , masterClientId , findUserId ,
            findActionText , dateStarted , dateFinished ) + " ) t ";
    String sql = sqlSelect + sqlFrom;

    // execute sql
    // DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "transactiondb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return totalRecords;
    }

    // populate record
    Iterator it = qr.iterator();
    if ( !it.hasNext() ) {
      return totalRecords;
    }
    QueryItem qi = (QueryItem) it.next();
    if ( qi == null ) {
      return totalRecords;
    }
    try {
      totalRecords = Integer.parseInt( qi.getFirstValue() );
    } catch ( NumberFormatException e ) {
    }

    return totalRecords;
  }

  public List listRecords( int clientType , int masterClientId ,
      String findUserId , String findActionText , Date dateStarted ,
      Date dateFinished , int top , int limit ) {
    List listRecords = new ArrayList();

    // compose sql

    String sql = UserLogViewQuery.sql( clientType , masterClientId ,
        findUserId , findActionText , dateStarted , dateFinished );
    sql += "LIMIT " + top + " , " + limit + " ";

    // execute sql
    DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "transactiondb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return listRecords;
    }

    // populate record
    UserLogViewBean bean = null;
    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      bean = UserLogViewQuery.populateRecord( (QueryItem) it.next() );
      if ( bean == null ) {
        continue;
      }
      listRecords.add( bean );
    }

    return listRecords;
  }

}
