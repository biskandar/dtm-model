package com.beepcast.model.role;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class RoleService {

  static final DLogContext lctx = new SimpleContext( "RoleService" );

  private RoleDAO dao;

  public RoleService() {
    dao = new RoleDAO();
  }

  public boolean verifyRoleBean( RoleBean roleBean ) {
    boolean result = false;
    if ( roleBean == null ) {
      return result;
    }
    if ( StringUtils.isBlank( roleBean.getRole() ) ) {
      return result;
    }
    result = true;
    return result;
  }

  public boolean insertRoleBean( RoleBean roleBean ) {
    boolean result = false;
    if ( !verifyRoleBean( roleBean ) ) {
      DLog.warning( lctx , "Failed to insert role bean , found null bean" );
      return result;
    }
    result = dao.insertRoleBean( roleBean );
    return result;
  }

  public RoleBean selectBasedOnRole( String role ) {
    RoleBean bean = null;
    if ( StringUtils.isBlank( role ) ) {
      DLog.warning( lctx , "Failed to insert role bean , found empty role" );
      return bean;
    }
    bean = dao.selectBasedOnRole( role );
    return bean;
  }

  public boolean delete( String role ) {
    return dao.delete( role );
  }

}
