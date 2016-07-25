package com.beepcast.model.webTransaction;

public class WebTransactionBean {

  private int id;
  private String messageId;
  private int eventId;
  private String mobileNumber;
  private double debitAmount;
  private String browserAddress;
  private String browserAgent;
  private String messageRequest;
  private String messageResponse;
  private String statusCode;
  private String statusDescription;

  public WebTransactionBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId( String messageId ) {
    this.messageId = messageId;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId( int eventId ) {
    this.eventId = eventId;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber( String mobileNumber ) {
    this.mobileNumber = mobileNumber;
  }

  public double getDebitAmount() {
    return debitAmount;
  }

  public void setDebitAmount( double debitAmount ) {
    this.debitAmount = debitAmount;
  }

  public String getBrowserAddress() {
    return browserAddress;
  }

  public void setBrowserAddress( String browserAddress ) {
    this.browserAddress = browserAddress;
  }

  public String getBrowserAgent() {
    return browserAgent;
  }

  public void setBrowserAgent( String browserAgent ) {
    this.browserAgent = browserAgent;
  }

  public String getMessageRequest() {
    return messageRequest;
  }

  public void setMessageRequest( String messageRequest ) {
    this.messageRequest = messageRequest;
  }

  public String getMessageResponse() {
    return messageResponse;
  }

  public void setMessageResponse( String messageResponse ) {
    this.messageResponse = messageResponse;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode( String statusCode ) {
    this.statusCode = statusCode;
  }

  public String getStatusDescription() {
    return statusDescription;
  }

  public void setStatusDescription( String statusDescription ) {
    this.statusDescription = statusDescription;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "WebTransactionBean ( " + "id = " + this.id + TAB
        + "messageId = " + this.messageId + TAB + "eventId = " + this.eventId
        + TAB + "mobileNumber = " + this.mobileNumber + TAB + "debitAmount = "
        + this.debitAmount + TAB + "browserAddress = " + this.browserAddress
        + TAB + "browserAgent = " + this.browserAgent + TAB
        + "messageRequest = " + this.messageRequest + TAB
        + "messageResponse = " + this.messageResponse + TAB + "statusCode = "
        + this.statusCode + TAB + "statusDescription = "
        + this.statusDescription + TAB + " )";
    return retValue;
  }

}
