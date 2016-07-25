package com.beepcast.model.provider;

public class ProviderBean {

  private int id;
  private int masterId;
  private String providerId;
  private String providerName;
  private String direction;
  private String type;
  private String shortCode;
  private String countryCode;
  private String accessUrl;
  private String accessUsername;
  private String accessPassword;
  private String listenerUrl;
  private double inCreditCost;
  private double ouCreditCost;
  private String description;
  private boolean active;

  public ProviderBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public int getMasterId() {
    return masterId;
  }

  public void setMasterId( int masterId ) {
    this.masterId = masterId;
  }

  public String getProviderId() {
    return providerId;
  }

  public void setProviderId( String providerId ) {
    this.providerId = providerId;
  }

  public String getProviderName() {
    return providerName;
  }

  public void setProviderName( String providerName ) {
    this.providerName = providerName;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection( String direction ) {
    this.direction = direction;
  }

  public String getType() {
    return type;
  }

  public void setType( String type ) {
    this.type = type;
  }

  public String getShortCode() {
    return shortCode;
  }

  public void setShortCode( String shortCode ) {
    this.shortCode = shortCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode( String countryCode ) {
    this.countryCode = countryCode;
  }

  public String getAccessUrl() {
    return accessUrl;
  }

  public void setAccessUrl( String accessUrl ) {
    this.accessUrl = accessUrl;
  }

  public String getAccessUsername() {
    return accessUsername;
  }

  public void setAccessUsername( String accessUsername ) {
    this.accessUsername = accessUsername;
  }

  public String getAccessPassword() {
    return accessPassword;
  }

  public void setAccessPassword( String accessPassword ) {
    this.accessPassword = accessPassword;
  }

  public String getListenerUrl() {
    return listenerUrl;
  }

  public void setListenerUrl( String listenerUrl ) {
    this.listenerUrl = listenerUrl;
  }

  public double getInCreditCost() {
    return inCreditCost;
  }

  public void setInCreditCost( double inCreditCost ) {
    this.inCreditCost = inCreditCost;
  }

  public double getOuCreditCost() {
    return ouCreditCost;
  }

  public void setOuCreditCost( double ouCreditCost ) {
    this.ouCreditCost = ouCreditCost;
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
    retValue = "ProviderBean ( " + "id = " + this.id + TAB + "masterId = "
        + this.masterId + TAB + "providerId = " + this.providerId + TAB
        + "providerName = " + this.providerName + TAB + "direction = "
        + this.direction + TAB + "type = " + this.type + TAB + "shortCode = "
        + this.shortCode + TAB + "accessUrl = " + this.accessUrl + TAB
        + "accessUsername = " + this.accessUsername + TAB + "accessPassword = "
        + this.accessPassword + TAB + "listenerUrl = " + this.listenerUrl + TAB
        + "inCreditCost = " + this.inCreditCost + TAB + "ouCreditCost = "
        + this.ouCreditCost + TAB + " )";
    return retValue;
  }

}
