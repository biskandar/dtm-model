package com.beepcast.model.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.beepcast.model.country.CountryBean;

public class ClientToCountriesBean {

  private int clientId;
  private Map countries;

  public ClientToCountriesBean() {
    countries = new HashMap();
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId( int clientId ) {
    this.clientId = clientId;
  }

  public boolean addCountryBean( CountryBean countryBean ) {
    boolean result = false;
    if ( countryBean == null ) {
      return result;
    }
    countries.put( new Integer( countryBean.getId() ) , countryBean );
    result = true;
    return result;
  }

  public int getTotalCountries() {
    return countries.size();
  }

  public Iterator iterCountriesId() {
    Iterator iter = null;
    Set keySet = countries.keySet();
    if ( keySet == null ) {
      return iter;
    }
    iter = keySet.iterator();
    return iter;
  }

  public CountryBean getCountryBean( int countryId ) {
    return getCountryBean( new Integer( countryId ) );
  }

  public CountryBean getCountryBean( Integer countryId ) {
    CountryBean countryBean = null;
    if ( countryId == null ) {
      return countryBean;
    }
    if ( countryId.intValue() < 1 ) {
      return countryBean;
    }
    countryBean = (CountryBean) countries.get( countryId );
    return countryBean;
  }

  public String getCountriesId( String delimiter ) {
    String result = "";
    if ( StringUtils.isBlank( delimiter ) ) {
      delimiter = ",";
    }
    Set keySet = countries.keySet();
    if ( keySet == null ) {
      return result;
    }
    List listCountriesId = new ArrayList( keySet );
    if ( listCountriesId == null ) {
      return result;
    }
    result = StringUtils.join( listCountriesId , delimiter );
    return result;
  }

}
