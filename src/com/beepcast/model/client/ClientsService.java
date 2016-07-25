package com.beepcast.model.client;

import org.apache.commons.lang.StringUtils;

import com.beepcast.billing.profile.PaymentType;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientsService {

  static final DLogContext lctx = new SimpleContext( "ClientsService" );

  private ClientsDAO dao;

  public ClientsService() {
    dao = new ClientsDAO();
  }

  public ClientsBean generateClientsBean( int clientType , int masterClientId ) {
    ClientsBean bean = null;

    int clientId = 0;

    if ( clientType == ClientType.SUPER ) {
      masterClientId = 0;
    }
    if ( clientType == ClientType.MASTER ) {
      if ( masterClientId < 1 ) {
        DLog.warning( lctx , "Failed to generate clients bean "
            + ", found empty master client id" );
        return bean;
      }
    }
    if ( clientType == ClientType.USER ) {
      if ( masterClientId < 1 ) {
        DLog.warning( lctx , "Failed to generate clients bean "
            + ", found empty master client id" );
        return bean;
      }
      clientId = masterClientId;
      masterClientId = 0;
    }

    bean = dao.generateClientsBean( masterClientId , clientId );
    return bean;
  }

  public ClientsBean generateClientsBeanByPaymentType( int paymentType ) {
    ClientsBean bean = null;
    if ( paymentType == PaymentType.UNKNOWN ) {
      return bean;
    }
    bean = dao.generateClientsBeanByPaymentType( paymentType );
    return bean;
  }

  public ClientsBean generateClientsBeanByClientIds( String strClientIds ) {
    ClientsBean bean = null;
    if ( StringUtils.isBlank( strClientIds ) ) {
      return bean;
    }
    bean = dao.generateClientsBeanByClientIds( strClientIds );
    return bean;
  }

}
