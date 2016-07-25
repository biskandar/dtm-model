package com.beepcast.model.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class MasterClientUsersInfoDAO {

  static final DLogContext lctx = new SimpleContext( "MasterClientUsersInfoDAO" );

  private DatabaseLibrary dbLib;

  public MasterClientUsersInfoDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public List getClientUsersBasedOnMasterClientId( int masterClientId ,
      String clientStates ) {
    List list = new ArrayList();

    // compose sql

    String sqlSelect = "SELECT c.master_client_id , c.client_id ";
    sqlSelect += ", c.company_name , u.id AS 'user_id' , u.name ";
    sqlSelect += ", u.email , u.phone ";

    String sqlFrom = "FROM `user` u ";
    sqlFrom += "INNER JOIN `client` c ON c.client_id = u.client_id ";

    String sqlWhere = "WHERE ( u.display = 1 ) AND ( u.active = 1 ) ";
    sqlWhere += "AND ( c.display = 1 ) AND ( c.active = 1 ) ";
    if ( !StringUtils.isBlank( clientStates ) ) {
      sqlWhere += "AND ( c.state IN ( " + clientStates + " ) ) ";
    }

    String sqlOrder = "ORDER BY c.master_client_id ASC ";
    sqlOrder += ", c.company_name ASC , u.name ASC ";

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

  private MasterClientUsersInfoBean populateRecord( QueryItem qi ) {
    MasterClientUsersInfoBean bean = null;

    if ( qi == null ) {
      return bean;
    }

    bean = new MasterClientUsersInfoBean();

    String stemp;

    stemp = (String) qi.get( 0 ); // master_client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setMasterClientId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setClientId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 2 ); // company_name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setCompanyName( stemp );
    }

    stemp = (String) qi.get( 3 ); // user_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setUserId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 4 ); // name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setName( stemp );
    }

    stemp = (String) qi.get( 5 ); // email
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setEmail( stemp );
    }

    stemp = (String) qi.get( 6 ); // phone
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setPhone( stemp );
    }

    return bean;
  }

}
