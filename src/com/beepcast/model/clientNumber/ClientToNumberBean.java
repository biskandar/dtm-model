package com.beepcast.model.clientNumber;

import java.util.Date;

import com.beepcast.dbmanager.util.DateTimeFormat;

public class ClientToNumberBean {

  private int id;
  private int clientId;
  private String number;
  private String description;
  private Date activeStarted;
  private Date activeStopped;
  private boolean active;

  public ClientToNumberBean() {
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

  public String getNumber() {
    return number;
  }

  public void setNumber( String number ) {
    this.number = number;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public Date getActiveStarted() {
    return activeStarted;
  }

  public void setActiveStarted( Date activeStarted ) {
    this.activeStarted = activeStarted;
  }

  public Date getActiveStopped() {
    return activeStopped;
  }

  public void setActiveStopped( Date activeStopped ) {
    this.activeStopped = activeStopped;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive( boolean active ) {
    this.active = active;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "ClientToNumberBean ( " + "id = " + this.id + TAB
        + "clientId = " + this.clientId + TAB + "number = " + this.number + TAB
        + "description = " + this.description + TAB + "activeStarted = "
        + DateTimeFormat.convertToString( this.activeStarted ) + TAB
        + "activeStopped = "
        + DateTimeFormat.convertToString( this.activeStopped ) + TAB
        + "active = " + this.active + TAB + " )";
    return retValue;
  }

}
