package com.beepcast.model.signup;

import java.util.Date;

public class NewClientUserBean {

  private int id;
  private String userFirstName;
  private String userLastName;
  private String userPhone;
  private String userEmail;
  private String companyName;

  private int companySizeId;
  private String companyAddr;
  private String companyCity;
  private String companyState;
  private String companyPostcode;
  private int companyCountryId;
  private String companyWww;

  private String featureBand;
  private boolean featureApi;

  private String loginUsr;
  private String loginPwd;
  private String activationKey;

  private boolean sentEmail;
  private boolean active;

  private Date dateInserted;
  private Date dateUpdated;

  public NewClientUserBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getUserFirstName() {
    return userFirstName;
  }

  public void setUserFirstName( String userFirstName ) {
    this.userFirstName = userFirstName;
  }

  public String getUserLastName() {
    return userLastName;
  }

  public void setUserLastName( String userLastName ) {
    this.userLastName = userLastName;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone( String userPhone ) {
    this.userPhone = userPhone;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail( String userEmail ) {
    this.userEmail = userEmail;
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

  public String getCompanyAddr() {
    return companyAddr;
  }

  public void setCompanyAddr( String companyAddr ) {
    this.companyAddr = companyAddr;
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

  public String getCompanyWww() {
    return companyWww;
  }

  public void setCompanyWww( String companyWww ) {
    this.companyWww = companyWww;
  }

  public String getFeatureBand() {
    return featureBand;
  }

  public void setFeatureBand( String featureBand ) {
    this.featureBand = featureBand;
  }

  public boolean isFeatureApi() {
    return featureApi;
  }

  public void setFeatureApi( boolean featureApi ) {
    this.featureApi = featureApi;
  }

  public String getLoginUsr() {
    return loginUsr;
  }

  public void setLoginUsr( String loginUsr ) {
    this.loginUsr = loginUsr;
  }

  public String getLoginPwd() {
    return loginPwd;
  }

  public void setLoginPwd( String loginPwd ) {
    this.loginPwd = loginPwd;
  }

  public String getActivationKey() {
    return activationKey;
  }

  public void setActivationKey( String activationKey ) {
    this.activationKey = activationKey;
  }

  public boolean isSentEmail() {
    return sentEmail;
  }

  public void setSentEmail( boolean sentEmail ) {
    this.sentEmail = sentEmail;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive( boolean active ) {
    this.active = active;
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
    retValue = "NewClientUserBean ( " + "id = " + this.id + TAB
        + "userFirstName = " + this.userFirstName + TAB + "userLastName = "
        + this.userLastName + TAB + "userPhone = " + this.userPhone + TAB
        + "userEmail = " + this.userEmail + TAB + "companyName = "
        + this.companyName + TAB + "featureBand = " + this.featureBand + TAB
        + "featureApi = " + this.featureApi + TAB + "activationKey = "
        + this.activationKey + TAB + "sentEmail = " + this.sentEmail + TAB
        + " )";
    return retValue;
  }

}
