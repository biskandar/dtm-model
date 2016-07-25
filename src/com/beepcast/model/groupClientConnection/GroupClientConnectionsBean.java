package com.beepcast.model.groupClientConnection;

import java.util.List;

public class GroupClientConnectionsBean {

  private long totalRecords;
  private List listRecords;

  public GroupClientConnectionsBean() {

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
