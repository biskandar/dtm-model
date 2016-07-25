package com.beepcast.model.gatewayXipme;

public class GatewayXipmeBeanFactory {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public static GatewayXipmeBean createGatewayXipmeBean( String gatewayXipmeId ,
      String xipmeMasterCode , String xipmeCode , String xipmeCodeEncrypted ) {
    GatewayXipmeBean bean = new GatewayXipmeBean();
    bean.setGatewayXipmeId( gatewayXipmeId );
    bean.setXipmeMasterCode( xipmeMasterCode );
    bean.setXipmeCode( xipmeCode );
    bean.setXipmeCodeEncrypted( xipmeCodeEncrypted );
    return bean;
  }

}
