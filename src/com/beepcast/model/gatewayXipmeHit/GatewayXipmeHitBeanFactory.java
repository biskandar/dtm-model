package com.beepcast.model.gatewayXipmeHit;

import java.util.Date;

public class GatewayXipmeHitBeanFactory {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public static GatewayXipmeHitBean createGatewayXipmeHitBean(
      String gatewayXipmeId , String xipmeMasterCode , String xipmeCode ,
      String visitId , Date dateHit ) {
    GatewayXipmeHitBean bean = new GatewayXipmeHitBean();
    bean.setGatewayXipmeId( gatewayXipmeId );
    bean.setXipmeMasterCode( xipmeMasterCode );
    bean.setXipmeCode( xipmeCode );
    bean.setVisitId( visitId );
    bean.setDateHit( dateHit );
    return bean;
  }

}
