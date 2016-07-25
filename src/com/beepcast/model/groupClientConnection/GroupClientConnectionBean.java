package com.beepcast.model.groupClientConnection;

public class GroupClientConnectionBean {

  private int id;
  private String name;
  private boolean active;

  public GroupClientConnectionBean() {

  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
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
    retValue = "GroupClientConnectionBean ( " + "id = " + this.id + TAB
        + "name = " + this.name + TAB + "active = " + this.active + TAB + " )";
    return retValue;
  }

}
