package com.beepcast.model.event;

import java.util.Calendar;
import java.util.Date;

public class EventBean {

  public final static int ADVANCED_TYPE = 0;
  public final static int BASIC_TYPE = 1;
  public final static int MENU_TYPE = 2;
  public final static int BEEPCARD_TYPE = 3;
  public final static int SURVEY_TYPE = 4;
  public final static int CLOSURE_TYPE = 5;
  public final static int CHANNEL_BASIC_TYPE = 6;
  public final static int CHANNEL_MENU_TYPE = 7;
  public final static int CHANNEL_ADVANCED_TYPE = 8;
  public final static int LUCKY_DRAW_TYPE = 9;
  public final static int PING_COUNT_TYPE = 10;
  public final static int TELL_A_FRIEND_TYPE = 11;
  public final static int CHANNEL_BROADCAST_TYPE = 12;
  public final static int RSVP_TYPE = 13;
  public final static int CHANNEL_RSVP_TYPE = 14;

  // 0000 0000 0000 0001
  public final static long SHOW_SURVEY_RESULTS = 1L;
  // 0000 0000 0000 0010
  public final static long SHOW_SURVEY_TOTAL = 2L;
  // 0000 0000 0000 0100
  public final static long LUCKY_DRAW_RANDOM_WINNER = 4L;
  // 0000 0000 0000 1000
  public final static long LUCKY_DRAW_MOST_PINGS_WINNER = 8L;
  // 0000 0000 0001 0000
  public final static long LUCKY_DRAW_RANDOM_WINNER_MULT = 16L;
  // 0000 0000 0010 0000
  public final static long AUTO_SUBSCRIBE = 32L;
  // 0000 0000 0100 0000
  public final static long BROADCAST_TO_SUBSCRIBER = 64L;
  // 0000 0000 1000 0000
  public final static long ONE_PING_ONLY = 128L;
  // 0000 0001 0000 0000
  public final static long BROADCAST_SUSPENDED_MODE = 256L;
  // 0000 0010 0000 0000
  public final static long SUSPENDED = 512L;
  // 1111 1101 1111 1111
  public final static long RESUMED = 65023L;

  // public final static long BROADCASTING = 1024L; // 0000 0100 0000 0000
  // public final static long IDLE = 64511L; // 1111 1011 1111 1111

  private long eventID;
  private String eventName;
  private long clientID;
  private long catagoryID;
  private Date startDate;
  private Date endDate;
  private Date remindDate;
  private String remindFreq;
  private int numCodes;
  private int codeLength;
  private String codes;
  private String comment;
  private String process;
  private int processType;
  private long pingCount;
  private double budget;
  private double usedBudget;
  private boolean channel;
  private boolean mobileMenuEnabled;
  private String mobileMenuName;
  private Date overbudgetDate;
  private boolean unsubscribeImmediate;
  private long bitFlags;
  private String mobileMenuBrandName;
  private String unsubscribeResponse;
  private String outgoingNumber;
  private String clientEventID;
  private String senderID;
  private boolean display;
  private boolean suspend;
  private boolean active;
  private Date dateInserted;
  private Date dateUpdated;

  // optional helper fields

  private int channelSessionId;

  public EventBean() {

    // set next 1 year
    Calendar next1years = Calendar.getInstance();
    next1years.add( Calendar.YEAR , 1 );
    startDate = new Date();
    endDate = next1years.getTime();

    // ???
    remindDate = next1years.getTime();

    mobileMenuBrandName = "";

    // init date
    active = true;
    display = true;
    dateInserted = new Date();
    dateUpdated = new Date();

  }

  public long getEventID() {
    return eventID;
  }

