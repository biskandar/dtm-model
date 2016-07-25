package com.beepcast.model.country;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CountriesBean {

  private List countries;

  public CountriesBean() {
    countries = new ArrayList();
  }

  public boolean addCountryBean( CountryBean countryBean ) {
    boolean result = false;
    if ( countryBean == null ) {
      return result;
    }
    countries.add( countryBean );
    result = true;
    return result;
  }

  public Iterator iterCountriesBean() {
    return countries.iterator();
  }

}
