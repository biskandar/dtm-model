package com.beepcast.model.clientSenderId;

import java.util.Iterator;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToSenderIdInfoDAO {

  static final DLogContext lctx = new SimpleContext( "ClientToSenderIdInfoDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public ClientToSenderIdInfoDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public ClientToSenderIdInfoBean selectBean( int clientToSenderIdId ) {
    ClientToSenderIdInfoBean bean = null;

    // populate sql
    String sqlSelectFrom = ClientToSenderIdInfoQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( id = " + clientToSenderIdId + " ) ";
    String sql = sqlSelectFrom + sqlWhere;

    // populate record
    bean = selectBean( sql );
    return bean;
  }

  private ClientToSenderIdInfoBean selectBean( String sql ) {
    ClientToSenderIdInfoBean bean = null;

    // execute sql
    // DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }
    Iterator it = qr.iterator();
    if ( !it.hasNext() ) {
      return bean;
    }

    // populate record
    bean = ClientToSenderIdInfoQuery.populateRecord( (QueryItem) it.next() );
    return bean;
  }

}
