package com.beepcast.service.progress;

import java.io.IOException;
import java.io.Serializable;

/*******************************************************************************
 * Progress Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class ProgressBean implements Serializable {

  private long progressID; // primary key
  private long minimum;
  private long maximum;
  private long value;
  private String errorMessage = "";

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public ProgressBean() {
  }

  /*****************************************************************************
   * Set progress ID.
   ****************************************************************************/
  public void setProgressID( long progressID ) {
    this.progressID = progressID;
  }

  /**
   * Get progress ID.
   */
  public long getProgressID() {
    return progressID;
  }

  /*****************************************************************************
   * Set minimum.
   ****************************************************************************/
  public void setMinimum( long minimum ) {
    this.minimum = minimum;
  }

  /**
   * Get minimum.
   */
  public long getMinimum() {
    return minimum;
  }

  /*****************************************************************************
   * Set maximum.
   ****************************************************************************/
  public void setMaximum( long maximum ) {
    this.maximum = maximum;
  }

  /**
   * Get maximum.
   */
  public long getMaximum() {
    return maximum;
  }

  /*****************************************************************************
   * Set value.
   ****************************************************************************/
  public void setValue( long value ) {
    this.value = value;
  }

  /**
   * Get value.
   */
  public long getValue() {
    return value;
  }

  /*****************************************************************************
   * Set error message.
   ****************************************************************************/
  public void setErrorMessage( String errorMessage ) {
    this.errorMessage = errorMessage;
  }

  /**
   * Get error message..
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /*****************************************************************************
   * Insert new progress record.
   * <p>
   * 
   * @return progressID
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public long insert() throws IOException {
    return new ProgressDAO().insert( this );
  }

  /*****************************************************************************
   * Select progress record.
   * <p>
   * 
   * @param progressID
   * @return ProgressBean, or null if not found
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public ProgressBean select( long progressID ) throws IOException {
    return new ProgressDAO().select( progressID );
  }

  /*****************************************************************************
   * Update progress record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update() throws IOException {
    new ProgressDAO().update( this );
  }

  /*****************************************************************************
   * Delete progress record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete() throws IOException {
    new ProgressDAO().delete( this );
  }

} // eof
