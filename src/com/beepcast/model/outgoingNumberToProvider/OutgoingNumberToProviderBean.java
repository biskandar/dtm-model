package com.beepcast.model.outgoingNumberToProvider;

public class OutgoingNumberToProviderBean {

  private int id;
  private String outgoingNumber;
  private int level;
  private String countryCode;
  private String prefixNumber;
  private String telcoCode;
  private String providerId;
  private int groupConnectionId;
  private String masked;
  private String description;
  private int priority;
  private boolean active;
  private boolean suspend;

  public OutgoingNumberToProviderBean() {

  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getOutgoingNumber() {
    return outgoingNumber;
  }

  public void setOutgoingNumber( String outgoingNumber ) {
    this.outgoingNumber = outgoingNumber;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel( int level ) {
    this.level = level;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode( String countryCode ) {
    this.countryCode = countryCode;
  }

  public String getPrefixNumber() {
    return prefixNumber;
  }

  public void setPrefixNumber( String prefixNumber ) {
    this.prefixNumber = prefixNumber;
  }

  public String getTelcoCode() {
    return telcoCode;
  }

  public void setTelcoCode( String telcoCode ) {
    this.telcoCode = telcoCode;
  }

  public String getProviderId() {
    return providerId;
  }

  public void setProviderId( String providerId ) {
    this.providerId = providerId;
  }

  public int getGroupConnectionId() {
    return groupConnectionId;
  }

  public void setGroupConnectionId( int groupConnectionId ) {
    this.groupConnectionId = groupConnectionId;
  }

  public String getMasked() {
    return masked;
  }

  public void setMasked( String masked ) {
    this.masked = masked;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority( int priority ) {
    this.priority = priority;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive( boolean active ) {
    this.active = active;
  }

  public boolean isSuspend() {
    return suspend;
  }

  public void setSuspend( boolean suspend ) {
    this.suspend = suspend;
  }

  public String toString() {
    return "OutgoingNumberToProviderBean [id=" + id + ", outgoingNumber="
        + outgoingNumber + ", level=" + level + ", countryCode=" + countryCode
        + ", prefixNumber=" + prefixNumber + ", telcoCode=" + telcoCode
        + ", providerId=" + providerId + ", groupConnectionId="
        + groupConnectionId + ", masked=" + masked + ", priority=" + priority
        + ", active=" + active + ", suspend=" + suspend + "]";
  }

}
