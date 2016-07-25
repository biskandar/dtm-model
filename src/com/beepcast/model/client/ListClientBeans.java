package com.beepcast.model.client;

import java.util.ArrayList;
import java.util.List;

public class ListClientBeans {

  private int masterClientId;
  private long totalRecords;
  private List clientBeans;

  public ListClientBeans() {
    clientBeans = new ArrayList();
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

  public List getClientBeans() {
    return clientBeans;
  }

  public void setClientBeans( List clientBeans ) {
    this.clientBeans = clientBeans;
  }

}
