package com.beepcast.model.event;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.beepcast.util.Util;

/*******************************************************************************
 * Catagories Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class CatagoriesBean implements Serializable {

  private Vector catagoryNames;
  private Hashtable catagories;
  private String newCatagoryName;
  private Exception _exception;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public CatagoriesBean() {
  }

  /*****************************************************************************
   * Set catagory names.
   ****************************************************************************/
  public void setCatagoryNames( Vector catagoryNames ) {
    this.catagoryNames = catagoryNames;
  }

  /**
   * Get catagory names.
   */
  public Vector getCatagoryNames() {
    if ( catagoryNames == null ) {
      catagoryNames = new Vector( 100 , 100 );
      catagories = getCatagories();
      if ( catagories != null ) {
        try {
          Enumeration num = catagories.elements();
          while ( num.hasMoreElements() ) {
            CatagoryBean catagory = (CatagoryBean) num.nextElement();
            catagoryNames.addElement( catagory.getCatagoryName() );
          }
          Util.sortVector( catagoryNames );
        } catch ( Exception e ) {
        }
      }
    }
    return catagoryNames;
  }

  /*****************************************************************************
   * Set catagories.
   ****************************************************************************/
  public void setCatagories( Hashtable catagories ) {
    this.catagories = catagories;
  }

  /**
   * Get catagories.
   */
  public Hashtable getCatagories() {
    if ( catagories == null ) {
      try {
        catagories = new CatagoryDAO().select();
      } catch ( Exception e ) {
        _exception = e;
      }
    }
    return catagories;
  }

  /*****************************************************************************
   * Set new catagory name.
   ****************************************************************************/
  public void setNewCatagoryName( String newCatagoryName ) {
    this.newCatagoryName = newCatagoryName;
    catagories = null;
    catagoryNames = null;
  }

  /**
   * Get new catagory name.
   */
  public String getNewCatagoryName() {
    String temp = newCatagoryName;
    newCatagoryName = null;
    return temp;
  }

  /*****************************************************************************
   * Set exception.
   ****************************************************************************/
  public void setException( Exception _exception ) {
    this._exception = _exception;
  }

  /**
   * Get exception.
   */
  public Exception getException() {
    return _exception;
  }

}
