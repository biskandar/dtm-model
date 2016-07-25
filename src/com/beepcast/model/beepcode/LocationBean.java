package com.beepcast.model.beepcode;

import java.io.IOException;
import java.io.Serializable;

/*******************************************************************************
 * Location Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class LocationBean implements Serializable {

  private long locationID;
  private String location;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public LocationBean() {
  }

  /*****************************************************************************
   * Set location id.
   ****************************************************************************/
  public void setLocationID( long locationID ) {
    this.locationID = locationID;
  }

  /**
   * Get location ID.
   */
  public long getLocationID() {
    return locationID;
  }

  /*****************************************************************************
   * Set location name.
   ****************************************************************************/
  public void setLocation( String location ) {
    this.location = location;
  }

  /**
   * Get location name.
   */
  public String getLocation() {
    return location;
  }

  /*****************************************************************************
   * Insert location record.
   * <p>
   * 
   * @return location ID
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public long insert() throws IOException {
    return new LocationDAO().insert( this );
  }

  /*****************************************************************************
   * Select location record.
   * <p>
   * 
   * @param locationID
   * @return LocationBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public LocationBean select( long locationID ) throws IOException {
    return new LocationDAO().select( locationID );
  }

  /*****************************************************************************
   * Select location record.
   * <p>
   * 
   * @param location
   * @return LocationBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public LocationBean select( String location ) throws IOException {
    return new LocationDAO().select( location );
  }

} // eof
