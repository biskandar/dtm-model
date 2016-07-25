package com.beepcast.model.global;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/*******************************************************************************
 * Global Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class GlobalBean implements Serializable {

  private String name;
  private String description;
  private String stringValue;
  private double numericValue;
  private boolean booleanValue;
  private Date dateValue;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public GlobalBean() {
  }

  /*****************************************************************************
   * Set name.
   ****************************************************************************/
  public void setName( String name ) {
    this.name = name;
  }

  /**
   * Get name.
   */
  public String getName() {
    return name;
  }

  /*****************************************************************************
   * Set description.
   ****************************************************************************/
  public void setDescription( String description ) {
    this.description = description;
  }

  /**
   * Get description.
   */
  public String getDescription() {
    return description;
  }

  /*****************************************************************************
   * Set string value.
   ****************************************************************************/
  public void setStringValue( String stringValue ) {
    this.stringValue = stringValue;
  }

  /**
   * Get string value.
   */
  public String getStringValue() {
    return stringValue;
  }

  /*****************************************************************************
   * Set numeric value.
   ****************************************************************************/
  public void setNumericValue( double numericValue ) {
    this.numericValue = numericValue;
  }

  /**
   * Get numeric value.
   */
  public double getNumericValue() {
    return numericValue;
  }

  /*****************************************************************************
   * Set boolean value.
   ****************************************************************************/
  public void setBooleanValue( boolean booleanValue ) {
    this.booleanValue = booleanValue;
  }

  /**
   * Get boolean value.
   */
  public boolean getBooleanValue() {
    return booleanValue;
  }

  /*****************************************************************************
   * Set date value.
   ****************************************************************************/
  public void setDateValue( Date dateValue ) {
    this.dateValue = dateValue;
  }

  /**
   * Get date value.
   */
  public Date getDateValue() {
    return dateValue;
  }

  /*****************************************************************************
   * Insert global record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert() throws IOException {
    new GlobalDAO().insert( this );
  }

  /*****************************************************************************
   * Select global record.
   * <p>
   * 
   * @param name
   * @return GlobalBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public GlobalBean select( String name ) throws IOException {
    return new GlobalDAO().select( name );
  }

  /*****************************************************************************
   * Update global record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update() throws IOException {
    new GlobalDAO().update( this );
  }

  /*****************************************************************************
   * Delete global record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete() throws IOException {
    new GlobalDAO().delete( this );
  }

} // eof
