package com.beepcast.model.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.beepcast.model.country.CountriesBean;
import com.beepcast.model.country.CountriesService;
import com.beepcast.model.country.CountryBean;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToCountriesService {

  static final DLogContext lctx = new SimpleContext( "ClientToCountriesService" );

  private ClientToCountriesDAO dao;

  public ClientToCountriesService() {
    dao = new ClientToCountriesDAO();
  }

  public ClientToCountriesBean getClientToCountriesBean( int clientId ) {
    ClientToCountriesBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to list countries "
          + ", found zero client id" );
      return bean;
    }
    bean = dao.getClientToCountriesBean( clientId );
    return bean;
  }

  public boolean updateClientCountriesId( int clientId , String countriesId ,
      String delimiter ) {
    boolean result = false;
    if ( StringUtils.isBlank( delimiter ) ) {
      delimiter = ",";
    }
    if ( StringUtils.isBlank( countriesId ) ) {
      return result;
    }
    String[] arrCountryId = StringUtils.split( countriesId , delimiter );
    if ( arrCountryId == null ) {
      DLog.warning( lctx , "Failed to update client countries id "
          + ", found null arr countries id" );
      return result;
    }
    List listCoutriesId = new ArrayList();
    for ( int idxCountryId = 0 ; idxCountryId < arrCountryId.length ; idxCountryId++ ) {
      try {
        int countryId = Integer.parseInt( arrCountryId[idxCountryId] );
        if ( countryId > 0 ) {
          listCoutriesId.add( Integer.toString( countryId ) );
        }
      } catch ( NumberFormatException e ) {
      }
    }
    String strCountriesId = StringUtils.join( listCoutriesId , "," );
    if ( StringUtils.isBlank( strCountriesId ) ) {
      DLog.warning( lctx , "Failed to update client countries id "
          + ", found null valid countries id" );
      return result;
    }
    CountriesService countriesService = new CountriesService();
    CountriesBean countriesBean = countriesService
        .getActiveCountriesBean( strCountriesId );
    if ( countriesBean == null ) {
      DLog.warning( lctx , "Failed to update client countries id "
          + ", found null countries bean" );
      return result;
    }
    int totalRecords = dao.deleteAllActiveMap( clientId );
    if ( totalRecords > 0 ) {
      DLog.debug( lctx , "Delete all previous active map "
          + ", total effected = " + totalRecords + " record(s)" );
    }
    int countryId;
    totalRecords = 0;
    Iterator iterCountriesBean = countriesBean.iterCountriesBean();
    while ( iterCountriesBean.hasNext() ) {
      CountryBean countryBean = (CountryBean) iterCountriesBean.next();
      if ( countryBean == null ) {
        continue;
      }
      countryId = countryBean.getId();
      if ( countryId < 1 ) {
        continue;
      }
      if ( !dao.insertActiveMap( clientId , countryId ) ) {
        DLog.warning( lctx , "Failed to insert into "
            + "client_to_country table , with clientId = " + clientId
            + " and countryId = " + countryId );
        continue;
      }
      totalRecords = totalRecords + 1;
    }
    DLog.debug( lctx , "Successfully update map client to country "
        + ", total effected = " + totalRecords + " record(s)" );
    if ( totalRecords > 0 ) {
      result = true;
    }
    return result;
  }

}
