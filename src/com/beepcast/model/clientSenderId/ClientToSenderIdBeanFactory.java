package com.beepcast.model.clientSenderId;

public class ClientToSenderIdBeanFactory {

  public static ClientToSenderIdBean createClientToSenderIdBean( int clientId ,
      String outgoingNumber , String senderId , String description ) {
    ClientToSenderIdBean bean = new ClientToSenderIdBean();
    bean.setClientId( clientId );
    bean.setOutgoingNumber( outgoingNumber );
    bean.setSenderId( senderId );
    bean.setDescription( description );
    bean.setActive( true );
    return bean;
  }

}
