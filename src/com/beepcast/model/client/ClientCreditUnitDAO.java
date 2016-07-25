package com.beepcast.model.client;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientCreditUnitDAO {

  static final DLogContext lctx = new SimpleContext( "ClientCreditUnitDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public ClientCreditUnitDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public boolean insert( ClientCreditUnitBean bean ) {
    boolean result = false;

    int clientId = bean.getClientId();
    double unit = bean.getUnit();
    String description = bean.getDescription();

    String sqlInsert = "INSERT INTO client_credit_unit ";
    sqlInsert += "(client_id,unit,description,active,date_inserted,date_updated) ";
    String sqlValues = "VALUES ";
    sqlValues += "( " + clientId + " , " + unit + " , '" + description + "' , "
        + " 1 , NOW() , NOW() )";

    String sql = sqlInsert + sqlValues;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

}
