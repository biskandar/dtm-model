package com.beepcast.model.number;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientConnectionDAO {

  static final DLogContext lctx = new SimpleContext( "ClientConnectionDAO" );

  private DatabaseLibrary dbLib;

  public ClientConnectionDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insertBean( ClientConnectionBean bean ) {
    boolean result = false;

    String description = StringUtils.trimToEmpty( bean.getDescription() );
    int intActive = bean.isActive() ? 1 : 0;

    String sqlInsert = "INSERT INTO client_connection ";
    sqlInsert += "( group_client_connection_id , number , description ";
    sqlInsert += ", active , date_inserted , date_updated ) ";
    String sqlValues = "VALUES ( " + bean.getGroupClientConnectionId() + " , '"
        + StringEscapeUtils.escapeSql( bean.getNumber() ) + "' , '"
        + StringEscapeUtils.escapeSql( description ) + "' , " + intActive
        + " , NOW() , NOW() ) ";

    String sql = sqlInsert + sqlValues;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public ClientConnectionBean selectBean( int groupClientConnectionId ,
      String number ) {
    ClientConnectionBean bean = null;

    String sqlSelect = "SELECT id , group_client_connection_id ";
    sqlSelect += ", number , description , active ";
    String sqlFrom = "FROM client_connection ";
    String sqlWhere = "WHERE ( group_client_connection_id = "
        + groupClientConnectionId + " ) ";
    sqlWhere += "AND ( number = '" + StringEscapeUtils.escapeSql( number )
        + "' ) ";

    String sql = sqlSelect + sqlFrom + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }
    QueryItem qi = null;
    Iterator iter = qr.iterator();
    if ( iter.hasNext() ) {
      qi = (QueryItem) iter.next();
    }
    if ( qi == null ) {
      return bean;
    }

    bean = populateRecord( qi );

    return bean;
  }

  public boolean deleteBean( ClientConnectionBean bean ) {
    boolean result = false;

    String sqlUpdate = "UPDATE client_connection ";
    String sqlSet = "SET active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE id = " + bean.getId() + " ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public List listBeans( int groupClientConnectionId , boolean active ) {
    List list = new ArrayList();

    int intActive = active ? 1 : 0;

    String sqlSelect = "SELECT id , group_client_connection_id ";
    sqlSelect += ", number , description , active ";
    String sqlFrom = "FROM client_connection ";
    String sqlWhere = "WHERE ( active = " + intActive + " ) ";
    sqlWhere += "AND ( group_client_connection_id = " + groupClientConnectionId
        + " ) ";
    String sqlOrder = "ORDER BY id ASC ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      list = populateRecords( qr , list );
    }

    return list;
  }

  private List populateRecords( QueryResult qr , List list ) {
    Iterator iter = qr.iterator();
    while ( iter.hasNext() ) {
      QueryItem qi = (QueryItem) iter.next();
      if ( qi == null ) {
        continue;
      }
      ClientConnectionBean bean = populateRecord( qi );
      if ( bean == null ) {
        continue;
      }
      list.add( bean );
    }
    return list;
  }

  private ClientConnectionBean populateRecord( QueryItem qi ) {
    ClientConnectionBean bean = new ClientConnectionBean();

    String stemp;
    int itemp;

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        if ( itemp > 0 ) {
          bean.setId( itemp );
        }
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // group_client_connection_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        if ( itemp > 0 ) {
          bean.setGroupClientConnectionId( itemp );
        }
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 2 ); // number
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setNumber( stemp );
    }

    stemp = (String) qi.get( 3 ); // description
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setDescription( stemp );
    }

    stemp = (String) qi.get( 4 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        bean.setActive( itemp > 0 );
      } catch ( NumberFormatException e ) {
      }
    }

    return bean;
  }

}