  public void setEventID( long eventID ) {
    this.eventID = eventID;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName( String eventName ) {
    this.eventName = eventName;
  }

  public long getClientID() {
    return clientID;
  }

  public void setClientID( long clientID ) {
    this.clientID = clientID;
  }

  public long getCatagoryID() {
    return catagoryID;
  }

  public void setCatagoryID( long catagoryID ) {
    this.catagoryID = catagoryID;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate( Date startDate ) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate( Date endDate ) {
    this.endDate = endDate;
  }

  public Date getRemindDate() {
    return remindDate;
  }

  public void setRemindDate( Date remindDate ) {
    this.remindDate = remindDate;
  }

  public String getRemindFreq() {
    return remindFreq;
  }

  public void setRemindFreq( String remindFreq ) {
    this.remindFreq = remindFreq;
  }

  public int getNumCodes() {
    return numCodes;
  }

  public void setNumCodes( int numCodes ) {
    this.numCodes = numCodes;
  }

  public int getCodeLength() {
    return codeLength;
  }

  public void setCodeLength( int codeLength ) {
    this.codeLength = codeLength;
  }

  public String getCodes() {
    return codes;
  }

  public void setCodes( String codes ) {
    this.codes = codes;
  }

  public String getComment() {
    return comment;
  }

  public void setComment( String comment ) {
    this.comment = comment;
  }

  public String getProcess() {
    return process;
  }

  public void setProcess( String process ) {
    this.process = process;
  }

  public int getProcessType() {
    return processType;
  }

  public void setProcessType( int processType ) {
    this.processType = processType;
  }

  public long getPingCount() {
    return pingCount;
  }

  public void setPingCount( long pingCount ) {
    this.pingCount = pingCount;
  }

  public double getBudget() {
    return budget;
  }

  public void setBudget( double budget ) {
    this.budget = budget;
  }

  public double getUsedBudget() {
    return usedBudget;
  }

  public void setUsedBudget( double usedBudget ) {
    this.usedBudget = usedBudget;
  }

  public boolean isChannel() {
    return channel;
  }

  public boolean getChannel() {
    return channel;
  }

  public void setChannel( boolean channel ) {
    this.channel = channel;
  }

  public boolean isMobileMenuEnabled() {
    return mobileMenuEnabled;
  }

  public boolean getMobileMenuEnabled() {
    return mobileMenuEnabled;
  }

  public void setMobileMenuEnabled( boolean mobileMenuEnabled ) {
    this.mobileMenuEnabled = mobileMenuEnabled;
  }

  public String getMobileMenuName() {
    return mobileMenuName;
  }

  public void setMobileMenuName( String mobileMenuName ) {
    this.mobileMenuName = mobileMenuName;
  }

  public Date getOverbudgetDate() {
    return overbudgetDate;
  }

  public void setOverbudgetDate( Date overbudgetDate ) {
    this.overbudgetDate = overbudgetDate;
  }

  public boolean isUnsubscribeImmediate() {
    return unsubscribeImmediate;
  }

  public boolean getUnsubscribeImmediate() {
    return unsubscribeImmediate;
  }

  public void setUnsubscribeImmediate( boolean unsubscribeImmediate ) {
    this.unsubscribeImmediate = unsubscribeImmediate;
  }

  public long getBitFlags() {
    return bitFlags;
  }

  public void setBitFlags( long bitFlags ) {
    this.bitFlags = bitFlags;
  }

  public String getMobileMenuBrandName() {
    return mobileMenuBrandName;
  }

  public void setMobileMenuBrandName( String mobileMenuBrandName ) {
    this.mobileMenuBrandName = mobileMenuBrandName;
  }

  public String getUnsubscribeResponse() {
    return unsubscribeResponse;
  }

  public void setUnsubscribeResponse( String unsubscribeResponse ) {
    this.unsubscribeResponse = unsubscribeResponse;
  }

  public String getOutgoingNumber() {
    return outgoingNumber;
  }

  public void setOutgoingNumber( String outgoingNumber ) {
    this.outgoingNumber = outgoingNumber;
  }

  public String getClientEventID() {
    return clientEventID;
  }

  public void setClientEventID( String clientEventID ) {
    this.clientEventID = clientEventID;
  }

  public String getSenderID() {
    return senderID;
  }

  public void setSenderID( String senderID ) {
    this.senderID = senderID;
  }

  public boolean isDisplay() {
    return display;
  }

  public boolean getDisplay() {
    return display;
  }

  public void setDisplay( boolean display ) {
    this.display = display;
  }

  public boolean isSuspend() {
    return suspend;
  }

  public void setSuspend( boolean suspend ) {
    this.suspend = suspend;
  }

  public boolean isActive() {
    return active;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive( boolean active ) {
    this.active = active;
  }

  public Date getDateInserted() {
    return dateInserted;
  }

  public void setDateInserted( Date dateInserted ) {
    this.dateInserted = dateInserted;
  }

  public Date getDateUpdated() {
    return dateUpdated;
  }

  public void setDateUpdated( Date dateUpdated ) {
    this.dateUpdated = dateUpdated;
  }

  public int getChannelSessionId() {
    return channelSessionId;
  }

  public void setChannelSessionId( int channelSessionId ) {
    this.channelSessionId = channelSessionId;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "EventBean ( " + "eventID = " + this.eventID + TAB
        + "eventName = " + this.eventName + TAB + "clientID = " + this.clientID
        + TAB + "catagoryID = " + this.catagoryID + TAB + "numCodes = "
        + this.numCodes + TAB + "codeLength = " + this.codeLength + TAB
        + "codes = " + this.codes + TAB + "channel = " + this.channel + TAB
        + "outgoingNumber = " + this.outgoingNumber + TAB + "senderID = "
        + this.senderID + TAB + " )";
    return retValue;
  }

}
