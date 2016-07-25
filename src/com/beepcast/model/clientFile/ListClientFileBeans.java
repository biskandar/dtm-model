package com.beepcast.model.clientFile;

import java.util.ArrayList;
import java.util.List;

public class ListClientFileBeans {

  private int clientId;
  private long totalRecords;
  private List listRecords;

  public ListClientFileBeans() {
    listRecords = new ArrayList();
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

  public List getListRecords() {
    return listRecords;
  }

  public void setListRecords( List listRecords ) {
    this.listRecords = listRecords;
  }
}
