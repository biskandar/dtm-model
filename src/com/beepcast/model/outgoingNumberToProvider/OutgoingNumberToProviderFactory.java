package com.beepcast.model.outgoingNumberToProvider;

public class OutgoingNumberToProviderFactory {

  public static OutgoingNumberToProviderBean createOutgoingNumberToProviderBean(
      String outgoingNumber , int level , String countryCode ,
      String prefixNumber , String telcoCode , String providerId ,
      int groupConnectionId , String masked , String description ,
      int priority , boolean suspend ) {
    OutgoingNumberToProviderBean bean = new OutgoingNumberToProviderBean();
    bean.setOutgoingNumber( outgoingNumber );
    bean.setLevel( level );
    bean.setCountryCode( countryCode );
    bean.setPrefixNumber( prefixNumber );
    bean.setTelcoCode( telcoCode );
    bean.setProviderId( providerId );
    bean.setGroupConnectionId( groupConnectionId );
    bean.setMasked( masked );
    bean.setDescription( description );
    bean.setPriority( priority );
    bean.setActive( true );
    bean.setSuspend( suspend );
    return bean;
  }

}
