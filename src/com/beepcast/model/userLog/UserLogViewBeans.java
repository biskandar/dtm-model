package com.beepcast.model.userLog;

import java.util.List;

public class UserLogViewBeans {

  private int totalRecords;
  private List listRecords;

  public UserLogViewBeans() {
  }

  public int getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords( int totalRecords ) {
    this.totalRecords = totalRecords;
  }

  public List getListRecords() {
    return listRecords;
  }

  public void setListRecords( List listRecords ) {
    this.listRecords = listRecords;
  }

}
