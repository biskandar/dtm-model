package com.beepcast.model.client;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToDedicatedModemsDAO {

  static final DLogContext lctx = new SimpleContext(
      "ClientToDedicatedModemsDAO" );

  private DatabaseLibrary dbLib;

  public ClientToDedicatedModemsDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public ClientToDedicatedModemsBean generateBean( int clientId ) {
    ClientToDedicatedModemsBean bean = new ClientToDedicatedModemsBean();
    bean.setClientId( clientId );

    String sqlSelect = "SELECT modem_number ";
    String sqlFrom = "FROM modem_number_to_client ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";

    String sql = sqlSelect + sqlFrom + sqlWhere;

    // DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }
    Iterator iter = qr.iterator();
    while ( iter.hasNext() ) {
      QueryItem qi = (QueryItem) iter.next();
      if ( qi == null ) {
        continue;
      }
      String modemNumber = qi.getFirstValue();
      if ( StringUtils.isBlank( modemNumber ) ) {
        continue;
      }
      bean.getModemNumbers().add( modemNumber );
    }

    return bean;
  }

}
