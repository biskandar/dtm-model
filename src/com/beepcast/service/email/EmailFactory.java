package com.beepcast.service.email;

public class EmailFactory {

  public static EmailBean createEmailBean( String emailId , String to ,
      String toList[] , String from , String subject , String body ,
      String attachment , String bcc , String bccList[] ) {
    EmailBean emailBean = new EmailBean();
    emailBean.setEmailId( emailId );
    emailBean.setTo( to );
    emailBean.setToList( toList );
    emailBean.setFrom( from );
    emailBean.setSubject( subject );
    emailBean.setBody( body );
    emailBean.setAttachment( attachment );
    emailBean.setBcc( bcc );
    emailBean.setBccList( bccList );
    return emailBean;
  }

}
