package com.beepcast.model.mobileUser;

import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

/*******************************************************************************
 * Interest Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class InterestBean implements Serializable {

  private String phone;
  private long catagoryID;

  // non-db fields
  private String host = "localhost";

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public InterestBean() {
  }

  /*****************************************************************************
   * Constructor that sets beepadmin web server host.
   ****************************************************************************/
  public InterestBean( String access ) {
    if ( access.equalsIgnoreCase( "REMOTE" ) ) {
      try {
        // this.host = new HttpSupport().getBeepadminIP().trim();
      } catch ( Exception e ) {
      }
    }
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
   * Set catagory id.
   ****************************************************************************/
  public void setCatagoryID( long catagoryID ) {
    this.catagoryID = catagoryID;
  }

  /**
   * Get catagory ID.
   */
  public long getCatagoryID() {
    return catagoryID;
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
   * Insert interest record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert() throws IOException {
    new InterestDAO().insert( this );
  }

  /*****************************************************************************
   * Select interest record for the given phone and catagoryID.
   * <p>
   * 
   * @param phone
   * @param catagoryID
   * @return InterestBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public InterestBean select( String phone , long catagoryID )
      throws IOException {
    return new InterestDAO().select( phone , catagoryID );
  }

  /*****************************************************************************
   * Select all interest records for the given phone.
   * <p>
   * 
   * @param phone
   * @return Vector of InterestBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector select( String phone ) throws IOException {
    return new InterestDAO().select( phone );
  }

  /*****************************************************************************
   * Delete all interest records for the given phone.
   * <p>
   * 
   * @param phone
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( String phone ) throws IOException {
    new InterestDAO().delete( phone );
  }

  /*****************************************************************************
   * Get interests via http service.
   * <p>
   * 
   * @param phone
   * @return List of interests, or "ERROR:" + error message
   ****************************************************************************/
  public String getInterests( String phone ) {
    this.phone = phone;
    return new InterestDAO().getInterests( this );
  }

  /*****************************************************************************
   * Set interests via http service.
   * <p>
   * 
   * @param phone
   * @param interestList
   * @return "OK" if success, or "ERROR:" + error message
   ****************************************************************************/
  public String setInterests( String phone , String interestList ) {
    this.phone = phone;
    return new InterestDAO().setInterests( this , interestList );
  }

} // eof
