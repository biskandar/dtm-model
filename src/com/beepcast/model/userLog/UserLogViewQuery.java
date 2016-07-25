package com.beepcast.model.userLog;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.model.client.ClientType;
import com.beepcast.model.util.DateTimeFormat;

public class UserLogViewQuery {

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT ul.id , c.company_name , u.user_id ";
    sqlSelect += ", ul.`action` , ul.date_inserted ";
    String sqlFrom = "FROM `user_log` ul ";
    sqlFrom += "LEFT OUTER JOIN `user` u ON u.id = ul.user_id ";
    sqlFrom += "LEFT OUTER JOIN `client` c ON c.client_id = u.client_id ";
    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  public static String sqlWhere( int clientType , int masterClientId ,
      String findUserId , String findActionText , Date dateStarted ,
      Date dateFinished ) {
    String sqlWhere = "WHERE ( ul.active IN ( 0 , 1 ) ) ";
    if ( clientType != ClientType.SUPER ) {
      if ( clientType == ClientType.MASTER ) {
        sqlWhere += "AND ( c.master_client_id = " + masterClientId + " ) ";
      } else {
        sqlWhere += "AND ( c.client_id = " + masterClientId + " ) ";
      }
    }
    if ( ( findUserId != null ) && ( !findUserId.equals( "" ) ) ) {
      StringBuffer sb = null;
      String[] findUserIdArr = findUserId.split( "," );
      for ( int idx = 0 ; idx < findUserIdArr.length ; idx++ ) {
        String findUserIdItem = findUserIdArr[idx];
        if ( StringUtils.isBlank( findUserIdItem ) ) {
          continue;
        }
        findUserIdItem = findUserIdItem.trim();
        if ( sb == null ) {
          sb = new StringBuffer();
        } else {
          sb.append( "OR " );
        }
        sb.append( "( u.user_id LIKE '%"
            + StringEscapeUtils.escapeSql( findUserIdItem ) + "%' ) " );
      }
      if ( sb != null ) {
        sqlWhere += "AND ( " + sb.toString() + " ) ";
      }
    }
    if ( ( findActionText != null ) && ( !findActionText.equals( "" ) ) ) {
      StringBuffer sb = null;
      String[] findActionTextArr = findActionText.split( "," );
      for ( int idx = 0 ; idx < findActionTextArr.length ; idx++ ) {
        String findActionTextItem = findActionTextArr[idx];
        if ( StringUtils.isBlank( findActionTextItem ) ) {
          continue;
        }
        findActionTextItem = findActionTextItem.trim();
        if ( sb == null ) {
          sb = new StringBuffer();
        } else {
          sb.append( "OR " );
        }
        sb.append( "( ul.action LIKE '%"
            + StringEscapeUtils.escapeSql( findActionTextItem ) + "%' ) " );
      }
      if ( sb != null ) {
        sqlWhere += "AND ( " + sb.toString() + " ) ";
      }
    }
    if ( dateStarted != null ) {
      sqlWhere += "AND ( ul.date_inserted >= '"
          + DateTimeFormat.convertToString( dateStarted ) + "' ) ";
    }
    if ( dateFinished != null ) {
      sqlWhere += "AND ( ul.date_inserted <= '"
          + DateTimeFormat.convertToString( dateFinished ) + "' ) ";
    }
    return sqlWhere;
  }

  public static String sql( int clientType , int masterClientId ,
      String findUserId , String findActionText , Date dateStarted ,
      Date dateFinished ) {
    String sql = sqlSelectFrom();
    sql += sqlWhere( clientType , masterClientId , findUserId , findActionText ,
        dateStarted , dateFinished );
    sql += "ORDER BY ul.id DESC ";
    return sql;
  }

  public static UserLogViewBean populateRecord( QueryItem qi ) {
    UserLogViewBean bean = null;
    if ( qi == null ) {
      return bean;
    }

    bean = new UserLogViewBean();

    String stemp = null;

    stemp = (String) qi.get( 0 ); // ul.id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        bean.setUserLogId( Integer.parseInt( stemp ) );
      } catch ( NumberFormatException e ) {
      }
    }

    stemp = (String) qi.get( 1 ); // c.company_name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setCompanyName( stemp );
    }

    stemp = (String) qi.get( 2 ); // u.user_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setUserId( stemp );
    }

    stemp = (String) qi.get( 3 ); // ul.`action`
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setActionText( stemp );
    }

    stemp = (String) qi.get( 4 ); // ul.date_inserted
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      bean.setActionDate( DateTimeFormat.convertToDate( stemp ) );
    }

    return bean;
  }

}
