package com.beepcast.model.user;

import java.util.Date;

public class UserBean {

  private int id;
  private String userID;
  private String password;
  private String[] roles;
  private String name;
  private String phone;
  private String email;
  private long clientID;
  private long proxyID;

  private boolean display;
  private boolean active;
  private Date dateInserted;
  private Date dateUpdated;
  private Date datePasswordUpdated;

  // additional
  private String companyName;

  public UserBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID( String userID ) {
    this.userID = userID;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword( String password ) {
    this.password = password;
  }

  public String[] getRoles() {
    return roles;
  }

  public void setRoles( String[] roles ) {
    this.roles = roles;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone( String phone ) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail( String email ) {
    this.email = email;
  }

  public long getClientID() {
    return clientID;
  }

  public void setClientID( long clientID ) {
    this.clientID = clientID;
  }

  public long getProxyID() {
    return proxyID;
  }

  public void setProxyID( long proxyID ) {
    this.proxyID = proxyID;
  }

  public boolean isDisplay() {
    return display;
  }

  public void setDisplay( boolean display ) {
    this.display = display;
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

  public Date getDatePasswordUpdated() {
    return datePasswordUpdated;
  }

  public void setDatePasswordUpdated( Date datePasswordUpdated ) {
    this.datePasswordUpdated = datePasswordUpdated;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName( String companyName ) {
    this.companyName = companyName;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "UserBean ( " + "id = " + this.id + TAB + "userID = "
        + this.userID + TAB + "roles = " + this.roles + TAB + "name = "
        + this.name + TAB + "clientID = " + this.clientID + TAB + "proxyID = "
        + this.proxyID + TAB + "display = " + this.display + TAB + "active = "
        + this.active + TAB + " )";
    return retValue;
  }

}
