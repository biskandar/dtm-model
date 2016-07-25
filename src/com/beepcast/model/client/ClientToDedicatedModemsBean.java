package com.beepcast.model.client;

import java.util.ArrayList;
import java.util.List;

public class ClientToDedicatedModemsBean {

  private int clientId;
  private List modemNumbers;

  public ClientToDedicatedModemsBean() {
    modemNumbers = new ArrayList();
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId( int clientId ) {
    this.clientId = clientId;
  }

  public List getModemNumbers() {
    return modemNumbers;
  }

  public void setModemNumbers( List modemNumbers ) {
    this.modemNumbers = modemNumbers;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "ClientToDedicatedModemsBean ( " + "clientId = " + this.clientId
        + TAB + "modemNumbers = " + this.modemNumbers + TAB + " )";
    return retValue;
  }

}
