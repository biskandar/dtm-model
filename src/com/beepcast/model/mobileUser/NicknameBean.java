package com.beepcast.model.mobileUser;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/*******************************************************************************
 * Nickname Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class NicknameBean implements Serializable {

  private long nicknameID;
  private String nickname;
  private String phone = "";
  private Date lastHitDate;
  private long hitCount;

  // non-db fields
  private String host = "localhost";

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public NicknameBean() {
  }

  /*****************************************************************************
   * Constructor that sets beepadmin web server host.
   ****************************************************************************/
  public NicknameBean( String access ) {
    if ( access.equalsIgnoreCase( "REMOTE" ) ) {
      try {
        // this.host = new HttpSupport().getBeepadminIP().trim();
      } catch ( Exception e ) {
      }
    }
  }

  /*****************************************************************************
   * Set nickname id.
   ****************************************************************************/
  public void setNicknameID( long nicknameID ) {
    this.nicknameID = nicknameID;
  }

  /**
   * Get nickname id.
   */
  public long getNicknameID() {
    return nicknameID;
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
   * Insert nickname record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert() throws IOException {
    new NicknameDAO().insert( this );
  }

  /*****************************************************************************
   * Select nickname record.
   * <p>
   * 
   * @param nickname
   * @return NicknameBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public NicknameBean select( String nickname ) throws IOException {
    return new NicknameDAO().select( nickname );
  }

  /*****************************************************************************
   * Select a Vector of nicknames for the given phone.
   * <p>
   * 
   * @param phone
   * @return A Vector of nicknames.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector selectNicknames( String phone ) throws IOException {
    return new NicknameDAO().selectNicknames( phone );
  }

  /*****************************************************************************
   * Update nickname record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update() throws IOException {
    new NicknameDAO().update( this );
  }

  /*****************************************************************************
   * Delete nickname record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete() throws IOException {
    new NicknameDAO().delete( this );
  }

  /*****************************************************************************
   * Load nickname record via http service.
   * <p>
   * 
   * @param phone
   * @param nickname
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String load( String phone , String nickname ) {
    this.phone = phone;
    this.nickname = nickname;
    return new NicknameDAO().load( this );
  }

  /*****************************************************************************
   * Get nicknames via http service.
   * <p>
   * 
   * @param phone
   * @return List of nicknames, or "ERROR:" + error message
   ****************************************************************************/
  public String getNicknames( String phone ) {
    this.phone = phone;
    return new NicknameDAO().getNicknames( this );
  }

  /*****************************************************************************
   * Create nickname record via http service.
   * <p>
   * 
   * @param phone
   * @param nickname
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String create( String phone , String nickname ) {
    this.phone = phone;
    this.nickname = nickname;
    return new NicknameDAO().create( this );
  }

  /*****************************************************************************
   * Destroy nickname record via http service.
   * <p>
   * 
   * @param phone
   * @param nickname
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String destroy( String phone , String nickname ) {
    this.phone = phone;
    this.nickname = nickname;
    return new NicknameDAO().destroy( this );
  }

  /*****************************************************************************
   * Rename password via http service.
   * <p>
   * 
   * @param phone
   * @param oldNickname
   * @param newNickname
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String renameNickname( String phone , String oldNickname ,
      String newNickname ) {
    this.phone = phone;
    this.nickname = oldNickname;
    return new NicknameDAO().renameNickname( this , newNickname );
  }

} // eof
