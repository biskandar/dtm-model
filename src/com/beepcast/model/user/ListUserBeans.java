package com.beepcast.model.user;

import java.util.ArrayList;
import java.util.List;

public class ListUserBeans {

  private int clientType;
  private int masterClientId;
  private long totalRecords;
  private List userBeans;

  public ListUserBeans() {
    userBeans = new ArrayList();
  }

  public int getClientType() {
    return clientType;
  }

  public void setClientType( int clientType ) {
    this.clientType = clientType;
  }

  public int getMasterClientId() {
    return masterClientId;
  }

  public void setMasterClientId( int masterClientId ) {
    this.masterClientId = masterClientId;
  }

  public long getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords( long totalRecords ) {
    this.totalRecords = totalRecords;
  }

  public List getUserBeans() {
    return userBeans;
  }

  public void setUserBeans( List userBeans ) {
    this.userBeans = userBeans;
  }

}
