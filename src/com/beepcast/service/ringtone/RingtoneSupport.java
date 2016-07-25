package com.beepcast.service.ringtone;

/*******************************************************************************
 * Ringtone support class.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class RingtoneSupport {

  /*****************************************************************************
   * Parses an ringtone clob and returns a RingtoneBean.
   * <p>
   * 
   * @param ringtoneClob
   *          The clob to be parsed.
   * @return A RingtoneBean.
   ****************************************************************************/
  public RingtoneBean parseRingtoneClob( String ringtoneClob ) {

    RingtoneBean ringtone = new RingtoneBean();

    /*-------------------------
      parse hex file
    -------------------------*/
    int p1 = ringtoneClob.indexOf( "[hexFile]" );
    int p2 = ringtoneClob.indexOf( "[end]" );
    if ( p1 != -1 && p2 != -1 )
      ringtone.setHexFile( ringtoneClob.substring( p1 + 9 , p2 ).trim() );

    // success
    return ringtone;

  } // parseRingtoneClob()

} // eof
