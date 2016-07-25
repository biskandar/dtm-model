package com.beepcast.model.clientSenderId;

public class ClientToSenderIdBean {

  private int id;
  private int clientId;
  private String outgoingNumber;
  private String senderId;
  private String description;
  private boolean active;

  public ClientToSenderIdBean() {
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

  public String getOutgoingNumber() {
    return outgoingNumber;
  }

  public void setOutgoingNumber( String outgoingNumber ) {
    this.outgoingNumber = outgoingNumber;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId( String senderId ) {
    this.senderId = senderId;
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

  public String toString() {
    final String TAB = " ";
    String retValue = "ClientToSenderIdBean ( " + "id = " + this.id + TAB
        + "clientId = " + this.clientId + TAB + "outgoingNumber = "
        + this.outgoingNumber + TAB + "senderId = " + this.senderId + TAB
        + "description = " + this.description + TAB + "active = " + this.active
        + TAB + " )";
    return retValue;
  }

}
