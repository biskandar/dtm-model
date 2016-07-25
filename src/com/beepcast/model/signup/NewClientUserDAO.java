package com.beepcast.model.signup;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class NewClientUserDAO {

  static final DLogContext lctx = new SimpleContext( "NewClientUserDAO" );

  public static final String SELECT_BEAN = "SELECT "
      + "id , user_first_name , user_last_name , user_phone , user_email , "
      + "company_name , company_size_id , company_addr , company_city , "
      + "company_state , company_postcode , company_country_id , company_www , "
      + "feature_band , feature_api , "
      + "login_usr , login_pwd , activation_key , sent_email , active ";
  public static final String FROM_BEAN = "FROM new_client_user ";

  private DatabaseLibrary dbLib;

  public NewClientUserDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insertNewClientUser( NewClientUserBean bean ) {
    boolean result = false;

    int featureApi = bean.isFeatureApi() ? 1 : 0;
    int sentEmail = bean.isSentEmail() ? 1 : 0;
    int active = bean.isActive() ? 1 : 0;

    String sqlInsert = "INSERT INTO new_client_user ( ";
    sqlInsert += "user_first_name , user_last_name , user_phone , user_email , ";
    sqlInsert += "company_name , company_size_id , company_addr , company_city , ";
    sqlInsert += "company_state , company_postcode , company_country_id , company_www , ";
    sqlInsert += "feature_band , feature_api , ";
    sqlInsert += "login_usr , login_pwd , activation_key , sent_email , ";
    sqlInsert += "active , date_inserted , date_updated ) ";

    String sqlValues = "VALUES ( ";
    sqlValues += "'" + StringEscapeUtils.escapeSql( bean.getUserFirstName() )
        + "' , '" + StringEscapeUtils.escapeSql( bean.getUserLastName() )
        + "' , '" + StringEscapeUtils.escapeSql( bean.getUserPhone() )
        + "' , '" + StringEscapeUtils.escapeSql( bean.getUserEmail() )
        + "' , '" + StringEscapeUtils.escapeSql( bean.getCompanyName() )
        + "' , " + bean.getCompanySizeId() + " , '"
        + StringEscapeUtils.escapeSql( bean.getCompanyAddr() ) + "' , '"
        + StringEscapeUtils.escapeSql( bean.getCompanyCity() ) + "' , '"
        + StringEscapeUtils.escapeSql( bean.getCompanyState() ) + "' , '"
        + StringEscapeUtils.escapeSql( bean.getCompanyPostcode() ) + "' , "
        + bean.getCompanyCountryId() + " , '"
        + StringEscapeUtils.escapeSql( bean.getCompanyWww() ) + "' , '"
        + StringEscapeUtils.escapeSql( bean.getFeatureBand() ) + "' , "
        + featureApi + " , '"
        + StringEscapeUtils.escapeSql( bean.getLoginUsr() ) + "' , '"
        + StringEscapeUtils.escapeSql( bean.getLoginPwd() ) + "' , '"
        + StringEscapeUtils.escapeSql( bean.getActivationKey() ) + "' , "
        + sentEmail + " , " + active + " , NOW() , NOW() ) ";

    String sql = sqlInsert + sqlValues;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public NewClientUserBean findNewClientUserBasedOnId( int id ) {
    NewClientUserBean bean = null;

    String sqlSelect = SELECT_BEAN;
    String sqlFrom = FROM_BEAN;
    String sqlWhere = "WHERE ( id = " + id + " )  ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      bean = populateRecord( qr );
    }

    return bean;
  }

  public NewClientUserBean findNewClientUserBasedOnKey( String key ) {
    NewClientUserBean bean = null;

    String sqlSelect = SELECT_BEAN;
    String sqlFrom = FROM_BEAN;
    String sqlWhere = "WHERE ( activation_key = '"
        + StringEscapeUtils.escapeSql( key ) + "' ) ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      bean = populateRecord( qr );
    }

    return bean;
  }

  public boolean deleteNewClientUserBasedOnId( int id ) {
    boolean result = false;

    String sqlDelete = "DELETE FROM new_client_user ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";

    String sql = sqlDelete + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public boolean setActiveNewClientUserBasedOnId( int id , boolean active ) {
    boolean result = false;

    int iactive = active ? 1 : 0;

    String sqlUpdate = "UPDATE new_client_user ";
    String sqlSet = "SET ";
    sqlSet += "active = " + iactive + " , ";
    sqlSet += "date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  private NewClientUserBean populateRecord( QueryResult qr ) {
    NewClientUserBean bean = null;
    Iterator iter = qr.iterator();
    if ( iter.hasNext() ) {
      QueryItem qi = (QueryItem) iter.next();
      bean = populateRecord( qi );
    }
    return bean;
  }

  private NewClientUserBean populateRecord( QueryItem qi ) {
    NewClientUserBean bean = null;

    if ( qi == null ) {
      return bean;
    }

    String stemp = null;
    int itemp = 0;

    boolean valid = true;
    NewClientUserBean tempBean = new NewClientUserBean();

    stemp = (String) qi.get( 0 ); // id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        if ( itemp < 1 ) {
          valid = false;
        } else {
          tempBean.setId( itemp );
        }
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 1 ); // user_first_name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setUserFirstName( stemp );
    }
    stemp = (String) qi.get( 2 ); // user_last_name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setUserLastName( stemp );
    }
    stemp = (String) qi.get( 3 ); // user_phone
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setUserPhone( stemp );
    }
    stemp = (String) qi.get( 4 ); // user_email
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setUserEmail( stemp );
    }
    stemp = (String) qi.get( 5 ); // company_name
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setCompanyName( stemp );
    }
    stemp = (String) qi.get( 6 ); // company_size_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        tempBean.setCompanySizeId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 7 ); // company_addr
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setCompanyAddr( stemp );
    }
    stemp = (String) qi.get( 8 ); // company_city
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setCompanyCity( stemp );
    }
    stemp = (String) qi.get( 9 ); // company_state
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setCompanyState( stemp );
    }
    stemp = (String) qi.get( 10 ); // company_postcode
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setCompanyPostcode( stemp );
    }
    stemp = (String) qi.get( 11 ); // company_country_id
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        tempBean.setCompanyCountryId( itemp );
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 12 ); // company_www
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setCompanyWww( stemp );
    }
    stemp = (String) qi.get( 13 ); // feature_band
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setFeatureBand( stemp );
    }
    stemp = (String) qi.get( 14 ); // feature_api
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        tempBean.setFeatureApi( itemp > 0 );
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 15 ); // login_usr
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setLoginUsr( stemp );
    }
    stemp = (String) qi.get( 16 ); // login_pwd
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setLoginPwd( stemp );
    }
    stemp = (String) qi.get( 17 ); // activation_key
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      tempBean.setActivationKey( stemp );
    }
    stemp = (String) qi.get( 18 ); // sent_email
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        tempBean.setSentEmail( itemp > 0 );
      } catch ( NumberFormatException e ) {
      }
    }
    stemp = (String) qi.get( 19 ); // active
    if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
      try {
        itemp = Integer.parseInt( stemp );
        tempBean.setActive( itemp > 0 );
      } catch ( NumberFormatException e ) {
      }
    }

    if ( valid ) {
      bean = tempBean;
    }

    return bean;
  }

}
