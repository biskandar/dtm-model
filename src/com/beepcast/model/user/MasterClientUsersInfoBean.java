package com.beepcast.model.user;

public class MasterClientUsersInfoBean {

  private int masterClientId;
  private int clientId;
  private String companyName;
  private int userId;
  private String name;
  private String email;
  private String phone;

  public MasterClientUsersInfoBean() {

  }

  public int getMasterClientId() {
    return masterClientId;
  }

  public void setMasterClientId( int masterClientId ) {
    this.masterClientId = masterClientId;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId( int clientId ) {
    this.clientId = clientId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName( String companyName ) {
    this.companyName = companyName;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId( int userId ) {
    this.userId = userId;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone( String phone ) {
    this.phone = phone;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "MasterClientUsersInfoBean ( " + "masterClientId = "
        + this.masterClientId + TAB + "clientId = " + this.clientId + TAB
        + "companyName = " + this.companyName + TAB + "userId = " + this.userId
        + TAB + "name = " + this.name + TAB + "email = " + this.email + TAB
        + "phone = " + this.phone + TAB + " )";
    return retValue;
  }

}
