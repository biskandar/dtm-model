package com.beepcast.model.cdr;

import java.util.HashMap;

import com.beepcast.common.cdr.CDRAdapterWriter;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class BeepcastCDR {

  static final DLogContext lctx = new SimpleContext( "BeepcastCDR" );

  private CDRAdapterWriter cdrAdapterWriter = null;

  public BeepcastCDR() {
    cdrAdapterWriter = new CDRAdapterWriter( "beepcastCDR" );
  }

  public void createTicket( String contentType , String contentDirection ,
      String providerId , String eventId , String phoneNumber ,
      String shortCode , String senderId , String intMessageId ,
      String extMessageId , String status , String message ) {
    HashMap hashmap = new HashMap();
    hashmap.put( "contentType" , contentType );
    hashmap.put( "contentDirection" , contentDirection );
    hashmap.put( "providerId" , providerId );
    hashmap.put( "eventId" , eventId );
    hashmap.put( "phoneNumber" , phoneNumber );
    hashmap.put( "shortCode" , shortCode );
    hashmap.put( "senderId" , senderId );
    hashmap.put( "intMessageId" , intMessageId );
    hashmap.put( "extMessageId" , extMessageId );
    hashmap.put( "status" , status );
    hashmap.put( "message" , message );
    int result = cdrAdapterWriter.write( hashmap );
    if ( result == 0 ) {
      DLog.debug( lctx , "Successfully write to the cdr file - " + intMessageId );
    } else {
      DLog.warning( lctx , "Failed to write the cdr file - " + intMessageId );
    }

  }

}
