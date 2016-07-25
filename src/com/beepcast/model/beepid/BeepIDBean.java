package com.beepcast.model.beepid;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/*******************************************************************************
 * Beep ID Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class BeepIDBean implements Serializable {

  private String beepID;
  private String phone;
  private String plainText;
  private long clientID;
  private long eventID;
  private Date lastHitDate;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public BeepIDBean() {
  }

  /*****************************************************************************
   * Set beep id.
   ****************************************************************************/
  public void setBeepID( String beepID ) {
    this.beepID = beepID;
  }

  /**
   * Get beep id.
   */
  public String getBeepID() {
    return beepID;
  }

  /*****************************************************************************
   * Set phone.
   ****************************************************************************/
  public void setPhone( String phone ) {
    this.phone = phone;
  }

  /**
   * Get phone.
   */
  public String getPhone() {
    return phone;
  }

  /*****************************************************************************
   * Set plain text version of beep id.
   ****************************************************************************/
  public void setPlainText( String plainText ) {
    this.plainText = plainText;
  }

  /**
   * Get plain text version of beep id.
   */
  public String getPlainText() {
    return plainText;
  }

  /*****************************************************************************
   * Set client id.
   ****************************************************************************/
  public void setClientID( long clientID ) {
    this.clientID = clientID;
  }

  /**
   * Get client id.
   */
  public long getClientID() {
    return clientID;
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
   * Set last hit date.
   ****************************************************************************/
  public void setLastHitDate( java.util.Date lastHitDate ) {
    this.lastHitDate = lastHitDate;
  }

  /**
   * Get last hit date.
   */
  public java.util.Date getLastHitDate() {
    return lastHitDate;
  }

  /*****************************************************************************
   * Insert beepID record.
   * <p>
   * 
   * @return -1 if beepID already exists, -2 if phone already has beepID, 0 if
   *         ok.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public int insert() throws IOException {
    return new BeepIDDAO().insert( this );
  }

  /*****************************************************************************
   * Select beepID record.
   * <p>
   * 
   * @param phone
   * @return BeepIDBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public BeepIDBean select( String phone ) throws IOException {
    return new BeepIDDAO().select( phone );
  }

  /*****************************************************************************
   * Select beepID record.
   * <p>
   * 
   * @param beepID
   * @param anything
   *          if true or false, selects where beepID
   * @return BeepIDBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public BeepIDBean select( String beepID , boolean anything )
      throws IOException {
    return new BeepIDDAO().select( beepID , anything );
  }

  /*****************************************************************************
   * Select a Vector of beepid records.
   * <p>
   * 
   * @param criteria
   *          Example: "phone='+6596690924'"
   * @return A Vector of BeepIDBean objects.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector selectCriteria( String criteria ) throws IOException {
    return new BeepIDDAO().selectCriteria( criteria );
  }

  /*****************************************************************************
   * Update beepID record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update() throws IOException {
    new BeepIDDAO().update( this );
  }

  /*****************************************************************************
   * Delete beepID record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete() throws IOException {
    new BeepIDDAO().delete( this );
  }
}
