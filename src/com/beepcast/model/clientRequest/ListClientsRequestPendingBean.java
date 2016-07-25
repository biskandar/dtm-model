package com.beepcast.model.clientRequest;

import java.util.List;

public class ListClientsRequestPendingBean {

  private String memberClientIds;
  private long totalRecords;
  private List listRecords;

  public ListClientsRequestPendingBean() {

  }

  public String getMemberClientIds() {
    return memberClientIds;
  }

  public void setMemberClientIds( String memberClientIds ) {
    this.memberClientIds = memberClientIds;
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
