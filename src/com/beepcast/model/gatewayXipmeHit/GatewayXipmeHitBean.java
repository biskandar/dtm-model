package com.beepcast.model.gatewayXipmeHit;

import java.util.Date;

public class GatewayXipmeHitBean {

  private int id;
  private String gatewayXipmeId;
  private String xipmeMasterCode;
  private String xipmeCode;
  private String visitId;
  private Date dateHit;

  public GatewayXipmeHitBean() {

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

  public String getVisitId() {
    return visitId;
  }

  public void setVisitId( String visitId ) {
    this.visitId = visitId;
  }

  public Date getDateHit() {
    return dateHit;
  }

  public void setDateHit( Date dateHit ) {
    this.dateHit = dateHit;
  }

}
