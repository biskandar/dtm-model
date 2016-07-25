package com.beepcast.model.event;

import org.apache.commons.lang.StringEscapeUtils;

public class EventEmailBean {

  private int id;
  private int eventId;
  private int processStep;
  private String emailClob;

  public EventEmailBean() {

  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public int getEventId() {
    return eventId;
  }

  public void setEventId( int eventId ) {
    this.eventId = eventId;
  }

  public int getProcessStep() {
    return processStep;
  }

  public void setProcessStep( int processStep ) {
    this.processStep = processStep;
  }

  public String getEmailClob() {
    return emailClob;
  }

  public void setEmailClob( String emailClob ) {
    this.emailClob = emailClob;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "EventEmailBean ( " + "id = " + this.id + TAB + "eventId = "
        + this.eventId + TAB + "processStep = " + this.processStep + TAB
        + "emailClob = " + StringEscapeUtils.escapeJava( this.emailClob ) + TAB
        + " )";
    return retValue;
  }

}
