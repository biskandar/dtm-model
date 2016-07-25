package com.beepcast.model.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EventEmailsDAO {

  static final DLogContext lctx = new SimpleContext( "EventEmailsDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public EventEmailsDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public List selectByEventId( long eventId ) {
    List listBeans = new ArrayList();
    if ( eventId < 1 ) {
      return listBeans;
    }

    // compose sql
    String sqlSelectFrom = EventEmailQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( event_id = " + eventId + " ) ";
    String sqlOrder = "ORDER BY process_step ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder;

    // execute sql
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return listBeans;
    }

    // populate records
    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi == null ) {
        continue;
      }
      EventEmailBean bean = EventEmailQuery.populateRecord( qi );
      if ( bean == null ) {
        continue;
      }
      listBeans.add( bean );
    }

    return listBeans;
  }

}
