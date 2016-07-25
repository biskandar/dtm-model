package com.beepcast.model.outgoingNumberToProvider;

import java.util.List;

public class OutgoingNumberToProvidersBean {

  private long totalRecords;
  private List listRecords;

  public OutgoingNumberToProvidersBean() {

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
