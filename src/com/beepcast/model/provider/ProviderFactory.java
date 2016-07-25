package com.beepcast.model.provider;

public class ProviderFactory {

  public static ProviderBean createProviderBean( int masterId ,
      String providerId , String providerName , String direction , String type ,
      String shortCode , String countryCode , String accessUrl ,
      String accessUsername , String accessPassword , String listenerUrl ,
      double inCreditCost , double ouCreditCost , String description ) {
    ProviderBean bean = new ProviderBean();
    bean.setMasterId( masterId );
    bean.setProviderId( providerId );
    bean.setProviderName( providerName );
    bean.setDirection( direction );
    bean.setType( type );
    bean.setShortCode( shortCode );
    bean.setCountryCode( countryCode );
    bean.setAccessUrl( accessUrl );
    bean.setAccessUsername( accessUsername );
    bean.setAccessPassword( accessPassword );
    bean.setListenerUrl( listenerUrl );
    bean.setInCreditCost( inCreditCost );
    bean.setOuCreditCost( ouCreditCost );
    bean.setDescription( description );
    return bean;
  }

}
