package com.beepcast.model.country;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class CountriesDAO {

  static final DLogContext lctx = new SimpleContext( "CountriesDAO" );

  private DatabaseLibrary dbLib;

  public CountriesDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public CountriesBean getActiveCountriesBean( String countriesId ) {
    CountriesBean bean = new CountriesBean();

    // compose sql
    String sqlSelect = sqlSelect();
    String sqlFrom = sqlFrom();
    String sqlWhere = "WHERE ( active = 1 ) ";
    if ( !StringUtils.isBlank( countriesId ) ) {
      sqlWhere += "AND ( id IN ( " + StringEscapeUtils.escapeSql( countriesId )
          + " ) ) ";
    }
    String sqlOrder = sqlOrder();

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    // execute sql
    DLog.debug( lctx , "Perform " + sql );
    QueryResult qs = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qs == null ) || ( qs.size() < 1 ) ) {
      return bean;
    }

    // populate records
    populateRecords( bean , qs );

    return bean;
  }

  public CountriesBean getInactiveCountriesBean( String countriesId ) {
    CountriesBean bean = new CountriesBean();

    // compose sql
    String sqlSelect = sqlSelect();
    String sqlFrom = sqlFrom();
    String sqlWhere = "WHERE ( active = 0 ) ";
    if ( !StringUtils.isBlank( countriesId ) ) {
      sqlWhere += "AND ( id IN ( " + StringEscapeUtils.escapeSql( countriesId )
          + " ) ) ";
    }
    String sqlOrder = sqlOrder();

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    // execute sql
    DLog.debug( lctx , "Perform " + sql );
    QueryResult qs = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qs == null ) || ( qs.size() < 1 ) ) {
      return bean;
    }

    // populate records
    populateRecords( bean , qs );

    return bean;
  }

  private String sqlSelect() {
    String sqlSelect = "SELECT id , code , name , description ";
    sqlSelect += ", phone_length , phone_length_min , phone_length_max ";
    sqlSelect += ", phone_prefix , currency_code , order_id , active ";
    return sqlSelect;
  }

  private String sqlFrom() {
    String sqlFrom = "FROM country ";
    return sqlFrom;
  }

  private String sqlOrder() {
    String sqlOrder = "ORDER BY name ASC ";
    return sqlOrder;
  }

  private boolean populateRecords( CountriesBean bean , QueryResult qs ) {
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
