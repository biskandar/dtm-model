package com.beepcast.model.gateway;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.beepcast.model.transaction.MessageType;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GatewayLogService {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "GatewayLog" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private GatewayLogDAO dao;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public GatewayLogService() {
    dao = new GatewayLogDAO();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insertIncomingMessage( String messageId , String messageType ,
      int messageCount , int eventId , String provider , double debitAmount ,
      Date dateTm , String phoneNumber , int phoneCountryId ,
      String destinationNumber , String messageContent , String messageStatus ) {
    boolean result = false;

    // validate must be params
    if ( ( phoneNumber == null ) || ( phoneNumber.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to insert incoming message "
          + ", found zero phoneNumber" );
      return result;
    }
    if ( ( messageContent == null ) || ( messageContent.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to insert incoming message "
          + ", found zero message" );
      return result;
    }

    // validate not must be params
    messageId = ( messageId == null ) ? "" : messageId;
    messageType = ( messageType == null ) ? MessageType
        .messageTypeToString( MessageType.TEXT_TYPE ) : messageType;
    messageCount = ( messageCount < 1 ) ? 0 : messageCount;
    eventId = ( eventId < 1 ) ? 0 : eventId;
    provider = ( provider == null ) ? "" : provider;
    dateTm = ( dateTm == null ) ? new Date() : dateTm;
    destinationNumber = ( destinationNumber == null ) ? "" : destinationNumber;
    messageContent = ( messageContent == null ) ? "" : messageContent;
    messageStatus = ( messageStatus == null ) ? "" : messageStatus;

    // trying to insert
    result = dao.insertIncomingMessage( messageId , messageType , messageCount ,
        eventId , provider , debitAmount , dateTm , phoneNumber ,
        phoneCountryId , destinationNumber , messageContent , messageStatus );

    return result;
  }

  public boolean insertOutgoingMessage( String messageId , String messageType ,
      int messageCount , int eventId , int channelSessionId , String provider ,
      double debitAmount , Date dateTm , String phoneNumber ,
      int phoneCountryId , String originalNumber , String messageContent ,
      String messageStatus ) {
    boolean result = false;

    // validate must be params
    if ( ( phoneNumber == null ) || ( phoneNumber.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to insert outgoing message "
          + ", found zero phoneNumber" );
      return result;
    }
    if ( ( messageContent == null ) || ( messageContent.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to insert outgoing message "
          + ", found zero message" );
      return result;
    }

    // validate not must be params
    messageId = ( messageId == null ) ? "" : messageId;
    messageType = ( messageType == null ) ? MessageType
        .messageTypeToString( MessageType.TEXT_TYPE ) : messageType;
    messageCount = ( messageCount < 1 ) ? 0 : messageCount;
    eventId = ( eventId < 1 ) ? 0 : eventId;
    channelSessionId = ( channelSessionId < 1 ) ? 0 : channelSessionId;
    provider = ( provider == null ) ? "" : provider;
    dateTm = ( dateTm == null ) ? new Date() : dateTm;
    originalNumber = ( originalNumber == null ) ? "" : originalNumber;
    messageContent = ( messageContent == null ) ? "" : messageContent;
    messageStatus = ( messageStatus == null ) ? "" : messageStatus;

    // trying to insert
    result = dao.insertOutgoingMessage( messageId , messageType , messageCount ,
        eventId , channelSessionId , provider , debitAmount , dateTm ,
        phoneNumber , phoneCountryId , originalNumber , messageContent ,
        messageStatus );

    return result;
  }

  public boolean insert( GatewayLogBean bean ) {
    return dao.insert( bean );
  }

  public GatewayLogBean selectByProviderAndExternalMessageId(
      String providerId , String externalMessageId ) {
    GatewayLogBean gatewayLogBean = null;
    if ( StringUtils.isBlank( providerId ) ) {
      DLog.warning( lctx , "Failed to select gateway log bean "
          + ", found empty provider id" );
      return gatewayLogBean;
    }
    if ( StringUtils.isBlank( externalMessageId ) ) {
      DLog.warning( lctx , "Failed to select gateway log bean "
          + ", found empty external message id" );
      return gatewayLogBean;
    }
    gatewayLogBean = dao.selectByProviderAndExtMsgId( providerId ,
        externalMessageId );
    return gatewayLogBean;
  }

  public GatewayLogBean[] select( String whereCriteria ) {
    return dao.select( whereCriteria );
  }

  public long getMaxLogID() {
    return dao.getMaxLogID();
  }

  public boolean update( GatewayLogBean bean ) {
    return dao.update( bean );
  }

  public boolean update( String setCriteria , String whereCriteria ) {
    return dao.update( setCriteria , whereCriteria );
  }

  public boolean delete( GatewayLogBean bean ) {
    return dao.delete( bean );
  }

  public int totalGatewayXipmeRecordsByChannelSessionId( int channelSessionId ,
      String xipmeMasterCode ) {
    int totalRecords = 0;
    if ( channelSessionId < 1 ) {
      DLog.warning( lctx , "Failed to calculate total gateway xipme "
          + "records , found zero channel session id" );
      return totalRecords;
    }
    totalRecords = dao.totalGatewayXipmeRecordsByChannelSessionId(
        channelSessionId , xipmeMasterCode );
    return totalRecords;
  }

  public int totalGatewayXipmeHitRecordsByChannelSessionId(
      int channelSessionId , String xipmeMasterCode ) {
    int totalRecords = 0;
    if ( channelSessionId < 1 ) {
      DLog.warning( lctx , "Failed to calculate total gateway xipme "
          + "hit records , found zero channel session id" );
      return totalRecords;
    }
    totalRecords = dao.totalGatewayXipmeHitRecordsByChannelSessionId(
        channelSessionId , xipmeMasterCode );
    return totalRecords;
  }

  public int totalGatewayXipmeVisitRecordsByChannelSessionId(
      int channelSessionId , String xipmeMasterCode ) {
    int totalRecords = 0;
    if ( channelSessionId < 1 ) {
      DLog.warning( lctx , "Failed to calculate total gateway xipme "
          + "visit records , found zero channel session id" );
      return totalRecords;
    }
    totalRecords = dao.totalGatewayXipmeVisitRecordsByChannelSessionId(
        channelSessionId , xipmeMasterCode );
    return totalRecords;
  }

}
