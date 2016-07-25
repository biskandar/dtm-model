package com.beepcast.model.client;

import java.util.Iterator;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.model.country.CountryBean;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToCountriesDAO {

  static final DLogContext lctx = new SimpleContext( "ClientToCountriesDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public ClientToCountriesDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public ClientToCountriesBean getClientToCountriesBean( int clientId ) {
    ClientToCountriesBean bean = new ClientToCountriesBean();
    bean.setClientId( clientId );

    // compose sql
    String sqlSelect = "SELECT co.id , co.code , co.name , co.description ";
    sqlSelect += ", co.phone_length , co.phone_length_min , co.phone_length_max ";
    sqlSelect += ", co.phone_prefix , co.currency_code , co.order_id , co.active ";
    String sqlFrom = "FROM client_to_country cc ";
    sqlFrom += "INNER JOIN country co ON co.id = cc.country_id ";
    String sqlWhere = "WHERE ( cc.active = 1 ) AND ( co.active = 1 ) ";
    sqlWhere += "AND ( cc.client_id = " + clientId + " ) ";
    String sqlOrder = "ORDER BY co.name ASC ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    // execute sql
    // DLog.debug( lctx , "Perform " + sql );
    QueryResult qs = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qs == null ) || ( qs.size() < 1 ) ) {
      return bean;
    }

    // populate records
    populateRecords( bean , qs );

    return bean;
  }

  public int deleteAllActiveMap( int clientId ) {
    int totalRecords = 0;
    if ( clientId < 1 ) {
      return totalRecords;
    }

    // compose sql
    String sqlUpdate = "UPDATE client_to_country ";
    String sqlSet = "SET active = 0 , date_updated = NOW() ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    // execute sql
    if ( opropsApp.getBoolean( "Model.DebugAllSqlDelete" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( irslt != null ) {
      totalRecords = irslt.intValue();
    }

    return totalRecords;
  }

  public boolean insertActiveMap( int clientId , int countryId ) {
    boolean result = false;

    if ( clientId < 1 ) {
      return result;
    }

    if ( countryId < 1 ) {
      return result;
    }

    // compose sql
    String sqlInsert = "INSERT INTO client_to_country ";
    sqlInsert += "( client_id , country_id , active , date_inserted , date_updated ) ";
    String sqlValues = "VALUES ";
    sqlValues += "( " + clientId + " , " + countryId
        + " , 1 , NOW() , NOW() ) ";

    String sql = sqlInsert + sqlValues;

    // execute sql
    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  private boolean populateRecords( ClientToCountriesBean bean , QueryResult qs ) {
    boolean result = false;

    String stemp;
    int itemp;

    Iterator iter = qs.iterator();
    while ( iter.hasNext() ) {
      QueryItem qi = (QueryItem) iter.next();
      if ( qi == null ) {
        continue;
      }

      CountryBean countryBean = new CountryBean();

      stemp = (String) qi.get( 0 ); // co.id
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        try {
          itemp = Integer.parseInt( stemp );
          countryBean.setId( itemp );
        } catch ( NumberFormatException e ) {

        }
      }

      stemp = (String) qi.get( 1 ); // co.code
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        countryBean.setCode( stemp );
      }

      stemp = (String) qi.get( 2 ); // co.name
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        countryBean.setName( stemp );
      }

      stemp = (String) qi.get( 3 ); // co.description
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        countryBean.setDescription( stemp );
      }

      stemp = (String) qi.get( 4 ); // co.phone_length
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        try {
          itemp = Integer.parseInt( stemp );
          countryBean.setPhoneLength( itemp );
        } catch ( NumberFormatException e ) {

        }
      }

      stemp = (String) qi.get( 5 ); // co.phone_length_min
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        try {
          itemp = Integer.parseInt( stemp );
          countryBean.setPhoneLengthMin( itemp );
        } catch ( NumberFormatException e ) {

        }
      }

      stemp = (String) qi.get( 6 ); // co.phone_length_max
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        try {
          itemp = Integer.parseInt( stemp );
          countryBean.setPhoneLengthMax( itemp );
        } catch ( NumberFormatException e ) {

        }
      }

      stemp = (String) qi.get( 7 ); // co.phone_prefix
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        countryBean.setPhonePrefix( stemp );
      }

      stemp = (String) qi.get( 8 ); // co.currency_code
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        countryBean.setCurrencyCode( stemp );
      }

      stemp = (String) qi.get( 9 ); // co.order_id
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        try {
          itemp = Integer.parseInt( stemp );
          countryBean.setOrderId( itemp );
        } catch ( NumberFormatException e ) {
        }
      }

      stemp = (String) qi.get( 10 ); // co.active
      if ( ( stemp != null ) && ( !stemp.equals( "" ) ) ) {
        try {
          itemp = Integer.parseInt( stemp );
          countryBean.setActive( itemp > 0 );
        } catch ( NumberFormatException e ) {

        }
      }

      if ( countryBean.getId() > 0 ) {
        bean.addCountryBean( countryBean );
      }

    } // end iterator

    return result;
  }

}
