package com.beepcast.model.clientNumber;

import java.util.ArrayList;
import java.util.List;

public class ClientToNumbersBean {

  private int clientId;
  private List beans;

  public ClientToNumbersBean() {
    beans = new ArrayList();
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId( int clientId ) {
    this.clientId = clientId;
  }

  public List getBeans() {
    return beans;
  }

  public void setBeans( List beans ) {
    this.beans = beans;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "ClientToNumbersBean ( " + "clientId = " + this.clientId + TAB
        + "beans = " + this.beans + TAB + " )";
    return retValue;
  }

}
