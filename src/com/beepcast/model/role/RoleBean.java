package com.beepcast.model.role;

import java.util.Date;

public class RoleBean {

  private int id;
  private String role;
  private String childRoles;
  private String menus;
  private boolean active;
  private Date dateInserted;
  private Date dateUpdated;

  public RoleBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole( String role ) {
    this.role = role;
  }

  public String getChildRoles() {
    return childRoles;
  }

  public void setChildRoles( String childRoles ) {
    this.childRoles = childRoles;
  }

  public String getMenus() {
    return menus;
  }

  public void setMenus( String menus ) {
    this.menus = menus;
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
    retValue = "RoleBean ( " + "id = " + this.id + TAB + "role = " + this.role
        + TAB + "childRoles = " + this.childRoles + TAB + "menus = "
        + this.menus + TAB + " )";
    return retValue;
  }

}
