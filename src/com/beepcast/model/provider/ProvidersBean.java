package com.beepcast.model.provider;

import java.util.List;

public class ProvidersBean {

  private long totalRecords;
  private List listRecords;

  public ProvidersBean() {

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
