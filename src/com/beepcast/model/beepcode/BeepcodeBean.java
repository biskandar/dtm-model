package com.beepcast.model.beepcode;

import java.io.Serializable;
import java.util.Date;

public class BeepcodeBean implements Serializable {

  private String code;
  private int codeLength;
  private long clientID;
  private long eventID;
  private long catagoryID;
  private long mediumID;
  private String description;
  private Date lastHitDate;
  private boolean active;
  private boolean reserved;
  private long locationID;

  public BeepcodeBean() {
    lastHitDate = new Date();
    lastHitDate.setTime( 1 );
  }

  public void setCode( String code ) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCodeLength( int codeLength ) {
    this.codeLength = codeLength;
  }

  public int getCodeLength() {
    return codeLength;
  }

  public void setClientID( long clientID ) {
    this.clientID = clientID;
  }

  public long getClientID() {
    return clientID;
  }

  public void setEventID( long eventID ) {
    this.eventID = eventID;
  }

  public long getEventID() {
    return eventID;
  }

  public void setCatagoryID( long catagoryID ) {
    this.catagoryID = catagoryID;
  }

  public long getCatagoryID() {
    return catagoryID;
  }

  public void setMediumID( long mediumID ) {
    this.mediumID = mediumID;
  }

  public long getMediumID() {
    return mediumID;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setLastHitDate( Date lastHitDate ) {
    this.lastHitDate = lastHitDate;
  }

  public Date getLastHitDate() {
    return lastHitDate;
  }

  public void setActive( boolean active ) {
    this.active = active;
  }

  public boolean getActive() {
    return active;
  }

  public void setReserved( boolean reserved ) {
    this.reserved = reserved;
  }

  public boolean getReserved() {
    return reserved;
  }

  public void setLocationID( long locationID ) {
    this.locationID = locationID;
  }

  public long getLocationID() {
    return locationID;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "BeepcodeBean ( " + "code = " + this.code + TAB
        + "codeLength = " + this.codeLength + TAB + "clientID = "
        + this.clientID + TAB + "eventID = " + this.eventID + TAB
        + "catagoryID = " + this.catagoryID + TAB + "mediumID = "
        + this.mediumID + TAB + "description = " + this.description + TAB
        + "lastHitDate = " + this.lastHitDate + TAB + "active = " + this.active
        + TAB + "reserved = " + this.reserved + TAB + "locationID = "
        + this.locationID + TAB + " )";
    return retValue;
  }

}
