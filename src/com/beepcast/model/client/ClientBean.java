package com.beepcast.model.client;

import java.util.Date;

public class ClientBean {

  public final static long ADVANCED_EVENT_OPTION = 1L; // 0000 0000 0000 0001
  public final static long ADVANCED_CHANNEL_OPTION = 2L; // 0000 0000 0000 0010
  public final static long UPLOAD_PHONE_LIST_OPTION = 4L; // 0000 0000 0000 0100

  // primary and parent key
  private long clientID;
  private long masterClientID;

  // profile
  private String companyName;
  private int companySizeId;
  private String address;
  private String companyCity;
  private String companyState;
  private String companyPostcode;
  private int companyCountryId;
  private String companyCountryName;
  private String officePhone;
  private String webAddress;
  private String manager;
  private String phone;
  private String email;
  private String country;
  private String urlLink;

  // feature
  private long bitFlags;
  private boolean beepme;
  private double budget;
  private int paymentType;
  private int balanceThreshold;
  private String notificationEmailAddresses;
  private String notificationMobileNumbers;
  private String fromEmailAddresses;
  private boolean chargeLocked;
  private String discount;
  private double smsCharge;
  private String state;
  private int groupConnectionId;
  private String groupConnectionName;
  private int bandId;
  private String bandName;
  private int clientLevelId;
  private String clientLevelName;
  private boolean enableClientApi;
  private int passwordRecycleInterval;
  private String allowIpAddresses;

  // flag profile
  private boolean display;
  private boolean expired;
  private boolean active;

  // date profile
  private Date dateExpired;
  private Date dateInserted;
  private Date dateUpdated;

  public ClientBean() {
  }

  public long getMasterClientID() {
    return masterClientID;
  }

  public void setMasterClientID( long masterClientID ) {
    this.masterClientID = masterClientID;
  }

  public String getWebAddress() {
    return webAddress;
  }

