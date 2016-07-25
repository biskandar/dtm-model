package com.beepcast.model.exchange;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/*******************************************************************************
 * Exchange Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class ExchangeBean implements Serializable {

  public final static String DEFAULT_MESSAGE = "Replace this with your new content. (Maximum 140 characters.)";

  // db fields
  private long exchangeID;
  private String nickname = "";
  private String password = "";
  private String message = DEFAULT_MESSAGE;
  private String sponsorCode = "";
  private Date lastHitDate = new Date();;
  private long hitCount;

  // non-db fields
  private String phone;
  private String params[];
  private String host = "localhost";

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public ExchangeBean() {
  }

  /*****************************************************************************
   * Constructor that sets beepadmin web server host.
   ****************************************************************************/
  public ExchangeBean( String access ) {
    if ( access.equalsIgnoreCase( "REMOTE" ) ) {
      try {
        // this.host = new HttpSupport().getBeepadminIP().trim();
      } catch ( Exception e ) {
      }
    }
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
   * Set nickname.
   ****************************************************************************/
  public void setNickname( String nickname ) {
    this.nickname = nickname;
  }

  /**
   * Get nickname.
   */
  public String getNickname() {
    return nickname;
  }

  /*****************************************************************************
   * Set password.
   ****************************************************************************/
  public void setPassword( String password ) {
    this.password = password;
  }

  /**
   * Get password.
   */
  public String getPassword() {
    return password;
  }

  /*****************************************************************************
   * Set passcode. (same as password)
   ****************************************************************************/
  public void setPasscode( String passcode ) {
    setPassword( passcode );
  }

  /**
   * Get passcode.
   */
  public String getPasscode() {
    return getPassword();
  }

  /*****************************************************************************
   * Set message.
   ****************************************************************************/
  public void setMessage( String message ) {
    this.message = message;
  }

  /**
   * Get message.
   */
  public String getMessage() {
    return message;
  }

  /*****************************************************************************
   * Set sponsor code.
   ****************************************************************************/
  public void setSponsorCode( String sponsorCode ) {
    this.sponsorCode = sponsorCode;
  }

  /**
   * Get sponsor code.
   */
  public String getSponsorCode() {
    return sponsorCode;
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
   * Set hit count.
   ****************************************************************************/
  public void setHitCount( long hitCount ) {
    this.hitCount = hitCount;
  }

  /**
   * Get hit count.
   */
  public long getHitCount() {
    return hitCount;
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
   * Set params.
   ****************************************************************************/
  public void setParams( String params[] ) {
    this.params = params;
  }

  /**
   * Get params.
   */
  public String[] getParams() {
    return params;
  }

  /*****************************************************************************
   * Set host. ip address of beepadmin server.
   ****************************************************************************/
  public void setHost( String host ) {
    this.host = host;
  }

  /**
   * Get host.
   */
  public String getHost() {
    return host;
  }

  /*****************************************************************************
   * Insert exchange record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert() throws IOException {
    new ExchangeDAO().insert( this );
  }

  /*****************************************************************************
   * Select exchange record.
   * <p>
   * 
   * @param nickname
   * @param password
   * @return ExchangeBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public ExchangeBean select( String nickname , String password )
      throws IOException {
    return new ExchangeDAO().select( nickname , password );
  }

  /*****************************************************************************
   * Select a Vector of passwords for the given nickname.
   * <p>
   * 
   * @param nickname
   * @return A Vector of passwords.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector selectPasswords( String nickname ) throws IOException {
    return new ExchangeDAO().selectPasswords( nickname );
  }

  /*****************************************************************************
   * Update exchange record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update() throws IOException {
    new ExchangeDAO().update( this );
  }

  /*****************************************************************************
   * Delete exchange record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete() throws IOException {
    new ExchangeDAO().delete( this );
  }

  /*****************************************************************************
   * Load exchange record via http service.
   * <p>
   * 
   * @param phone
   * @param nickname
   * @param passcode
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String load( String phone , String nickname , String passcode ) {
    this.phone = phone;
    this.nickname = nickname;
    this.password = passcode;
    return new ExchangeDAO().load( this );
  }

  /*****************************************************************************
   * Save exchange bean via http service.
   * <p>
   * 
   * @return 0 if record found, -1 if record not found
   ****************************************************************************/
  public int save() {
    return new ExchangeDAO().save( this );
  }

  /*****************************************************************************
   * Create exchange record via http service.
   * <p>
   * 
   * @param phone
   * @param nickname
   * @param passcode
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String create( String phone , String nickname , String passcode ) {
    this.phone = phone;
    this.nickname = nickname;
    this.password = passcode;
    return new ExchangeDAO().create( this );
  }

  /*****************************************************************************
   * Destroy exchange record via http service.
   * <p>
   * 
   * @param phone
   * @param nickname
   * @param passcode
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String destroy( String phone , String nickname , String passcode ) {
    this.phone = phone;
    this.nickname = nickname;
    this.password = passcode;
    return new ExchangeDAO().destroy( this );
  }

  /*****************************************************************************
   * Broadcast exchange record via http service.
   * <p>
   * 
   * @param phone
   * @param nickname
   * @param passcode
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String broadcast( String phone , String nickname , String passcode ) {
    this.phone = phone;
    this.nickname = nickname;
    this.password = passcode;
    return new ExchangeDAO().broadcast( this );
  }

  /*****************************************************************************
   * Get passwords via http service.
   * <p>
   * 
   * @param phone
   * @param nickname
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String getPasscodes( String phone , String nickname ) {
    this.phone = phone;
    this.nickname = nickname;
    return new ExchangeDAO().getPasswords( this );
  }

  /*****************************************************************************
   * Rename password via http service.
   * <p>
   * 
   * @param phone
   * @param nickname
   * @param oldPasscode
   * @param newPasscode
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String renamePasscode( String phone , String nickname ,
      String oldPasscode , String newPasscode ) {
    this.phone = phone;
    this.nickname = nickname;
    this.password = oldPasscode;
    return new ExchangeDAO().renamePasscode( this , newPasscode );
  }

} // eof
