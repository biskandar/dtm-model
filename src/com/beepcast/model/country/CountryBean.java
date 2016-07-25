package com.beepcast.model.country;

public class CountryBean {

  private int id;
  private String code;
  private String name;
  private String description;
  private int phoneLength;
  private int phoneLengthMin;
  private int phoneLengthMax;
  private String phonePrefix;
  private String currencyCode;
  private int orderId;
  private boolean active;

  public CountryBean() {

  }

  public int getId() {
    return id;
  }

  public String getStrId() {
    return Integer.toString( id );
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode( String code ) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public int getPhoneLength() {
    return phoneLength;
  }

  public void setPhoneLength( int phoneLength ) {
    this.phoneLength = phoneLength;
  }

  public int getPhoneLengthMin() {
    return phoneLengthMin;
  }

  public void setPhoneLengthMin( int phoneLengthMin ) {
    this.phoneLengthMin = phoneLengthMin;
  }

  public int getPhoneLengthMax() {
    return phoneLengthMax;
  }

  public void setPhoneLengthMax( int phoneLengthMax ) {
    this.phoneLengthMax = phoneLengthMax;
  }

  public String getPhonePrefix() {
    return phonePrefix;
  }

  public void setPhonePrefix( String phonePrefix ) {
    this.phonePrefix = phonePrefix;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode( String currencyCode ) {
    this.currencyCode = currencyCode;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId( int orderId ) {
    this.orderId = orderId;
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
    retValue = "CountryBean ( " + "id = " + this.id + TAB + "code = "
        + this.code + TAB + "name = " + this.name + TAB + "description = "
        + this.description + TAB + "phoneLength = " + this.phoneLength + TAB
        + "phoneLengthMin = " + this.phoneLengthMin + TAB + "phoneLengthMax = "
        + this.phoneLengthMax + TAB + "phonePrefix = " + this.phonePrefix + TAB
        + "currencyCode = " + this.currencyCode + TAB + "orderId = "
        + this.orderId + TAB + "active = " + this.active + TAB + " )";
    return retValue;
  }

}
