package com.beepcast.model.clientNumber;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.dbmanager.util.DateTimeFormat;
import com.beepcast.model.number.ClientConnectionBean;
import com.beepcast.model.number.ClientConnectionService;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToNumbersDAO {

  static final DLogContext lctx = new SimpleContext( "ClientToNumbersDAO" );

  private DatabaseLibrary dbLib;

  public ClientToNumbersDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public ClientToNumbersBean generateBean( int clientId ) {
    ClientToNumbersBean clientToNumbersBean = new ClientToNumbersBean();
    clientToNumbersBean.setClientId( clientId );

    String sqlSelect = "SELECT id , client_id ";
    sqlSelect += ", number , description , active_started ";
    sqlSelect += ", active_stopped , active ";
    String sqlFrom = "FROM client_to_number ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";

    String sql = sqlSelect + sqlFrom + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return clientToNumbersBean;
    }
    Iterator iter = qr.iterator();
    while ( iter.hasNext() ) {
      QueryItem qi = (QueryItem) iter.next();
      if ( qi == null ) {
        continue;
      }
      ClientToNumberBean clientToNumberBean = populateRecord( qi );
      if ( clientToNumberBean == null ) {
        continue;
      }
      clientToNumbersBean.getBeans().add( clientToNumberBean );
    }

    return clientToNumbersBean;
  }

  public int synchBeanNumbers( int clientId , int groupClientConnectionId ,
      Date activeStarted , Date activeStopped ) {
    int result = 0;

    List listNewNumbers = new ArrayList();

    ClientConnectionService clientConnectionService = new ClientConnectionService();
    List listClientConnectionBeans = clientConnectionService
        .listActiveBeans( groupClientConnectionId );
    if ( listClientConnectionBeans == null ) {
      return result;
    }

    DLog.debug( lctx , "Found total " + listClientConnectionBeans.size()
        + " numbers available on group client connection id = "
        + groupClientConnectionId );

    ClientToNumbersBean clientToNumbersBean = generateBean( clientId );
    List listCurBeans = clientToNumbersBean.getBeans();

    boolean foundCurBean;
    Iterator iter1 = listClientConnectionBeans.iterator();
    while ( iter1.hasNext() ) {
      ClientConnectionBean clientConnectionBean = (ClientConnectionBean) iter1
          .next();
      if ( clientConnectionBean == null ) {
        continue;
      }
      String number1 = clientConnectionBean.getNumber();
      if ( StringUtils.isBlank( number1 ) ) {
        continue;
      }
      if ( listCurBeans != null ) {
        foundCurBean = false;
        Iterator iter2 = listCurBeans.iterator();
        while ( iter2.hasNext() ) {
          ClientToNumberBean clientToNumberBean = (ClientToNumberBean) iter2
              .next();
          if ( clientToNumberBean == null ) {
            continue;
          }
          String number2 = clientToNumberBean.getNumber();
          if ( StringUtils.isBlank( number2 ) ) {
            continue;
          }
          if ( StringUtils.equals( number1 , number2 ) ) {
            foundCurBean = true;
            break;
          }
        }
        if ( foundCurBean ) {
          continue;
        }
      }
      listNewNumbers.add( number1 );
    }

    if ( listNewNumbers.size() < 1 ) {
      return result;
    }

    String strActiveStarted = DateTimeFormat.convertToString( activeStarted );
    String strActiveStopped = DateTimeFormat.convertToString( activeStopped );

    StringBuffer sbSql = new StringBuffer();
    sbSql.append( "INSERT INTO client_to_number " );
    sbSql.append( "(client_id,number,active_started,active_stopped" );
    sbSql.append( ",active,date_inserted,date_updated) " );
    sbSql.append( "VALUES " );

    boolean firstTime = true;
    Iterator iter3 = listNewNumbers.iterator();
    while ( iter3.hasNext() ) {
      String number = (String) iter3.next();
      if ( StringUtils.isBlank( number ) ) {
        continue;
      }
      if ( firstTime ) {
        firstTime = false;
      } else {
        sbSql.append( "," );
      }
      sbSql.append( "(" );
      sbSql.append( clientId );
      sbSql.append( ",'" );
      sbSql.append( number );
      sbSql.append( "','" );
      sbSql.append( strActiveStarted );
      sbSql.append( "','" );
      sbSql.append( strActiveStopped );
      sbSql.append( "'," );
      sbSql.append( "1,NOW(),NOW()" );
      sbSql.append( ") " );
    }

    if ( firstTime ) {
      return result;
    }

    String sql = sbSql.toString();

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( irslt != null ) {
      result = irslt.intValue();
    }

    return result;
  }

  public int cleanBeans( Date currentDate ) {
    int result = 0;

    String strCurrentDate = DateTimeFormat.convertToString( currentDate );

    String sqlUpdate = "UPDATE client_to_number ";
    String sqlSet = "SET active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( '" + strCurrentDate + "' >= active_stopped ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( irslt != null ) {
      result = irslt.intValue();
    }

    return result;
  }

  private ClientToNumberBean populateRecord( QueryItem qi ) {
    ClientToNumberBean bean = null;
    if ( qi == null ) {
      return bean;
    }

    String stemp;
    int itemp;

    bean = new ClientToNumberBean();

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

    stemp = (String) qi.get( 1 ); // client_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        if ( itemp > 0 ) {
          bean.setClientId( itemp );
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

    stemp = (String) qi.get( 4 ); // active_started
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setActiveStarted( DateTimeFormat.convertToDate( stemp ) );
    }

    stemp = (String) qi.get( 5 ); // active_stopped
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setActiveStopped( DateTimeFormat.convertToDate( stemp ) );
    }

    stemp = (String) qi.get( 6 ); // active
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
