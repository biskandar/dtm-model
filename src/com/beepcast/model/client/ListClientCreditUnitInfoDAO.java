package com.beepcast.model.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.model.util.DateTimeFormat;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ListClientCreditUnitInfoDAO {

  static final DLogContext lctx = new SimpleContext(
      "ListClientCreditUnitInfoDAO" );

  private DatabaseLibrary dbLib;

  public ListClientCreditUnitInfoDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public long totalBeans( int clientId ) {
    long totalRecords = 0;
    if ( clientId < 1 ) {
      return totalRecords;
    }

    String sqlSelect = "SELECT id ";
    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( clientId );

    String sql = sqlSelect + sqlFrom + sqlWhere;

    sql = "SELECT COUNT(*) AS total FROM ( " + sql + " ) tmp ";

    // DLog.debug( lctx , "Perform " + sql );

    // execute and fetch query
    QueryResult qr = dbLib.simpleQuery( "transactiondb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      Iterator iqr = qr.iterator();
      QueryItem qi = null;
      if ( iqr.hasNext() ) {
        qi = (QueryItem) iqr.next();
      }
      if ( qi != null ) {
        try {
          totalRecords = Long.parseLong( qi.getFirstValue() );
        } catch ( NumberFormatException e ) {
        }
      }
    }

    return totalRecords;
  }

  public List selectBeans( int clientId , int top , int limit ) {
    List list = new ArrayList();
    if ( clientId < 1 ) {
      return list;
    }

    String sqlSelect = "SELECT id , client_id , unit , description ";
    sqlSelect += ", active , date_inserted , date_updated ";

    String sqlFrom = sqlFrom();
    String sqlWhere = sqlWhere( clientId );
    String sqlOrder = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT " + top + " , " + limit + " ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder + sqlLimit;

    // DLog.debug( lctx , "Perform " + sql );

    // execute and fetch query
    QueryResult qr = dbLib.simpleQuery( "transactiondb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      Iterator iqr = qr.iterator();
      QueryItem qi = null;
      while ( iqr.hasNext() ) {
        qi = (QueryItem) iqr.next();
        if ( qi == null ) {
          continue;
        }
        ClientCreditUnitBean bean = populateBean( qi );
        if ( bean == null ) {
          continue;
        }
        list.add( bean );
      }
    }

    return list;
  }

  private String sqlFrom() {
    String sqlFrom = "FROM client_credit_unit ";
    return sqlFrom;
  }

  private String sqlWhere( int clientId ) {
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    return sqlWhere;
  }

  private ClientCreditUnitBean populateBean( QueryItem qi ) {
    ClientCreditUnitBean bean = new ClientCreditUnitBean();

    String stemp;
    int itemp;
    double dtemp;
    boolean btemp;

    boolean valid = true;

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        if ( itemp < 1 ) {
          valid = false;
        } else {
          bean.setId( itemp );
        }
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 1 ); // client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        if ( itemp < 1 ) {
          valid = false;
        } else {
          bean.setClientId( itemp );
        }
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 2 ); // unit
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        dtemp = Double.parseDouble( stemp );
        bean.setUnit( dtemp );
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 3 ); // description
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setDescription( stemp );
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 4 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        btemp = itemp > 0 ? true : false;
        bean.setActive( btemp );
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 5 ); // date_inserted
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDateInserted( DateTimeFormat.convertToDate( stemp ) );
    }
    stemp = (String) qi.get( 6 ); // date_inserted
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDateUpdated( DateTimeFormat.convertToDate( stemp ) );
    }

    if ( !valid ) {
      bean = null;
    }

    return bean;
  }

}
