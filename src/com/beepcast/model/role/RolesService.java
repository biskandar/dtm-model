package com.beepcast.model.role;

import com.beepcast.model.client.ClientType;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class RolesService {

  static final DLogContext lctx = new SimpleContext( "RolesService" );

  private RolesDAO dao;

  public RolesService() {
    dao = new RolesDAO();
  }

  public RolesBean generateActiveRoleBeans() {
    return dao.generateActiveRoleBeans();
  }

  public RolesBean generateActiveRoleBeansByRoles( String strRoles ) {
    return dao.generateActiveRoleBeansByRoles( strRoles );
  }

  public RolesBean generateActiveRoleBeansByClientType( int clientType ) {
    String notInRoles = null;
    if ( clientType == ClientType.SUPER ) {
      notInRoles = null;
    }
    if ( clientType == ClientType.MASTER ) {
      notInRoles = "'SUPER'";
    }
    if ( clientType == ClientType.USER ) {
      notInRoles = "'SUPER','MASTER'";
    }
    return dao.generateActiveRoleBeansByNotInRoles( notInRoles );
  }

}
