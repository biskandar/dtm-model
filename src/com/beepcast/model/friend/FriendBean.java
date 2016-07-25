package com.beepcast.model.friend;

import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

/*******************************************************************************
 * Friend Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class FriendBean implements Serializable {

  private String senderPhone;
  private String friendPhone;
  private long eventID = 0;
  private long exchangeID = 0;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public FriendBean() {
  }

  /*****************************************************************************
   * Set sender phone.
   ****************************************************************************/
  public void setSenderPhone( String senderPhone ) {
    this.senderPhone = senderPhone;
  }

  /**
   * Get sender phone.
   */
  public String getSenderPhone() {
    return senderPhone;
  }

  /*****************************************************************************
   * Set friend phone.
   ****************************************************************************/
  public void setFriendPhone( String friendPhone ) {
    this.friendPhone = friendPhone;
  }

  /**
   * Get friend phone.
   */
  public String getFriendPhone() {
    return friendPhone;
  }

  /*****************************************************************************
   * Set event ID.
   ****************************************************************************/
  public void setEventID( long eventID ) {
    this.eventID = eventID;
  }

  /**
   * Get event ID.
   */
  public long getEventID() {
    return eventID;
  }

  /*****************************************************************************
   * Set exchange id.
   ****************************************************************************/
  public void setExchangeID( long exchangeID ) {
    this.exchangeID = exchangeID;
  }

  /**
   * Get exchange id.
   */
  public long getExchangeID() {
    return exchangeID;
  }

  /*****************************************************************************
   * Insert friend record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert() throws IOException {
    new FriendDAO().insert( this );
  }

  /*****************************************************************************
   * Select a Vector of friend records.
   * <p>
   * 
   * @param criteria
   *          Example: "sender_phone='+6596690924'"
   * @return A Vector of FriendBean objects.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector select( String criteria ) throws IOException {
    return new FriendDAO().select( criteria );
  }

} // eof
