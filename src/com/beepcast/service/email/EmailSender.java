package com.beepcast.service.email;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.beepcast.onm.OnmApp;
import com.beepcast.onm.alert.AlertMessageFactory;
import com.beepcast.onm.alert.email.EmailConf;
import com.beepcast.onm.alert.email.EmailConfFactory;
import com.beepcast.onm.data.AlertMessage;
import com.beepcast.util.properties.GlobalEnvironment;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EmailSender {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "EmailSender" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private GlobalEnvironment globalEnv;
  private OnmApp onmApp;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public EmailSender() {
    globalEnv = GlobalEnvironment.getInstance();
    onmApp = OnmApp.getInstance();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean send( EmailBean emailBean ) {
    boolean result = false;
    try {

      // validate must be params

      if ( emailBean == null ) {
        DLog.warning( lctx , "Failed to send email , found null email bean" );
        return result;
      }

      // header log

      String headerLog = "[Email-" + emailBean.getEmailId() + "] ";

      // compose as alert message

      AlertMessage alertMessage = AlertMessageFactory.createAlertEmailMessage(
          null , StringUtils.join( emailBean.getToList() , ";" ) ,
          emailBean.getSubject() , emailBean.getBody() , 3 );

      // set same message id

      if ( !StringUtils.isBlank( emailBean.getEmailId() ) ) {
        DLog.debug( lctx ,
            headerLog + "Set alert message id : " + alertMessage.getMessageId()
                + " -> " + emailBean.getEmailId() );
        alertMessage.setMessageId( emailBean.getEmailId() );
      }

      // attach file if found any

      if ( !StringUtils.isBlank( emailBean.getAttachment() ) ) {
        DLog.debug(
            lctx ,
            headerLog + "Set alert message's param : "
                + AlertMessage.MSGHDR_ATTACHFILE + " = "
                + emailBean.getAttachment() );
        alertMessage.addMessageParam( AlertMessage.MSGHDR_ATTACHFILE ,
            emailBean.getAttachment() );
      }

      // prepare the email conf

      String nameFrom = emailBean.getFromName();
      if ( StringUtils.isBlank( nameFrom ) ) {
        nameFrom = emailBean.getFrom();
      }

      EmailConf emailConf = EmailConfFactory.createEmailConf(
          globalEnv.getProperty( "platform.email.protocol" ) ,
          globalEnv.getProperty( "platform.email.host" ) ,
          globalEnv.getProperty( "platform.email.port" ) ,
          globalEnv.getProperty( "platform.email.username" ) ,
          globalEnv.getProperty( "platform.email.password" ) ,
          emailBean.getFrom() , nameFrom );
      DLog.debug( lctx , headerLog + "Setup email conf : property = "
          + "platform.email.*" + " , addrFrom = " + emailConf.getAddrFrom()
          + " , nameFrom = " + emailConf.getNameFrom() );

      // log first

      DLog.debug(
          lctx ,
          headerLog + "Send an email message : " + emailBean.getFrom() + " -> "
              + Arrays.asList( emailBean.getToList() ) + " : subject = "
              + emailBean.getSubject() + " , attachment = "
              + emailBean.getAttachment() );

      // send alert message thru onm app

      result = onmApp.sendEmailAlert( alertMessage , emailConf );

    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to send email , " + e );
    }
    return result;
  }
  // ////////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ////////////////////////////////////////////////////////////////////////////

}
