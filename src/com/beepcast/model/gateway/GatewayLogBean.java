package com.beepcast.model.gateway;

import java.util.Date;

public class GatewayLogBean {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private long logID; // primary key

  private long eventID;
  private long channelSessionID;
  private String provider;

  private String mode;
  private String phone;
  private String shortCode;
  private String senderID;

  private int phoneCountryId;

  private String messageType;
  private String messageCount;
  private String message;

  private double debitAmount;

  private String messageID;
  private String externalMessageID;
  private String status;
  private String externalStatus;

  private int retry;

  private Date dateTm;
  private Date statusDateTm;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public GatewayLogBean() {

    // default value

    dateTm = new Date();
    statusDateTm = null;
    shortCode = "";
    senderID = "";

  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Set / Get Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public long getLogID() {
    return logID;
  }

  public void setLogID( long logID ) {
    this.logID = logID;
  }

  public long getEventID() {
    return eventID;
  }

  public void setEventID( long eventID ) {
    this.eventID = eventID;
  }

  public long getChannelSessionID() {
    return channelSessionID;
  }

  public void setChannelSessionID( long channelSessionID ) {
    this.channelSessionID = channelSessionID;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider( String provider ) {
    this.provider = provider;
  }

  public String getMode() {
    return mode;
  }

  public void setMode( String mode ) {
    this.mode = mode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone( String phone ) {
    this.phone = phone;
  }

  public String getShortCode() {
    return shortCode;
  }

  public void setShortCode( String shortCode ) {
    this.shortCode = shortCode;
  }

  public String getSenderID() {
    return senderID;
  }

  public void setSenderID( String senderID ) {
    this.senderID = senderID;
  }

  public int getPhoneCountryId() {
    return phoneCountryId;
  }

  public void setPhoneCountryId( int phoneCountryId ) {
    this.phoneCountryId = phoneCountryId;
  }

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType( String messageType ) {
    this.messageType = messageType;
  }

  public String getMessageCount() {
    return messageCount;
  }

  public void setMessageCount( String messageCount ) {
    this.messageCount = messageCount;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage( String message ) {
    this.message = message;
  }

  public double getDebitAmount() {
    return debitAmount;
  }

  public void setDebitAmount( double debitAmount ) {
    this.debitAmount = debitAmount;
  }

  public String getMessageID() {
    return messageID;
  }

  public void setMessageID( String messageID ) {
    this.messageID = messageID;
  }

  public String getExternalMessageID() {
    return externalMessageID;
  }

  public void setExternalMessageID( String externalMessageID ) {
    this.externalMessageID = externalMessageID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus( String status ) {
    this.status = status;
  }

  public String getExternalStatus() {
    return externalStatus;
  }

  public void setExternalStatus( String externalStatus ) {
    this.externalStatus = externalStatus;
  }

  public int getRetry() {
    return retry;
  }

  public void setRetry( int retry ) {
    this.retry = retry;
  }

  public Date getDateTm() {
    return dateTm;
  }

  public void setDateTm( Date dateTm ) {
    this.dateTm = dateTm;
  }

  public Date getStatusDateTm() {
    return statusDateTm;
  }

  public void setStatusDateTm( Date statusDateTm ) {
    this.statusDateTm = statusDateTm;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Helper
  //
  // ////////////////////////////////////////////////////////////////////////////

  public String toString() {
    return "GatewayLogBean [channelSessionID=" + channelSessionID + ", dateTm="
        + dateTm + ", debitAmount=" + debitAmount + ", eventID=" + eventID
        + ", externalMessageID=" + externalMessageID + ", externalStatus="
        + externalStatus + ", logID=" + logID + ", message=" + message
        + ", messageCount=" + messageCount + ", messageID=" + messageID
        + ", messageType=" + messageType + ", mode=" + mode + ", phone="
        + phone + ", provider=" + provider + ", retry=" + retry + ", senderID="
        + senderID + ", shortCode=" + shortCode + ", status=" + status
        + ", statusDateTm=" + statusDateTm + "]";
  }

} // eof
