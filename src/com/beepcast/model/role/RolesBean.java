package com.beepcast.model.role;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public class RolesBean {

  private HashMap roleMap;

  public RolesBean() {
    roleMap = new HashMap();
  }

  public void reset() {
    roleMap.clear();
  }

  public long totalRecords() {
    return roleMap.size();
  }

  public boolean addRoleBean( RoleBean roleBean ) {
    boolean result = false;
    if ( !verifyRoleBean( roleBean ) ) {
      return result;
    }
    roleMap.put( keyRoleBean( roleBean ) , roleBean );
    result = true;
    return result;
  }

  public RoleBean getRoleBean( String role ) {
    RoleBean roleBean = null;
    if ( !StringUtils.isBlank( role ) ) {
      roleBean = (RoleBean) roleMap.get( role );
    }
    return roleBean;
  }

  public ArrayList listRolesBean() {
    return new ArrayList( roleMap.values() );
  }

  private boolean verifyRoleBean( RoleBean roleBean ) {
    boolean verify = false;
    if ( roleBean == null ) {
      return verify;
    }
    String role = roleBean.getRole();
    if ( StringUtils.isBlank( role ) ) {
      return verify;
    }
    verify = true;
    return verify;
  }

  private String keyRoleBean( RoleBean roleBean ) {
    String key = null;
    if ( roleBean != null ) {
      key = roleBean.getRole();
    }
    return key;
  }

}
