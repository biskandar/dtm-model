package com.beepcast.service.ringtone;

import java.io.Serializable;

/*******************************************************************************
 * RingtoneBean Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class RingtoneBean implements Serializable {

  private String hexFile;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public RingtoneBean() {
  }

  /*****************************************************************************
   * Set hex file.
   ****************************************************************************/
  public void setHexFile( String hexFile ) {
    this.hexFile = hexFile;
  }

  /**
   * Get hex file.
   */
  public String getHexFile() {
    return hexFile;
  }

} // eof
