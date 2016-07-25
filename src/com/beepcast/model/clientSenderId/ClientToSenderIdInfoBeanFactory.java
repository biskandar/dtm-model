package com.beepcast.model.clientSenderId;

public class ClientToSenderIdInfoBeanFactory {

  public static ClientToSenderIdInfoBean createClientToSenderIdInfoBean(
      int clientId , String companyName , String outgoingNumber ,
      String senderId , String description , boolean active ) {
    ClientToSenderIdInfoBean bean = new ClientToSenderIdInfoBean();
    bean.setClientId( clientId );
    bean.setCompanyName( companyName );
    bean.setOutgoingNumber( outgoingNumber );
    bean.setSenderId( senderId );
    bean.setDescription( description );
    bean.setActive( active );
    return bean;
  }

}
