package com.beepcast.model.clientSenderId;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToSenderIdService {

  static final DLogContext lctx = new SimpleContext( "ClientToSenderIdService" );

  private ClientToSenderIdDAO dao;

  public ClientToSenderIdService() {
    dao = new ClientToSenderIdDAO();
  }

  public boolean insertBean( ClientToSenderIdBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert bean , found null bean" );
      return result;
    }
    if ( bean.getClientId() < 1 ) {
      DLog.warning( lctx , "Failed to insert bean , found zero client id" );
      return result;
    }
    result = dao.insertBean( bean );
    return result;
  }

  public ClientToSenderIdBean selectBean( int clientId , String outgoingNumber ,
      String senderId ) {
    ClientToSenderIdBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to select bean , found zero client id" );
      return bean;
    }
    bean = dao.selectBean( clientId , outgoingNumber , senderId );
    return bean;
  }

  public ClientToSenderIdBean selectBean( int clientToSenderIdId ) {
    ClientToSenderIdBean bean = null;
    if ( clientToSenderIdId < 1 ) {
      DLog.warning( lctx , "Failed to select bean "
          + ", found zero client to sender id id" );
      return bean;
    }
    bean = dao.selectBean( clientToSenderIdId );
    return bean;
  }

  public boolean updateProfile( ClientToSenderIdBean bean ) {
    boolean result = false;
    if ( bean.getId() < 1 ) {
      DLog.warning( lctx , "Failed to update profile , found zero id" );
      return result;
    }
    result = dao.updateProfile( bean );
    return result;
  }

  public boolean delete( int id ) {
    boolean result = false;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to update inactive , found zero id" );
      return result;
    }
    result = dao.updateInActive( id );
    return result;
  }

}
