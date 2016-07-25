package com.beepcast.service.email;

public class EmailBean {

  private String emailId;

  private String to;
  private String toList[];
  private String from;
  private String fromName;
  private String subject = "";
  private String body = "";
  private String attachment;
  private String bcc;
  private String bccList[];

  // email error properties

  private int numRetries;
  private String errorMessage;
  private long emailErrorID;

  public EmailBean() {
    numRetries = 0;
    emailErrorID = 0L;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId( String emailId ) {
    this.emailId = emailId;
  }

  public void setTo( String to ) {
    this.to = to;
    setToList( EmailAddressFinder.findEmailAddressesToArray( to ) );
  }

  public String getTo() {
    return to;
  }

  public void setToList( String toList[] ) {
    this.toList = toList;
  }

  public String[] getToList() {
    return toList;
  }

  public void setFrom( String from ) {
    this.from = from;
  }

  public String getFrom() {
    return from;
  }

  public String getFromName() {
    return fromName;
  }

  public void setFromName( String fromName ) {
    this.fromName = fromName;
  }

  public void setSubject( String subject ) {
    this.subject = subject;
  }

  public String getSubject() {
    return subject;
  }

  public void setBody( String body ) {
    this.body = body;
  }

  public String getBody() {
    return body;
  }

  public void setAttachment( String attachment ) {
    this.attachment = attachment;
  }

  public String getAttachment() {
    return attachment;
  }

  public void setBcc( String bcc ) {
    this.bcc = bcc;
    String temp[] = new String[1];
    temp[0] = bcc;
    setBccList( temp );
  }

  public String getBcc() {
    return bcc;
  }

  public void setBccList( String bccList[] ) {
    this.bccList = bccList;
  }

  public String[] getBccList() {
    return bccList;
  }

  public void setNumRetries( int numRetries ) {
    this.numRetries = numRetries;
  }

  public int getNumRetries() {
    return numRetries;
  }

  public void setErrorMessage( String errorMessage ) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setEmailErrorID( long emailErrorID ) {
    this.emailErrorID = emailErrorID;
  }

  public long getEmailErrorID() {
    return emailErrorID;
  }

}
