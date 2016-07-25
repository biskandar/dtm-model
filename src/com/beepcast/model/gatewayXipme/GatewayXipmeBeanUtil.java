package com.beepcast.model.gatewayXipme;

public class GatewayXipmeBeanUtil {

  public static GatewayXipmeBean cleanBean( GatewayXipmeBean bean ) {

    // validate must be params
    if ( bean == null ) {
      return bean;
    }

    // read params
    String gatewayXipmeId = bean.getGatewayXipmeId();
    String xipmeMasterCode = bean.getXipmeMasterCode();
    String xipmeCode = bean.getXipmeCode();
    String xipmeCodeEncrypted = bean.getXipmeCodeEncrypted();

    // clean params
    gatewayXipmeId = ( gatewayXipmeId == null ) ? "" : gatewayXipmeId.trim();
    xipmeMasterCode = ( xipmeMasterCode == null ) ? "" : xipmeMasterCode.trim();
    xipmeCode = ( xipmeCode == null ) ? "" : xipmeCode.trim();
    xipmeCodeEncrypted = ( xipmeCodeEncrypted == null ) ? ""
        : xipmeCodeEncrypted.trim();

    // update params
    bean.setGatewayXipmeId( gatewayXipmeId );
    bean.setXipmeMasterCode( xipmeMasterCode );
    bean.setXipmeCode( xipmeCode );
    bean.setXipmeCodeEncrypted( xipmeCodeEncrypted );

    return bean;
  }

}
