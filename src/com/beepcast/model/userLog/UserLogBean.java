package com.beepcast.model.userLog;

public class UserLogBean {

  private int id;
  private int userId;
  private String visitId;
  private String action;
  private boolean active;

  public UserLogBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId( int userId ) {
    this.userId = userId;
  }

  public String getVisitId() {
    return visitId;
  }

  public void setVisitId( String visitId ) {
    this.visitId = visitId;
  }

  public String getAction() {
    return action;
  }

  public void setAction( String action ) {
    this.action = action;
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
    retValue = "UserLogBean ( " + "id = " + this.id + TAB + "userId = "
        + this.userId + TAB + "visitId = " + this.visitId + TAB + "action = "
        + this.action + TAB + "active = " + this.active + TAB + " )";
    return retValue;
  }

}
