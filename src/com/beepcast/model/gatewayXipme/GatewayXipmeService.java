package com.beepcast.model.gatewayXipme;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GatewayXipmeService {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "GatewayXipmeService" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private GatewayXipmeDAO dao;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public GatewayXipmeService() {
    dao = new GatewayXipmeDAO();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insert( GatewayXipmeBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert "
          + ", found null gateway xipme bean" );
      return result;
    }
    if ( StringUtils.isBlank( bean.getGatewayXipmeId() ) ) {
      DLog.warning( lctx , "Failed to insert "
          + ", found blank gateway xipme id" );
      return result;
    }
    result = dao.insert( bean );
    return result;
  }

}
