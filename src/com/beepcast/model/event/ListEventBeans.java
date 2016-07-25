package com.beepcast.model.event;

import java.util.ArrayList;
import java.util.List;

public class ListEventBeans {

  private int clientId;
  private long totalRecords;
  private List eventBeans;

  public ListEventBeans() {
    eventBeans = new ArrayList();
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId( int clientId ) {
    this.clientId = clientId;
  }

  public long getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords( long totalRecords ) {
    this.totalRecords = totalRecords;
  }

  public List getEventBeans() {
    return eventBeans;
  }

  public void setEventBeans( List eventBeans ) {
    this.eventBeans = eventBeans;
  }

}
