package com.beepcast.model.client;

import java.util.Date;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientService {

  // ///////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ///////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "ClientService" );

  // ///////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ///////////////////////////////////////////////////////////////////////////

  private ClientDAO dao;

  // ///////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ///////////////////////////////////////////////////////////////////////////

  public ClientService() {
    dao = new ClientDAO();
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ///////////////////////////////////////////////////////////////////////////

  public long insert( ClientBean clientBean ) {
    return dao.insert( cleanBean( clientBean ) );
  }

  public boolean update( ClientBean clientBean ) {
    return dao.update( cleanBean( clientBean ) );
  }

  public boolean updateState( long clientID , String state ) {
    return dao.updateState( clientID , state );
  }

  public boolean updateExpired( long clientID , boolean expired ,
      Date dateExpired ) {
    return dao.updateExpired( clientID , expired , dateExpired );
  }

  public boolean setHidden( long clientID ) {
    return dao.updateDisplay( clientID , false );
  }

  public boolean setShown( long clientID ) {
    return dao.updateDisplay( clientID , true );
  }

  public boolean delete( long clientID ) {
    return dao.updateActive( clientID , false );
  }

  public ClientBean select( long clientID ) {
    return cleanBean( dao.select( clientID ) );
  }

  public ClientBean selectBasedOnCompanyName( String companyName ) {
    return cleanBean( dao.selectBasedOnCompanyName( companyName ) );
  }

  // ///////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ///////////////////////////////////////////////////////////////////////////

  private ClientBean cleanBean( ClientBean clientBean ) {
    if ( clientBean == null ) {
      return clientBean;
    }

    String webAddress = clientBean.getWebAddress();
    String phone = clientBean.getPhone();
    String email = clientBean.getEmail();
    String manager = clientBean.getManager();
    String officePhone = clientBean.getOfficePhone();
    String notificationEmailAddresses = clientBean
        .getNotificationEmailAddresses();
    String notificationMobileNumbers = clientBean
        .getNotificationMobileNumbers();
    String fromEmailAddresses = clientBean.getFromEmailAddresses();
    String discount = clientBean.getDiscount();
    String country = clientBean.getCountry();
    String companyName = clientBean.getCompanyName();
    String address = clientBean.getAddress();
    String companyCity = clientBean.getCompanyCity();
    String companyState = clientBean.getCompanyState();
    String companyPostcode = clientBean.getCompanyPostcode();
    String companyCountryName = clientBean.getCompanyCountryName();
    String urlLink = clientBean.getUrlLink();
    String state = clientBean.getState();
    String groupConnectionName = clientBean.getGroupConnectionName();
    String bandName = clientBean.getBandName();
    String clientLevelName = clientBean.getClientLevelName();
    String allowIpAddresses = clientBean.getAllowIpAddresses();

    clientBean.setWebAddress( cleanString( webAddress ) );
    clientBean.setPhone( cleanString( phone ) );
    clientBean.setEmail( cleanString( email ) );
    clientBean.setManager( cleanString( manager ) );
    clientBean.setOfficePhone( cleanString( officePhone ) );
    clientBean
        .setNotificationEmailAddresses( cleanString( notificationEmailAddresses ) );
    clientBean
        .setNotificationMobileNumbers( cleanString( notificationMobileNumbers ) );
    clientBean.setFromEmailAddresses( cleanString( fromEmailAddresses ) );
    clientBean.setDiscount( cleanString( discount ) );
    clientBean.setCountry( cleanString( country ) );
    clientBean.setCompanyName( cleanString( companyName ) );
    clientBean.setAddress( cleanString( address ) );
    clientBean.setCompanyCity( cleanString( companyCity ) );
    clientBean.setCompanyState( cleanString( companyState ) );
    clientBean.setCompanyPostcode( cleanString( companyPostcode ) );
    clientBean.setCompanyCountryName( cleanString( companyCountryName ) );
    clientBean.setUrlLink( cleanString( urlLink ) );
    clientBean.setState( cleanString( state ) );
    clientBean.setGroupConnectionName( cleanString( groupConnectionName ) );
    clientBean.setBandName( cleanString( bandName ) );
    clientBean.setClientLevelName( cleanString( clientLevelName ) );
    clientBean.setAllowIpAddresses( cleanString( allowIpAddresses ) );

    return clientBean;
  }

  private String cleanString( String str ) {
    return ( str == null ) ? "" : str.trim();
  }

}
