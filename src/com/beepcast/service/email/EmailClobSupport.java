package com.beepcast.service.email;

import com.beepcast.util.Base64;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EmailClobSupport {

  static final DLogContext lctx = new SimpleContext( "EmailClobSupport" );

  public static String emailBeanToEmailClob( EmailBean emailBean ) {
    String emailClob = null;
    if ( emailBean == null ) {
      DLog.warning( lctx , "Failed to encode email clob "
          + ", found null email bean" );
      return emailClob;
    }
    emailClob = composeToEmailClob( emailBean.getFrom() , emailBean.getTo() ,
        emailBean.getSubject() , emailBean.getBody() ,
        emailBean.getAttachment() );
    return emailClob;
  }

  public static String composeToEmailClob( String from , String to ,
      String subject , String body , String attachment ) {

    from = ( from == null ) ? "" : from.trim();
    to = ( to == null ) ? "" : to.trim();
    subject = ( subject == null ) ? "" : subject.trim();
    body = ( body == null ) ? "" : body.trim();
    attachment = ( attachment == null ) ? "" : attachment.trim();

    StringBuffer sbEmailClob = new StringBuffer();
    sbEmailClob.append( "[from-bs64]" );
    sbEmailClob.append( Base64.encodeString( from , false ) );
    sbEmailClob.append( "[to-bs64]" );
    sbEmailClob.append( Base64.encodeString( to , false ) );
    sbEmailClob.append( "[subject-bs64]" );
    sbEmailClob.append( Base64.encodeString( subject , false ) );
    sbEmailClob.append( "[body-bs64]" );
    sbEmailClob.append( Base64.encodeString( body , false ) );
    sbEmailClob.append( "[attach-bs64]" );
    sbEmailClob.append( Base64.encodeString( attachment , false ) );
    sbEmailClob.append( "[end-bs64]" );

    return sbEmailClob.toString();
  }

  public static EmailBean emailClobToEmailBean( String emailClob ) {
    EmailBean emailBean = null;
    if ( emailClob == null ) {
      DLog.warning( lctx , "Failed to decode email clob "
          + ", found null email club" );
      return emailBean;
    }
    try {
      emailBean = new EmailBean();
      emailBean.setFrom( readParamFromClob( emailClob , "from" , "to" ) );
      emailBean.setTo( readParamFromClob( emailClob , "to" , "subject" ) );
      emailBean
          .setSubject( readParamFromClob( emailClob , "subject" , "body" ) );
      emailBean.setBody( readParamFromClob( emailClob , "body" , "attach" ) );
      emailBean
          .setAttachment( readParamFromClob( emailClob , "attach" , "end" ) );
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to convert email clob to email bean , " + e );
    }
    return emailBean;
  }

  private static String readParamFromClob( String emailClob , String headerA ,
      String headerB ) {
    String param = null;

    if ( emailClob == null ) {
      return param;
    }
    if ( headerA == null ) {
      return param;
    }
    if ( headerB == null ) {
      return param;
    }

    boolean bs64 = false;

    String idx = "[" + headerA + "]";
    int p1 = emailClob.indexOf( idx );
    if ( p1 < 0 ) {
      idx = "[" + headerA + "-bs64]";
      p1 = emailClob.indexOf( idx );
      if ( p1 > -1 ) {
        bs64 = true;
      }
    }

    int p2 = emailClob.indexOf( "[" + headerB + "]" );
    if ( p2 < 0 ) {
      p2 = emailClob.indexOf( "[" + headerB + "-bs64]" );
    }

    if ( ( p1 != -1 ) && ( p2 != -1 ) ) {
      param = emailClob.substring( p1 + idx.length() , p2 ).trim();
    }

    if ( ( param != null ) && bs64 ) {
      param = Base64.decodeToString( param );
    }

    return param;
  }

}
