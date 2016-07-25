package com.beepcast.model.client;

import java.util.Date;

public class ClientCreditUnitBean {

  public static final String METHOD_ = "";
  public static final String STATUS_ = "";

  private int id;
  private String transactionId;
  private int clientId;
  private String method;
  private double unit;
  private String status;
  private String description;
  private boolean active;
  private Date dateInserted;
  private Date dateUpdated;

  public ClientCreditUnitBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId( String transactionId ) {
    this.transactionId = transactionId;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId( int clientId ) {
    this.clientId = clientId;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod( String method ) {
    this.method = method;
  }

  public double getUnit() {
    return unit;
  }

  public void setUnit( double unit ) {
    this.unit = unit;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus( String status ) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
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
    retValue = "ClientCreditUnitBean ( " + "id = " + this.id + TAB
        + "transactionId = " + this.transactionId + TAB + "clientId = "
        + this.clientId + TAB + "method = " + this.method + TAB + "unit = "
        + this.unit + TAB + "status = " + this.status + TAB + "description = "
        + this.description + TAB + "active = " + this.active + TAB
        + "dateInserted = " + this.dateInserted + TAB + "dateUpdated = "
        + this.dateUpdated + TAB + " )";
    return retValue;
  }

}
