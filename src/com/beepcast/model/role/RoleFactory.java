package com.beepcast.model.role;

public class RoleFactory {

  public static RoleBean createRoleBean( String role , String childRoles ,
      String menus ) {
    RoleBean roleBean = new RoleBean();
    roleBean.setRole( role );
    roleBean.setChildRoles( childRoles );
    roleBean.setMenus( menus );
    roleBean.setActive( true );
    return roleBean;
  }

}
