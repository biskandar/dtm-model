package com.beepcast.model.number;

public class ClientConnectionBean {

  private int id;
  private int groupClientConnectionId;
  private String number;
  private String description;
  private boolean active;

  public ClientConnectionBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public int getGroupClientConnectionId() {
    return groupClientConnectionId;
  }

  public void setGroupClientConnectionId( int groupClientConnectionId ) {
    this.groupClientConnectionId = groupClientConnectionId;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber( String number ) {
    this.number = number;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive( boolean active ) {
    this.active = active;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "ClientConnectionBean ( " + "id = " + this.id + TAB
        + "groupClientConnectionId = " + this.groupClientConnectionId + TAB
        + "number = " + this.number + TAB + "description = " + this.description
        + TAB + "active = " + this.active + TAB + " )";
    return retValue;
  }

}
