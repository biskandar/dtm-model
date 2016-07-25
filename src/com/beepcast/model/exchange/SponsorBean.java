package com.beepcast.model.exchange;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/*******************************************************************************
 * Sponsor Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class SponsorBean implements Serializable {

  private long sponsorID; // primary key
  private String code = "";
  private Date lastHitDate;
  private long hitCount;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public SponsorBean() {
  }

  /*****************************************************************************
   * Set sponsor id.
   ****************************************************************************/
  public void setSponsorID( long sponsorID ) {
    this.sponsorID = sponsorID;
  }

  /**
   * Get sponsor ID.
   */
  public long getSponsorID() {
    return sponsorID;
  }

  /*****************************************************************************
   * Set code.
   ****************************************************************************/
  public void setCode( String code ) {
    this.code = code;
  }

  /**
   * Get code.
   */
  public String getCode() {
    return code;
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
   * Insert sponsor record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert() throws IOException {
    new SponsorDAO().insert( this );
  }

  /*****************************************************************************
   * Select sponsor record.
   * <p>
   * 
   * @param sponsorID
   * @return SponsorBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public SponsorBean select( long sponsorID ) throws IOException {
    return new SponsorDAO().select( sponsorID );
  }

  /*****************************************************************************
   * Select sponsor record.
   * <p>
   * 
   * @param code
   * @return SponsorBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public SponsorBean select( String code ) throws IOException {
    return new SponsorDAO().select( code );
  }

  /*****************************************************************************
   * Select all sponsor records.
   * <p>
   * 
   * @return Hashtable of SponsorBean, null if none found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Hashtable select() throws IOException {
    return new SponsorDAO().select();
  }

  /*****************************************************************************
   * Select sponsor codes.
   * <p>
   * 
   * @return All defined sponsor codes.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector selectCodes() throws IOException {
    return new SponsorDAO().selectCodes();
  }

  /*****************************************************************************
   * Update sponsor record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update() throws IOException {
    new SponsorDAO().update( this );
  }

  /*****************************************************************************
   * Delete sponsor record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete() throws IOException {
    new SponsorDAO().delete( this );
  }

} // eof