  public void setWebAddress( String webAddress ) {
    this.webAddress = webAddress;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone( String phone ) {
    this.phone = phone;
  }

  public long getBitFlags() {
    return bitFlags;
  }

  public void setBitFlags( long bitFlags ) {
    this.bitFlags = bitFlags;
  }

  public boolean isBeepme() {
    return beepme;
  }

  public void setBeepme( boolean beepme ) {
    this.beepme = beepme;
  }

  public boolean isChargeLocked() {
    return chargeLocked;
  }

  public void setChargeLocked( boolean chargeLocked ) {
    this.chargeLocked = chargeLocked;
  }

  public double getBudget() {
    return budget;
  }

  public void setBudget( double budget ) {
    this.budget = budget;
  }

  public int getPaymentType() {
    return paymentType;
  }

  public void setPaymentType( int paymentType ) {
    this.paymentType = paymentType;
  }

  public int getBalanceThreshold() {
    return balanceThreshold;
  }

  public void setBalanceThreshold( int balanceThreshold ) {
    this.balanceThreshold = balanceThreshold;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail( String email ) {
    this.email = email;
  }

  public String getManager() {
    return manager;
  }

  public void setManager( String manager ) {
    this.manager = manager;
  }

  public String getOfficePhone() {
    return officePhone;
  }

  public void setOfficePhone( String officePhone ) {
    this.officePhone = officePhone;
  }

  public String getNotificationEmailAddresses() {
    return notificationEmailAddresses;
  }

  public void setNotificationEmailAddresses( String notificationEmailAddresses ) {
    this.notificationEmailAddresses = notificationEmailAddresses;
  }

  public String getNotificationMobileNumbers() {
    return notificationMobileNumbers;
  }

  public void setNotificationMobileNumbers( String notificationMobileNumbers ) {
    this.notificationMobileNumbers = notificationMobileNumbers;
  }

  public String getFromEmailAddresses() {
    return fromEmailAddresses;
  }

  public void setFromEmailAddresses( String fromEmailAddresses ) {
    this.fromEmailAddresses = fromEmailAddresses;
  }

  public String getDiscount() {
    return discount;
  }

  public void setDiscount( String discount ) {
    this.discount = discount;
  }

  public double getSmsCharge() {
    return smsCharge;
  }

  public void setSmsCharge( double smsCharge ) {
    this.smsCharge = smsCharge;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry( String country ) {
    this.country = country;
  }

  public long getClientID() {
    return clientID;
  }

  public void setClientID( long clientID ) {
    this.clientID = clientID;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName( String companyName ) {
    this.companyName = companyName;
  }

  public int getCompanySizeId() {
    return companySizeId;
  }

  public void setCompanySizeId( int companySizeId ) {
    this.companySizeId = companySizeId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress( String address ) {
    this.address = address;
  }

  public String getCompanyCity() {
    return companyCity;
  }

  public void setCompanyCity( String companyCity ) {
    this.companyCity = companyCity;
  }

  public String getCompanyState() {
    return companyState;
  }

  public void setCompanyState( String companyState ) {
    this.companyState = companyState;
  }

  public String getCompanyPostcode() {
    return companyPostcode;
  }

  public void setCompanyPostcode( String companyPostcode ) {
    this.companyPostcode = companyPostcode;
  }

  public int getCompanyCountryId() {
    return companyCountryId;
  }

  public void setCompanyCountryId( int companyCountryId ) {
    this.companyCountryId = companyCountryId;
  }

  public String getCompanyCountryName() {
    return companyCountryName;
  }

  public void setCompanyCountryName( String companyCountryName ) {
    this.companyCountryName = companyCountryName;
  }

  public String getUrlLink() {
    return urlLink;
  }

  public void setUrlLink( String urlLink ) {
    this.urlLink = urlLink;
  }

  public String getState() {
    return state;
  }

  public void setState( String state ) {
    this.state = state;
  }

  public int getGroupConnectionId() {
    return groupConnectionId;
  }

  public void setGroupConnectionId( int groupConnectionId ) {
    this.groupConnectionId = groupConnectionId;
  }

  public String getGroupConnectionName() {
    return groupConnectionName;
  }

  public void setGroupConnectionName( String groupConnectionName ) {
    this.groupConnectionName = groupConnectionName;
  }

  public int getBandId() {
    return bandId;
  }

  public void setBandId( int bandId ) {
    this.bandId = bandId;
  }

  public String getBandName() {
    return bandName;
  }

  public void setBandName( String bandName ) {
    this.bandName = bandName;
  }

  public int getClientLevelId() {
    return clientLevelId;
  }

  public void setClientLevelId( int clientLevelId ) {
    this.clientLevelId = clientLevelId;
  }

  public String getClientLevelName() {
    return clientLevelName;
  }

  public void setClientLevelName( String clientLevelName ) {
    this.clientLevelName = clientLevelName;
  }

  public boolean isEnableClientApi() {
    return enableClientApi;
  }

  public void setEnableClientApi( boolean enableClientApi ) {
    this.enableClientApi = enableClientApi;
  }

  public int getPasswordRecycleInterval() {
    return passwordRecycleInterval;
  }

  public void setPasswordRecycleInterval( int passwordRecycleInterval ) {
    this.passwordRecycleInterval = passwordRecycleInterval;
  }

  public String getAllowIpAddresses() {
    return allowIpAddresses;
  }

  public void setAllowIpAddresses( String allowIpAddresses ) {
    this.allowIpAddresses = allowIpAddresses;
  }

  public boolean isDisplay() {
    return display;
  }

  public void setDisplay( boolean display ) {
    this.display = display;
  }

  public boolean isExpired() {
    return expired;
  }

  public void setExpired( boolean expired ) {
    this.expired = expired;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive( boolean active ) {
    this.active = active;
  }

  public Date getDateExpired() {
    return dateExpired;
  }

  public void setDateExpired( Date dateExpired ) {
    this.dateExpired = dateExpired;
  }

  public Date getDateInserted() {
    return dateInserted;
  }

  public void setDateInserted( Date dateInserted ) {
    this.dateInserted = dateInserted;
  }

  public Date getDateUpdated() {
    return dateUpdated;
  }

  public void setDateUpdated( Date dateUpdated ) {
    this.dateUpdated = dateUpdated;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "ClientBean ( " + "clientID = " + this.clientID + TAB
        + "companyName = " + this.companyName + TAB + "state = " + this.state
        + TAB + "groupConnectionId = " + this.groupConnectionId + TAB
        + "bandId = " + this.bandId + TAB + "clientLevelId = "
        + this.clientLevelId + TAB + "enableClientApi = "
        + this.enableClientApi + TAB + "passwordRecycleInterval = "
        + this.passwordRecycleInterval + TAB + "display = " + this.display
        + TAB + "expired = " + this.expired + TAB + "active = " + this.active
        + TAB + " )";
    return retValue;
  }

}
