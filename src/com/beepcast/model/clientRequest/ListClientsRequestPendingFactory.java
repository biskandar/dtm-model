package com.beepcast.model.clientRequest;

public class ListClientsRequestPendingFactory {

  public static ListClientsRequestPendingBean createListClientsRequestPendingBean(
      String memberClientIds ) {
    ListClientsRequestPendingBean bean = new ListClientsRequestPendingBean();
    bean.setMemberClientIds( memberClientIds );
    return bean;
  }

}
