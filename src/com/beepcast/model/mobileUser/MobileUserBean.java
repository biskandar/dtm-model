package com.beepcast.model.mobileUser;

import java.util.Date;

public class MobileUserBean {

  private int id;
  private int clientId;
  private String phone;
  private String password;
  private String name;
  private String email;
  private String personalBeepID;
  private String clientBeepID;
  private Date birthDate;
  private String gender;
  private String maritalStatus;
  private String host;
  private String lastName;
  private String lastCode;
  private String companyName;
  private String ic;
  private String monthlyIncome;
  private String industry;
  private String occupation;
  private String education;
  private String mobileModel;
  private String mobileBrand;
  private String mobileOperator;
  private String mobileCcnc;
  private int numChildren;
  private String country;
  private String dwelling;
  private String officeZip;
  private String officeStreet;
  private String officeUnit;
  private String officeBlk;
  private String homeZip;
  private String homeStreet;
  private String homeUnit;
  private String homeBlk;
  private String nationality;
  private String salutation;
  private String clientDBKey1;
  private String clientDBKey2;
  private Date dateInserted;
  private Date dateUpdated;

  public MobileUserBean() {
    dateInserted = new Date();
    dateUpdated = new Date();
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId( int clientId ) {
    this.clientId = clientId;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone( String phone ) {
    this.phone = phone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword( String password ) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail( String email ) {
    this.email = email;
  }

  public String getPersonalBeepID() {
    return personalBeepID;
  }

  public void setPersonalBeepID( String personalBeepID ) {
    this.personalBeepID = personalBeepID;
  }

  public String getClientBeepID() {
    return clientBeepID;
  }

  public void setClientBeepID( String clientBeepID ) {
    this.clientBeepID = clientBeepID;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate( Date birthDate ) {
    this.birthDate = birthDate;
  }

  public String getGender() {
    return gender;
  }

  public void setGender( String gender ) {
    this.gender = gender;
  }

  public String getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus( String maritalStatus ) {
    this.maritalStatus = maritalStatus;
  }

  public String getHost() {
    return host;
  }

  public void setHost( String host ) {
    this.host = host;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName( String lastName ) {
    this.lastName = lastName;
  }

  public String getLastCode() {
    return lastCode;
  }

  public void setLastCode( String lastCode ) {
    this.lastCode = lastCode;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName( String companyName ) {
    this.companyName = companyName;
  }

  public String getIc() {
    return ic;
  }

  public void setIc( String ic ) {
    this.ic = ic;
  }

  public String getMonthlyIncome() {
    return monthlyIncome;
  }

  public void setMonthlyIncome( String monthlyIncome ) {
    this.monthlyIncome = monthlyIncome;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry( String industry ) {
    this.industry = industry;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation( String occupation ) {
    this.occupation = occupation;
  }

  public String getEducation() {
    return education;
  }

  public void setEducation( String education ) {
    this.education = education;
  }

  public String getMobileModel() {
    return mobileModel;
  }

  public void setMobileModel( String mobileModel ) {
    this.mobileModel = mobileModel;
  }

  public String getMobileBrand() {
    return mobileBrand;
  }

  public void setMobileBrand( String mobileBrand ) {
    this.mobileBrand = mobileBrand;
  }

  public String getMobileOperator() {
    return mobileOperator;
  }

  public void setMobileOperator( String mobileOperator ) {
    this.mobileOperator = mobileOperator;
  }

  public String getMobileCcnc() {
    return mobileCcnc;
  }

  public void setMobileCcnc( String mobileCcnc ) {
    this.mobileCcnc = mobileCcnc;
  }

  public int getNumChildren() {
    return numChildren;
  }

  public void setNumChildren( int numChildren ) {
    this.numChildren = numChildren;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry( String country ) {
    this.country = country;
  }

  public String getDwelling() {
    return dwelling;
  }

  public void setDwelling( String dwelling ) {
    this.dwelling = dwelling;
  }

  public String getOfficeZip() {
    return officeZip;
  }

  public void setOfficeZip( String officeZip ) {
    this.officeZip = officeZip;
  }

  public String getOfficeStreet() {
    return officeStreet;
  }

  public void setOfficeStreet( String officeStreet ) {
    this.officeStreet = officeStreet;
  }

  public String getOfficeUnit() {
    return officeUnit;
  }

  public void setOfficeUnit( String officeUnit ) {
    this.officeUnit = officeUnit;
  }

  public String getOfficeBlk() {
    return officeBlk;
  }

  public void setOfficeBlk( String officeBlk ) {
    this.officeBlk = officeBlk;
  }

  public String getHomeZip() {
    return homeZip;
  }

  public void setHomeZip( String homeZip ) {
    this.homeZip = homeZip;
  }

  public String getHomeStreet() {
    return homeStreet;
  }

  public void setHomeStreet( String homeStreet ) {
    this.homeStreet = homeStreet;
  }

  public String getHomeUnit() {
    return homeUnit;
  }

  public void setHomeUnit( String homeUnit ) {
    this.homeUnit = homeUnit;
  }

  public String getHomeBlk() {
    return homeBlk;
  }

  public void setHomeBlk( String homeBlk ) {
    this.homeBlk = homeBlk;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality( String nationality ) {
    this.nationality = nationality;
  }

  public String getSalutation() {
    return salutation;
  }

  public void setSalutation( String salutation ) {
    this.salutation = salutation;
  }

  public String getClientDBKey1() {
    return clientDBKey1;
  }

  public void setClientDBKey1( String clientDBKey1 ) {
    this.clientDBKey1 = clientDBKey1;
  }

  public String getClientDBKey2() {
    return clientDBKey2;
  }

  public void setClientDBKey2( String clientDBKey2 ) {
    this.clientDBKey2 = clientDBKey2;
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
    retValue = "MobileUserBean ( " + "id = " + this.id + TAB + "clientId = "
        + this.clientId + TAB + "phone = " + this.phone + TAB + "name = "
        + this.name + TAB + "email = " + this.email + TAB + "lastName = "
        + this.lastName + TAB + "ic = " + this.ic + TAB + " )";
    return retValue;
  }

}
