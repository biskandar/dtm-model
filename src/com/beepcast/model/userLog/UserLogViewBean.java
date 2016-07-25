package com.beepcast.model.userLog;

import java.util.Date;

public class UserLogViewBean {

  private int userLogId;
  private String companyName;
  private String userId;
  private String actionText;
  private Date actionDate;

  public UserLogViewBean() {
  }

  public int getUserLogId() {
    return userLogId;
  }

  public void setUserLogId( int userLogId ) {
    this.userLogId = userLogId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName( String companyName ) {
    this.companyName = companyName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId( String userId ) {
    this.userId = userId;
  }

  public String getActionText() {
    return actionText;
  }

  public void setActionText( String actionText ) {
    this.actionText = actionText;
  }

  public Date getActionDate() {
    return actionDate;
  }

  public void setActionDate( Date actionDate ) {
    this.actionDate = actionDate;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "UserLogViewBean ( " + "companyName = " + this.companyName + TAB
        + "userId = " + this.userId + TAB + "actionText = " + this.actionText
        + TAB + "actionDate = " + this.actionDate + TAB + " )";
    return retValue;
  }

}
