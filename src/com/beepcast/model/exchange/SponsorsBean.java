package com.beepcast.model.exchange;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.beepcast.util.Util;

/*******************************************************************************
 * Sponsors Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class SponsorsBean implements Serializable {

  private Vector codes;
  private Hashtable sponsors;
  private String newCode;
  private Exception _exception;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public SponsorsBean() {
  }

  /*****************************************************************************
   * Set codes.
   ****************************************************************************/
  public void setCodes( Vector codes ) {
    this.codes = codes;
  }

  /**
   * Get codes.
   */
  public Vector getCodes() {
    if ( codes == null ) {
      codes = new Vector( 100 , 100 );
      sponsors = getSponsors();
      if ( sponsors != null ) {
        try {
          Enumeration num = sponsors.elements();
          while ( num.hasMoreElements() ) {
            SponsorBean sponsor = (SponsorBean) num.nextElement();
            codes.addElement( sponsor.getCode() );
          }
          Util.sortVector( codes );
        } catch ( Exception e ) {
        }
      }
    }
    return codes;
  }

  /*****************************************************************************
   * Set sponsors.
   ****************************************************************************/
  public void setSponsors( Hashtable sponsors ) {
    this.sponsors = sponsors;
  }

  /**
   * Get sponsors.
   */
  public Hashtable getSponsors() {
    if ( sponsors == null ) {
      try {
        sponsors = new SponsorBean().select();
      } catch ( Exception e ) {
        _exception = e;
      }
    }
    return sponsors;
  }

  /*****************************************************************************
   * Set new code.
   ****************************************************************************/
  public void setNewCode( String newCode ) {
    this.newCode = newCode;
    sponsors = null;
    codes = null;
  }

  /**
   * Get new code.
   */
  public String getNewCode() {
    String temp = newCode;
    newCode = null;
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

} // eof
