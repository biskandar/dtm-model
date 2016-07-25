package com.beepcast.model.client;

public class MasterClientsInfoBean {

  private int masterClientId;
  private int clientId;
  private String companyName;
  private String manager;
  private String email;
  private String phone;

  public MasterClientsInfoBean() {
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

  public String getManager() {
    return manager;
  }

  public void setManager( String manager ) {
    this.manager = manager;
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
    retValue = "MasterClientsInfoBean ( " + "masterClientId = "
        + this.masterClientId + TAB + "clientId = " + this.clientId + TAB
        + "companyName = " + this.companyName + TAB + "manager = "
        + this.manager + TAB + "email = " + this.email + TAB + "phone = "
        + this.phone + TAB + " )";
    return retValue;
  }

}
