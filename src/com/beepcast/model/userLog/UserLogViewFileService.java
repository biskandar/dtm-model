package com.beepcast.model.userLog;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;

import com.beepcast.model.client.ClientType;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class UserLogViewFileService {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "UserLogViewFileService" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private UserLogViewFileDAO dao;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public UserLogViewFileService( int clientType , int masterClientId ,
      String findUserId , String findActionText , Date dateStarted ,
      Date dateFinished ) {
    DLog.debug(
        lctx ,
        "Creating file beans with : clientType = "
            + ClientType.toString( clientType ) + " , masterClientId = "
            + masterClientId + " , findUserId = " + findUserId
            + " , findActionText = " + findActionText + " , dateStarted = "
            + dateStarted + " , dateFinished = " + dateFinished );
    dao = new UserLogViewFileDAO( clientType , masterClientId , findUserId ,
        findActionText , dateStarted , dateFinished );
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public String filePathWithoutExtension() {
    String strFileName = null;

    // add date created time
    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd.HHmmss" );
    String dateTimeNowStr = sdf.format( now );

    // random alphanumeric 5 digit
    String randAlphanum5digits = RandomStringUtils.randomAlphanumeric( 5 );

    // compose str file name
    strFileName = "user-log-activities-report-".concat( dateTimeNowStr )
        .concat( "-" ).concat( randAlphanum5digits );

    return strFileName;
  }

  public long exportToFile( String reportFileNameWithoutExt ) {
    long totalRecords = 0;
    long deltaTime = System.currentTimeMillis();
    totalRecords = dao.exportToFile( reportFileNameWithoutExt );
    deltaTime = System.currentTimeMillis() - deltaTime;
    DLog.debug( lctx , "Successfully export to file , total records = "
        + totalRecords + " , take " + deltaTime + " ms " );
    return totalRecords;
  }

}
