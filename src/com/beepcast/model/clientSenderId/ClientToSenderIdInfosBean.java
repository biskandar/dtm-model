package com.beepcast.model.clientSenderId;

import java.util.ArrayList;
import java.util.List;

public class ClientToSenderIdInfosBean {

  private int totalRecords;
  private List listRecords;

  public ClientToSenderIdInfosBean() {
    listRecords = new ArrayList();
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
