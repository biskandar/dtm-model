package com.beepcast.model.gatewayXipme;

import java.util.Iterator;
import java.util.List;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GatewayXipmesDAO {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "GatewayXipmesDAO" );

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

  public GatewayXipmesDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public int insert( List listGatewayXipmeBeans ) {
    int totalInserted = 0;
    if ( listGatewayXipmeBeans == null ) {
      return totalInserted;
    }

    // compose sql
    String sqlInsert = "INSERT INTO gateway_xipme ( gateway_xipme_id ";
    sqlInsert += ", xipme_master_code , xipme_code , xipme_code_encrypted ";
    sqlInsert += ", date_inserted ) ";
    String sqlValues = "VALUES ( ? , ? , ? , ? , NOW() ) ";
    String sql = sqlInsert + sqlValues;

    // compose param set
    int idxRec = 0 , maxRec = listGatewayXipmeBeans.size();
    Object[][] params = new Object[maxRec][];
    GatewayXipmeBean gatewayXipmeBean = null;
    Iterator iterGatewayXipmeBeans = listGatewayXipmeBeans.iterator();
    while ( iterGatewayXipmeBeans.hasNext() ) {
      gatewayXipmeBean = (GatewayXipmeBean) iterGatewayXipmeBeans.next();
      if ( gatewayXipmeBean == null ) {
        continue;
      }
      gatewayXipmeBean = GatewayXipmeBeanUtil.cleanBean( gatewayXipmeBean );
      if ( gatewayXipmeBean == null ) {
        continue;
      }
      params[idxRec] = new Object[4];
      params[idxRec][0] = gatewayXipmeBean.getGatewayXipmeId();
      params[idxRec][1] = gatewayXipmeBean.getXipmeMasterCode();
      params[idxRec][2] = gatewayXipmeBean.getXipmeCode();
      params[idxRec][3] = gatewayXipmeBean.getXipmeCodeEncrypted();
      idxRec = idxRec + 1;
    }

    // execute sql
    int[] results = dbLib
        .executeBatchStatement( "transactiondb" , sql , params );
    if ( results == null ) {
      DLog.warning( lctx , "Failed to execute the batch insert into "
          + "gateway xipme table" );
      return totalInserted;
    }

    // calculate total inserted
    int i , n = results.length;
    for ( i = 0 ; i < n ; i++ ) {
      totalInserted = totalInserted + results[i];
    }

    return totalInserted;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ////////////////////////////////////////////////////////////////////////////

}
