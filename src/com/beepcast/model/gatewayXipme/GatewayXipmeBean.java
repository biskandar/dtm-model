package com.beepcast.model.gatewayXipme;

public class GatewayXipmeBean {

  private int id;
  private String gatewayXipmeId;
  private String xipmeMasterCode;
  private String xipmeCode;
  private String xipmeCodeEncrypted;

  public GatewayXipmeBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getGatewayXipmeId() {
    return gatewayXipmeId;
  }

  public void setGatewayXipmeId( String gatewayXipmeId ) {
    this.gatewayXipmeId = gatewayXipmeId;
  }

  public String getXipmeMasterCode() {
    return xipmeMasterCode;
  }

  public void setXipmeMasterCode( String xipmeMasterCode ) {
    this.xipmeMasterCode = xipmeMasterCode;
  }

  public String getXipmeCode() {
    return xipmeCode;
  }

  public void setXipmeCode( String xipmeCode ) {
    this.xipmeCode = xipmeCode;
  }

  public String getXipmeCodeEncrypted() {
    return xipmeCodeEncrypted;
  }

  public void setXipmeCodeEncrypted( String xipmeCodeEncrypted ) {
    this.xipmeCodeEncrypted = xipmeCodeEncrypted;
  }

}
