package com.beepcast.model.clientApi;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientApiFactory {

  static final DLogContext lctx = new SimpleContext( "ClientApiFactory" );

  public static ClientApiBean createClientApiBean( int clientId ,
      String moDirection , String moUri , String moFormat , String dnDirection ,
      String dnUri , String dnFormat ) {
    return createClientApiBean( clientId , ClientApiBean.VERSION_01_00 ,
        moDirection , moUri , moFormat , ClientApiBean.VERSION_01_00 ,
        dnDirection , dnUri , dnFormat , ClientApiBean.VERSION_01_00 , true ,
        "" );
  }

  public static ClientApiBean createClientApiBean( int clientId ,
      String apiVersion , String moDirection , String moUri , String moFormat ,
      String moApiVer , String dnDirection , String dnUri , String dnFormat ,
      String dnApiVer , boolean active , String description ) {
    ClientApiBean bean = null;
    if ( clientId < 1 ) {
      return bean;
    }
    bean = new ClientApiBean();
    bean.setClientId( clientId );
    bean.setApiVersion( apiVersion );
    bean.setMoDirection( moDirection );
    bean.setMoUri( moUri );
    bean.setMoFormat( moFormat );
    bean.setMoApiVer( moApiVer );
    bean.setDnDirection( dnDirection );
    bean.setDnUri( dnUri );
    bean.setDnFormat( dnFormat );
    bean.setDnApiVer( dnApiVer );
    bean.setActive( active );
    bean.setDescription( description );
    return bean;
  }

}
