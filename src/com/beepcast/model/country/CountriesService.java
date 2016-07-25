package com.beepcast.model.country;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class CountriesService {

  static final DLogContext lctx = new SimpleContext( "CountriesService" );

  private CountriesDAO dao;

  public CountriesService() {
    dao = new CountriesDAO();
  }

  public CountriesBean getActiveCountriesBean( String countriesId ) {
    return dao.getActiveCountriesBean( countriesId );
  }

  public CountriesBean getInactiveCountriesBean( String countriesId ) {
    return dao.getInactiveCountriesBean( countriesId );
  }

  public CountriesBean getActiveCountriesBean() {
    return dao.getActiveCountriesBean( null );
  }

  public CountriesBean getInactiveCountriesBean() {
    return dao.getInactiveCountriesBean( null );
  }

}
