package com.beepcast.model.client;

import java.io.IOException;
import java.io.Serializable;

/*******************************************************************************
 * Test Phone Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class TestPhoneBean implements Serializable {

  // properties
  private long clientID;
  private String phone;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public TestPhoneBean() {
  }

  /*****************************************************************************
   * Set client ID.
   ****************************************************************************/
  public void setClientID( long clientID ) {
    this.clientID = clientID;
  }

  /**
   * Get client ID.
   */
  public long getClientID() {
    return clientID;
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
   * Insert test phone record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert() throws IOException {
    new TestPhoneDAO().insert( this );
  }

  /*****************************************************************************
   * Select test phone record.
   * <p>
   * 
   * @param clientID
   * @param phone
   * @return TestPhoneBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public TestPhoneBean select( long clientID , String phone )
      throws IOException {
    return new TestPhoneDAO().select( clientID , phone );
  }

  /*****************************************************************************
   * Select test phone records for a given client ID.
   * <p>
   * 
   * @param clientID
   * @return Array of phone numbers, or null if none found
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public String[] select( long clientID ) throws IOException {
    return new TestPhoneDAO().select( clientID );
  }

  /*****************************************************************************
   * Delete test phone record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete() throws IOException {
    new TestPhoneDAO().delete( this );
  }

} // eof
