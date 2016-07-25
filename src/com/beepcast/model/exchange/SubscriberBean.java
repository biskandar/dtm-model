package com.beepcast.model.exchange;

import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

/*******************************************************************************
 * Subscriber Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class SubscriberBean implements Serializable {

  private String phone;
  private long exchangeID;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public SubscriberBean() {
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
   * Insert subscriber record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert() throws IOException {
    new SubscriberDAO().insert( this );
  }

  /*****************************************************************************
   * Select subscriber record.
   * <p>
   * 
   * @param phone
   * @param exchangeID
   * @return SubscriberBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public SubscriberBean select( String phone , long exchangeID )
      throws IOException {
    return new SubscriberDAO().select( phone , exchangeID );
  }

  /*****************************************************************************
   * Select all phones for a given exchange id.
   * <p>
   * 
   * @return Vector of phones, null if none found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector select( long exchangeID ) throws IOException {
    return new SubscriberDAO().select( exchangeID );
  }

  /*****************************************************************************
   * Delete subscriber record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete() throws IOException {
    new SubscriberDAO().delete( this );
  }

} // eof
