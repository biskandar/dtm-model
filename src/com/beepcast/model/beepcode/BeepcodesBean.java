package com.beepcast.model.beepcode;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.beepcast.util.Util;

/*******************************************************************************
 * Beepcodes Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class BeepcodesBean implements Serializable {

  private Vector codes;
  private Hashtable beepcodes;
  private Exception _exception;
  private long eventID;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public BeepcodesBean() {
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
      beepcodes = getBeepcodes();
      if ( beepcodes != null ) {
        try {
          Enumeration num = beepcodes.elements();
          while ( num.hasMoreElements() ) {
            BeepcodeBean beepcode = (BeepcodeBean) num.nextElement();
            codes.addElement( beepcode.getCode() );
          }
          Util.sortVector( codes );
        } catch ( Exception e ) {
        }
      }
    }
    return codes;
  }

  /*****************************************************************************
   * Set beepcodes.
   ****************************************************************************/
  public void setBeepcodes( Hashtable beepcodes ) {
    this.beepcodes = beepcodes;
  }

  /**
   * Get beepcodes.
   */
  public Hashtable getBeepcodes() {
    if ( beepcodes == null ) {
      try {
        beepcodes = new BeepcodeDAO().selectEventCodes( eventID );
      } catch ( Exception e ) {
        _exception = e;
      }
    }
    return beepcodes;
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

  /*****************************************************************************
   * Set event id.
   ****************************************************************************/
  public void setEventID( long eventID ) {
    this.eventID = eventID;
    codes = null;
    beepcodes = null;
  }

  /**
   * Get event id.
   */
  public long getEventID() {
    return eventID;
  }

} // eof
