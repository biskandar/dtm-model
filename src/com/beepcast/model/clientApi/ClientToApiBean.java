package com.beepcast.model.clientApi;

public class ClientToApiBean {

  private int id;
  private int clientId;
  private String clientApi;
  private boolean active;

  public ClientToApiBean() {
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

  public String getClientApi() {
    return clientApi;
  }

  public void setClientApi( String clientApi ) {
    this.clientApi = clientApi;
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
    retValue = "ClientToApiBean ( " + "id = " + this.id + TAB + "clientId = "
        + this.clientId + TAB + "clientApi = " + this.clientApi + TAB
        + "active = " + this.active + TAB + " )";
    return retValue;
  }

}
