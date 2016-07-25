package com.beepcast.model.gatewayXipmeHit;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GatewayXipmeHitService {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "GatewayXipmeHitService" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private GatewayXipmeHitDAO dao;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public GatewayXipmeHitService() {
    dao = new GatewayXipmeHitDAO();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insert( GatewayXipmeHitBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert "
          + ", found null gateway xipme hit bean" );
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
