package com.beepcast.model.clientApi;

public class ClientApiBean {

  public static final String VERSION_01_00 = "01.00";
  public static final String VERSION_02_00 = "02.00";

  public static final String DIRECTION_PULL = "pull";
  public static final String DIRECTION_PUSH = "push";

  public static final String FORMAT_XML = "xml";
  public static final String FORMAT_JSON = "json";

  private int id;
  private int clientId;
  private String apiVersion;
  private String moDirection;
  private String moUri;
  private String moFormat;
  private String moApiVer;
  private String dnDirection;
  private String dnUri;
  private String dnFormat;
  private String dnApiVer;
  private boolean active;
  private String description;

  public ClientApiBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId( int clientId ) {
    this.clientId = clientId;
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion( String apiVersion ) {
    this.apiVersion = apiVersion;
  }

  public String getMoDirection() {
    return moDirection;
  }

  public void setMoDirection( String moDirection ) {
    this.moDirection = moDirection;
  }

  public String getMoUri() {
    return moUri;
  }

  public void setMoUri( String moUri ) {
    this.moUri = moUri;
  }

  public String getMoFormat() {
    return moFormat;
  }

  public void setMoFormat( String moFormat ) {
    this.moFormat = moFormat;
  }

  public String getMoApiVer() {
    return moApiVer;
  }

  public void setMoApiVer( String moApiVer ) {
    this.moApiVer = moApiVer;
  }

  public String getDnDirection() {
    return dnDirection;
  }

  public void setDnDirection( String dnDirection ) {
    this.dnDirection = dnDirection;
  }

  public String getDnUri() {
    return dnUri;
  }

  public void setDnUri( String dnUri ) {
    this.dnUri = dnUri;
  }

  public String getDnFormat() {
    return dnFormat;
  }

  public void setDnFormat( String dnFormat ) {
    this.dnFormat = dnFormat;
  }

  public String getDnApiVer() {
    return dnApiVer;
  }

  public void setDnApiVer( String dnApiVer ) {
    this.dnApiVer = dnApiVer;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive( boolean active ) {
    this.active = active;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "ClientApiBean ( " + "id = " + this.id + TAB + "clientId = "
        + this.clientId + TAB + "apiVersion = " + this.apiVersion + TAB
        + "moDirection = " + this.moDirection + TAB + "moUri = " + this.moUri
        + TAB + "moFormat = " + this.moFormat + TAB + "moApiVer = "
        + this.moApiVer + TAB + "dnDirection = " + this.dnDirection + TAB
        + "dnUri = " + this.dnUri + TAB + "dnFormat = " + this.dnFormat + TAB
        + "dnApiVer = " + this.dnApiVer + TAB + "active = " + this.active + TAB
        + "description = " + this.description + TAB + " )";
    return retValue;
  }

}
