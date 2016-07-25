package com.beepcast.model.clientApi;

import java.util.List;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientToApisService {

  static final DLogContext lctx = new SimpleContext( "ClientToApisService" );

  private ClientToApisDAO dao;

  public ClientToApisService() {
    dao = new ClientToApisDAO();
  }

  public List listClientApis( int clientId ) {
    List listClientApis = null;
    if ( clientId < 1 ) {
      return listClientApis;
    }
    listClientApis = dao.listClientApis( clientId );
    return listClientApis;
  }

  public int updateClientApis( int clientId , List listClientApis ) {
    int totalRecords = 0;
    if ( clientId < 1 ) {
      return totalRecords;
    }
    if ( listClientApis == null ) {
      return totalRecords;
    }
    totalRecords = dao.updateClientApis( clientId , listClientApis );
    return totalRecords;
  }

}
