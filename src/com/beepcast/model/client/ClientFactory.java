package com.beepcast.model.client;

import java.util.Date;

public class ClientFactory {

  public static ClientBean createClientBean( long masterClientID ,
      String webAddress , String phone , long bitFlags , boolean beepme ,
      boolean chargeLocked , double budget , int paymentType ,
      int balanceThreshold , String email , String manager ,
      String officePhone , String notificationEmailAddresses ,
      String notificationMobileNumbers , String discount , double smsCharge ,
      String country , String companyName , int companySizeId , String address ,
      String companyCity , String companyState , String companyPostcode ,
      int companyCountryId , String companyCountryName , String urlLink ,
      String state , int groupConnectionId , String groupConnectionName ,
      int bandId , String bandName , int clientLevelId ,
      String clientLevelName , boolean enableClientApi ,
      int passwordRecycleInterval , String allowIpAddresses , boolean display ,
      boolean expired , Date dateExpired ) {
    ClientBean bean = new ClientBean();

    Date now = new Date();

    bean.setMasterClientID( masterClientID );
    bean.setWebAddress( webAddress );
    bean.setPhone( phone );
    bean.setBitFlags( bitFlags );
    bean.setBeepme( beepme );
    bean.setChargeLocked( chargeLocked );
    bean.setBudget( budget );
    bean.setPaymentType( paymentType );
    bean.setBalanceThreshold( balanceThreshold );
    bean.setEmail( email );
    bean.setManager( manager );
    bean.setOfficePhone( officePhone );
    bean.setNotificationEmailAddresses( notificationEmailAddresses );
    bean.setNotificationMobileNumbers( notificationMobileNumbers );
    bean.setDiscount( discount );
    bean.setSmsCharge( smsCharge );
    bean.setCountry( country );
    bean.setCompanyName( companyName );
    bean.setCompanySizeId( companySizeId );
    bean.setAddress( address );
    bean.setCompanyCity( companyCity );
    bean.setCompanyState( companyState );
    bean.setCompanyPostcode( companyPostcode );
    bean.setCompanyCountryId( companyCountryId );
    bean.setCompanyCountryName( companyCountryName );
    bean.setUrlLink( urlLink );
    bean.setState( state );
    bean.setGroupConnectionId( groupConnectionId );
    bean.setGroupConnectionName( groupConnectionName );
    bean.setBandId( bandId );
    bean.setBandName( bandName );
    bean.setClientLevelId( clientLevelId );
    bean.setPasswordRecycleInterval( passwordRecycleInterval );
    bean.setAllowIpAddresses( allowIpAddresses );
    bean.setDisplay( display );
    bean.setExpired( expired );
    bean.setActive( true );
    bean.setDateExpired( dateExpired );

    bean.setDateInserted( now );
    bean.setDateUpdated( now );

    return bean;
  }

}
