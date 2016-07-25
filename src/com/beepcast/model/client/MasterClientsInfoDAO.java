package com.beepcast.model.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class MasterClientsInfoDAO {

  static final DLogContext lctx = new SimpleContext( "MasterClientsInfoDAO" );

  private DatabaseLibrary dbLib;

  public MasterClientsInfoDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public List getClientsBasedOnMasterClientId( int masterClientId ) {
    return getClientsBasedOnMasterClientId( masterClientId , null );
  }

  public List getClientsBasedOnMasterClientId( int masterClientId ,
      String clientStates ) {
    List list = new ArrayList();

    // compose sql

    String sqlSelect = "SELECT c.master_client_id , c.client_id ";
    sqlSelect += ", c.company_name  , c.manager , c.email , c.phone ";

    String sqlFrom = "FROM client c ";

    String sqlWhere = "WHERE ( c.display = 1 ) AND ( c.active = 1 ) ";
    if ( masterClientId > 0 ) {
      sqlWhere += "AND ( c.master_client_id = " + masterClientId + " ) ";
    }
    if ( !StringUtils.isBlank( clientStates ) ) {
      sqlWhere += "AND ( c.state IN ( " + clientStates + " ) ) ";
    }

    String sqlOrder = "ORDER BY c.company_name ASC ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    // execute sql

    // DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return list;
    }

    // fetch records

    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      list.add( populateRecord( (QueryItem) it.next() ) );
    }

    return list;
  }

  private MasterClientsInfoBean populateRecord( QueryItem qi ) {
    MasterClientsInfoBean bean = new MasterClientsInfoBean();

    String stemp;
    int itemp;

    stemp = (String) qi.get( 0 ); // master_client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setMasterClientId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setClientId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 2 ); // company_name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setCompanyName( stemp );
    }

    stemp = (String) qi.get( 3 ); // manager
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setManager( stemp );
    }

    stemp = (String) qi.get( 4 ); // email
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setEmail( stemp );
    }

    stemp = (String) qi.get( 5 ); // phone
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setPhone( stemp );
    }

    return bean;
  }

}
