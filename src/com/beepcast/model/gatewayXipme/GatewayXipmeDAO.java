package com.beepcast.model.gatewayXipme;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GatewayXipmeDAO {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "GatewayXipmeDAO" );

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

  public GatewayXipmeDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insert( GatewayXipmeBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      return result;
    }

    // clean bean
    bean = GatewayXipmeBeanUtil.cleanBean( bean );

    // read params
    String gatewayXipmeId = bean.getGatewayXipmeId();
    String xipmeMasterCode = bean.getXipmeMasterCode();
    String xipmeCode = bean.getXipmeCode();
    String xipmeCodeEncrypted = bean.getXipmeCodeEncrypted();

    // compose sql
    String sqlInsert = "INSERT INTO gateway_xipme ( gateway_xipme_id ";
    sqlInsert += ", xipme_master_code , xipme_code , xipme_code_encrypted ";
    sqlInsert += ", date_inserted ) ";
    String sqlValues = "VALUES ( '"
        + StringEscapeUtils.escapeSql( gatewayXipmeId ) + "' , '"
        + StringEscapeUtils.escapeSql( xipmeMasterCode ) + "' , '"
        + StringEscapeUtils.escapeSql( xipmeCode ) + "' , '"
        + StringEscapeUtils.escapeSql( xipmeCodeEncrypted ) + "' , NOW() ) ";
    String sql = sqlInsert + sqlValues;

    // need to debug ?
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
