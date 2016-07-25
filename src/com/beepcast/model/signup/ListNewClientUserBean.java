package com.beepcast.model.signup;

import java.util.List;

public class ListNewClientUserBean {

  private long totalRecords;
  private List listBeans;

  public ListNewClientUserBean() {
  }

  public long getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords( long totalRecords ) {
    this.totalRecords = totalRecords;
  }

  public List getListBeans() {
    return listBeans;
  }

  public void setListBeans( List listBeans ) {
    this.listBeans = listBeans;
  }

}
