package com.beepcast.model.gatewayXipmeHit;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.model.util.DateTimeFormat;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GatewayXipmeHitDAO {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "GatewayXipmeHitDAO" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public GatewayXipmeHitDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insert( GatewayXipmeHitBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }

    // read params
    String gatewayXipmeId = bean.getGatewayXipmeId();
    String xipmeMasterCode = bean.getXipmeMasterCode();
    String xipmeCode = bean.getXipmeCode();
    String visitId = bean.getVisitId();
    Date dateHit = bean.getDateHit();

    // clean params
    gatewayXipmeId = ( gatewayXipmeId == null ) ? "" : gatewayXipmeId.trim();
    xipmeMasterCode = ( xipmeMasterCode == null ) ? "" : xipmeMasterCode.trim();
    xipmeCode = ( xipmeCode == null ) ? "" : xipmeCode.trim();
    visitId = ( visitId == null ) ? "" : visitId.trim();
    dateHit = ( dateHit == null ) ? new Date() : dateHit;

    // compose sql
    String sqlInsert = "INSERT INTO gateway_xipme_hit ( gateway_xipme_id "
        + ", xipme_master_code , xipme_code , visit_id , date_hit , date_inserted ) ";
    String sqlValues = "VALUES ( '"
        + StringEscapeUtils.escapeSql( gatewayXipmeId ) + "' , '"
        + StringEscapeUtils.escapeSql( xipmeMasterCode ) + "' , '"
        + StringEscapeUtils.escapeSql( xipmeCode ) + "' , '"
        + StringEscapeUtils.escapeSql( visitId ) + "' , '"
        + DateTimeFormat.convertToString( dateHit ) + "' , NOW() ) ";
    String sql = sqlInsert + sqlValues;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    // execute sql
    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  // ...

}
