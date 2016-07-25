package com.beepcast.model.beepid;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

/*******************************************************************************
 * Beepid support class.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class BeepIDSupport {

  // cypher keys
  Hashtable cypherKeys = new Hashtable( 26 );
  {
    cypherKeys.put( "A" , "VZTVX_U" );
    cypherKeys.put( "B" , "CKMLT_W" );
    cypherKeys.put( "C" , "PEGAH_K" );
    cypherKeys.put( "D" , "HNPVQ_X" );
    cypherKeys.put( "E" , "SYZVL_T" );
    cypherKeys.put( "F" , "VNAGZ_U" );
    cypherKeys.put( "G" , "UMIKQ_L" );
    cypherKeys.put( "H" , "QXVZA_F" );
    cypherKeys.put( "I" , "XLDIK_R" );
    cypherKeys.put( "J" , "BWMCF_P" );
    cypherKeys.put( "K" , "AZMLJ_F" );
    cypherKeys.put( "L" , "GIUDV_G" );
    cypherKeys.put( "M" , "SWXAS_K" );
    cypherKeys.put( "N" , "IJHEL_Y" );
    cypherKeys.put( "O" , "UJPSA_L" );
    cypherKeys.put( "P" , "EWUFD_S" );
    cypherKeys.put( "Q" , "CKILO_P" );
    cypherKeys.put( "R" , "ZEZQX_I" );
    cypherKeys.put( "S" , "HDKOW_L" );
    cypherKeys.put( "T" , "GSKEO_G" );
    cypherKeys.put( "U" , "SERTM_D" );
    cypherKeys.put( "V" , "KZVNJ_A" );
    cypherKeys.put( "W" , "ONLZL_P" );
    cypherKeys.put( "X" , "EKJGL_I" );
    cypherKeys.put( "Y" , "IRYTX_V" );
    cypherKeys.put( "Z" , "SNJRF_G" );
  }

  // instance
  BeepIDDAO beepIDDAO = null;

  /*****************************************************************************
   * Constructor.
   * <p>
   ****************************************************************************/
  public BeepIDSupport() {
    beepIDDAO = new BeepIDDAO();
  }

  /*****************************************************************************
   * Assign next available BEEPid to the given phone number. If none are
   * available, generates 100 new BEEPid's.
   * <p>
   * 
   * @param phone
   * @return BeepID Bean.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public BeepIDBean assignBeepID( String phone ) throws IOException {
    return assignBeepID( phone , 0 );
  }

  /*****************************************************************************
   * Assign next available BEEPid to the given phone number.
   * 
   * @param phone
   * @param clientID
   *          Client owner of BEEPid, or 0 if personal BEEPid.
   * @return BeepID Bean.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public BeepIDBean assignBeepID( String phone , long clientID )
      throws IOException {

    BeepIDBean beepIDBean = selectNextAvailableBeepID();
    beepIDBean.setPhone( phone );
    beepIDBean.setClientID( clientID );
    beepIDBean.update();

    return beepIDBean;

  } // assignBeepID()

  /*****************************************************************************
   * Get next available BEEPid. If none are available, generates 100 new
   * BEEPid's.
   * <p>
   * 
   * @returns Next availabel BEEPid bean.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public BeepIDBean selectNextAvailableBeepID() throws IOException {

    BeepIDBean beepIDBean = beepIDDAO.selectNextAvailableBeepID();
    if ( beepIDBean == null ) {
      BeepIDBean beepIDBeans[] = generateBeepIDs( 100 );
      beepIDBean = beepIDBeans[0];
    }
    return beepIDBean;

  } // selectNextAvailableBeepID()

  /*****************************************************************************
   * Update last hit date.
   ****************************************************************************/
  public void updateLastHitDate( String beepID ) throws IOException {
    BeepIDBean beepIDBean = new BeepIDBean().select( beepID , true );
    beepIDBean.setLastHitDate( new Date() );
    beepIDBean.update();
  }

  /*****************************************************************************
   * Generate a set of BEEPids.
   * <p>
   * 
   * @param numBeepIDs
   *          The number of beepIDs to generate.
   * @return BeepIDBean[]
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public BeepIDBean[] generateBeepIDs( int numBeepIDs ) throws IOException {

    BeepIDBean beepIDs[] = new BeepIDBean[numBeepIDs];

    /*------------------------
      read last plain text beep id from db
    ------------------------*/
    String lastPlainText = readLastPlainText();

    /*------------------------        
      for each beep id ...
    ------------------------*/
    for ( int i = 0 ; i < beepIDs.length ; i++ ) {

      /*-------------------------
        generate new beep id
      -------------------------*/
      char c[] = lastPlainText.toCharArray();
      boolean foundLast[] = new boolean[1];
      BeepIDBean beepIDBean = generateBeepID( c , 0 , lastPlainText , foundLast );
      if ( beepIDBean == null )
        throw new IOException( "Last BEEPid generated." );

      /*-------------------------
        insert new beep id into db
      -------------------------*/
      if ( beepIDBean.insert() == -1 )
        i--; // beep id already exists
      else
        beepIDs[i] = new BeepIDBean().select( beepIDBean.getBeepID() , true );
      lastPlainText = swap( beepIDBean.getPlainText() , 5 , 6 );
    }

    /*-------------------------
      update last plain text in db, 
      and return beepIDs
    -------------------------*/
    updateLastPlainText( lastPlainText );
    return beepIDs;

  } // generateBeepIDs()

  /*****************************************************************************
   * Generate new BEEPid, recursively.
   * <p>
   * 
   * @param c
   *          A work in process that each recursion updates.
   * @param i
   *          The current index into c.
   * @param lastPlainText
   *          The last plain text used to generate a BEEPid.
   * @param foundLast
   *          A way for each recursion to know if the last plain text has been
   *          found.
   * @return A bean containing the new BEEPid, or null if not generated.
   ****************************************************************************/
  private BeepIDBean generateBeepID( char c[] , int i , String lastPlainText ,
      boolean foundLast[] ) throws IOException {

    BeepIDBean beepIDBean = null;

    /*--------------------------
      for each letter ...
    --------------------------*/
    for ( ; c[i] <= 'Z' ; c[i]++ ) {

      /*----------------------------
        recurse through letters
      ----------------------------*/
      if ( i < c.length - 1 ) {
        beepIDBean = generateBeepID( c , i + 1 , lastPlainText , foundLast );
        if ( beepIDBean != null ) // new beepID is generated
          return beepIDBean; // return recursively
      }

      /*----------------------------
        if past last plain text,
        encrypt next plain text
      ----------------------------*/
      else {
        String plainText = new String( c );
        if ( foundLast[0] ) {
          plainText = swap( plainText , 5 , 6 ); // move lsb to a2
          beepIDBean = encryptBeepID( plainText );
          if ( beepIDBean != null )
            return beepIDBean; // return recursively
        } else if ( plainText.equals( lastPlainText ) )
          foundLast[0] = true;
      }
    }

    /*----------------------------
      reset this char position
    ----------------------------*/
    c[i] = 'A';
    return beepIDBean;

  } // generateBeepID()

  /*****************************************************************************
   * Encrypt BEEPid
   * <p>
   * .
   * 
   * @param plainText
   *          The starting point for encryption.
   * @return Encrypted BEEPid, or null if cannot encrypt (y,z)
   ****************************************************************************/
  private BeepIDBean encryptBeepID( String plainText ) throws IOException {

    /*--------------------------
      first level a1 validation
    --------------------------*/
    String a1 = plainText.substring( 6 , 7 );
    if ( "AIQY".indexOf( a1 ) == -1 )
      return null;

    /*--------------------------
      get cypher key
    --------------------------*/
    String a2 = plainText.substring( 5 , 6 );
    String cypherKey = (String) cypherKeys.get( a2 );

    /*--------------------------
      add cypher key to create cypher text
    --------------------------*/
    String cypherText = "";
    for ( int i = 0 ; i < 5 ; i++ ) {
      char a = (char) ( plainText.charAt( i ) + 1 );
      char b = (char) ( cypherKey.charAt( i ) + 1 );
      char c = (char) ( ( a + b ) % 26 );
      c = ( c == 0 ) ? 'Z' : (char) ( 'A' + ( c - 1 ) );
      cypherText += new Character( c ).toString();
    }

    /*--------------------------
      add a2 to cypher text
    --------------------------*/
    cypherText += a2;

    /*--------------------------
      calculate checksum
    --------------------------*/
    int sum = 0;
    for ( int i = 0 ; i < cypherText.length() ; i++ )
      sum += ( ( plainText.length() - i ) * ( ( cypherText.charAt( i ) - 'A' ) + 1 ) );
    int _checksum = 8 - ( sum % 8 );

    /*--------------------------
      second level a1 validation
    --------------------------*/
    if ( _checksum > 2 && a1.equals( "Y" ) )
      return null;

    /*--------------------------
      map a1 into the checksum
    --------------------------*/
    char checksumLetter = (char) ( a1.charAt( 0 ) + _checksum );

    /*--------------------------
      encrypt the checksum
    --------------------------*/
    char b1 = (char) ( cypherKey.charAt( 6 ) + 1 );
    char c1 = checksumLetter;
    char cs = (char) ( ( b1 + c1 ) % 26 );
    cs = ( cs == 0 ) ? 'Z' : (char) ( 'A' + ( cs - 1 ) );
    checksumLetter = cs;

    /*--------------------------
      add checksum to cypher text
    --------------------------*/
    a1 = new Character( checksumLetter ).toString();
    cypherText += a1;

    /*--------------------------
      scramble the cypher text
    --------------------------*/
    int numShifts = ( ( a1.charAt( 0 ) - 'A' ) + 1 )
        * ( ( a2.charAt( 0 ) - 'A' ) + 1 );
    numShifts %= 10;
    int j = 3;
    for ( int i = 0 ; i < numShifts ; i++ ) {
      cypherText = swap( cypherText , j , j + 1 );
      j = ( j == 0 ) ? 3 : j - 1;
    }

    /*--------------------------
      check if beep id already exists
    --------------------------*/
    BeepIDBean beepIDBean = new BeepIDBean().select( cypherText , true );
    if ( beepIDBean != null )
      return beepIDBean;

    /*--------------------------
      validate by decyphering
    --------------------------*/
    String decypheredText = decypherBeepID( cypherText );
    if ( decypheredText == null || !decypheredText.equals( plainText ) )
      throw new IOException( "Failed to decypher " + cypherText + " to "
          + plainText + ". Got [" + decypheredText + "] instead." );

    /*--------------------------
      build and return beep id bean
    --------------------------*/
    beepIDBean = new BeepIDBean();
    beepIDBean.setBeepID( cypherText );
    beepIDBean.setPlainText( plainText );
    beepIDBean.setPhone( "" );
    return beepIDBean;

  } // encryptBeepID()

  /*****************************************************************************
   * Decypher BEEPid
   * <p>
   * .
   * 
   * @param cypherText
   *          The BEEPid to be decyphered.
   * @return The plain text version of the BEEPid.
   ****************************************************************************/
  private String decypherBeepID( String cypherText ) {

    /*--------------------------
      get cypher key
    --------------------------*/
    String a2 = cypherText.substring( 5 , 6 );
    String cypherKey = (String) cypherKeys.get( a2 );

    /*--------------------------
      (un)scramble the cypher text
    --------------------------*/
    String a1 = cypherText.substring( 6 , 7 );
    int numShifts = ( ( a1.charAt( 0 ) - 'A' ) + 1 )
        * ( ( a2.charAt( 0 ) - 'A' ) + 1 );
    numShifts %= 10;
    int j = 0;
    for ( int i = 0 ; i < numShifts ; i++ )
      j = ( j == 0 ) ? 3 : j - 1;
    for ( int i = 0 ; i < numShifts ; i++ ) {
      cypherText = swap( cypherText , j , j + 1 );
      j = ( j == 3 ) ? 0 : j + 1;
    }

    /*--------------------------
      (de)crypt the checksum
    --------------------------*/
    int b1 = cypherKey.charAt( 6 ) + 1;
    int cs = ( ( a1.charAt( 0 ) + 1 ) - b1 ) % 26;
    cs = ( cs <= 0 ) ? cs + 26 : cs;
    int _checksum = cs % 8;
    a1 = new Character( (char) ( ( cs + 'A' ) - 1 ) ).toString();

    /*--------------------------
      validate the checksum
    --------------------------*/
    int sum = 0;
    for ( int i = 0 ; i < cypherText.length() - 1 ; i++ )
      sum += ( ( cypherText.length() - i ) * ( ( cypherText.charAt( i ) - 'A' ) + 1 ) );
    if ( ( ( sum + _checksum ) % 8 ) != 0 )
      return null;

    /*--------------------------
      subtrack cypher key to create plain text
    --------------------------*/
    String plainText = "";
    for ( int i = 0 ; i < 5 ; i++ ) {
      int c = cypherText.charAt( i ) + 1;
      int b = cypherKey.charAt( i ) + 1;
      int a = ( c - b ) % 26;
      a = ( a <= 0 ) ? a + 26 : a;
      plainText += new Character( (char) ( ( a + 'A' ) - 1 ) ).toString();
    }

    /*--------------------------
      (un)map a1 from the checksum
    --------------------------*/
    a1 = new Character(
        (char) ( ( ( ( a1.charAt( 0 ) - 'A' ) / 8 ) * 8 ) + 'A' ) ).toString();

    /*--------------------------
      complete plain text
    --------------------------*/
    plainText += a2 + a1;

    // success
    return plainText;

  } // decypherBeepID()

  /*****************************************************************************
   * Swap to character positions within a string
   * <p>
   * .
   * 
   * @param str
   *          The string to be modified.
   * @param p1
   *          String position 1.
   * @param p2
   *          String position 2.
   * @return Encrypted BEEPid, or null if cannot encrypt (y,z)
   ****************************************************************************/
  private String swap( String str , int p1 , int p2 ) {

    char c[] = str.toCharArray();
    char temp = c[p1];
    c[p1] = c[p2];
    c[p2] = temp;
    return new String( c );

  } // swap()

  /*****************************************************************************
   * Read last plain text beep id generated.
   * 
   * @return last plain text used to generate a beep id.
   ****************************************************************************/
  private String readLastPlainText() throws IOException {
    String lastPlainText = beepIDDAO.readLastPlainText();
    if ( lastPlainText == null )
      lastPlainText = "AZZZZZZ";
    return lastPlainText;
  }

  /*****************************************************************************
   * Update last plain text used to generate beep id generated.
   * <p>
   * 
   * @param lastPlainText
   ****************************************************************************/
  private void updateLastPlainText( String lastPlainText ) throws IOException {
    beepIDDAO.updateLastPlainText( lastPlainText );
  }

} // eof
