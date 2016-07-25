package com.beepcast.model.beepcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.beepcast.model.client.ClientBean;
import com.beepcast.model.client.ClientService;
import com.beepcast.model.event.EventBean;
import com.beepcast.model.event.EventService;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class BeepcodeSupport {

  static final DLogContext lctx = new SimpleContext( "BeepcodeSupport" );

  static final int NEXT_TOTAL_GENERATE_CODES = 100;

  private BeepcodeDAO dao;

  public BeepcodeSupport() {
    dao = new BeepcodeDAO();
  }

  public String allocateCodes( EventBean eventBean ) {
    String codes = null;

    if ( eventBean == null ) {
      DLog.warning( lctx , "Can not allocate codes "
          + ", found null event object" );
      return codes;
    }

    long eventID = eventBean.getEventID();
    long clientID = eventBean.getClientID();
    if ( ( eventID < 1 ) || ( clientID < 1 ) ) {
      DLog.warning( lctx , "Can not allocate codes "
          + ", found zero eventId and/or clientId" );
      return codes;
    }

    long catagoryID = eventBean.getCatagoryID();

    int codeLength = eventBean.getCodeLength();
    if ( codeLength < 1 ) {
      DLog.warning( lctx , "Can not allocate codes "
          + ", found zero event code length" );
      return codes;
    }

    // get current list of codes
    String strCurCodes = selectEventCodes( eventID );
    String[] arrCurCodes = StringUtils.split( strCurCodes , "," );
    int curNumCodes = ( arrCurCodes != null ) ? arrCurCodes.length : 0;

    // get total generate codes
    int desiredNumCodes = eventBean.getNumCodes();
    int generateNumCodes = desiredNumCodes - curNumCodes;
    DLog.debug( lctx , "Define current number codes = " + curNumCodes
        + " , desired number codes = " + desiredNumCodes
        + " , will generate number codes = " + generateNumCodes );

    // when no need to generate new codes , will use the current one
    if ( generateNumCodes < 1 ) {
      codes = strCurCodes;
      return codes;
    }

    // generate new codes if required
    int availableNumCodes = getAvailableQuantity( codeLength );
    if ( generateNumCodes > availableNumCodes ) {
      int totalGenerateCodes = generateNumCodes + NEXT_TOTAL_GENERATE_CODES;
      DLog.debug( lctx , "Found total available codes in the db "
          + "less than total generate codes "
          + ", perform auto generate new codes in the db , total = "
          + totalGenerateCodes );
      generateBeepcodes( codeLength , totalGenerateCodes );
    }

    // add new nodes
    DLog.debug( lctx , "Trying to get available " + generateNumCodes
        + " code(s) from beepcode table" );
    List newBeepcodeBeans = selectNextAvailableCode( codeLength ,
        generateNumCodes );
    Iterator iterNewBeepcodeBeans = newBeepcodeBeans.iterator();
    while ( iterNewBeepcodeBeans.hasNext() ) {
      BeepcodeBean beepcodeBean = (BeepcodeBean) iterNewBeepcodeBeans.next();
      if ( beepcodeBean == null ) {
        continue;
      }
      beepcodeBean.setEventID( eventID );
      beepcodeBean.setClientID( clientID );
      beepcodeBean.setCatagoryID( catagoryID );
      beepcodeBean.setLastHitDate( new Date() );
      beepcodeBean.setDescription( "" );
      beepcodeBean.setActive( true );
      if ( !dao.update( beepcodeBean ) ) {
        DLog.warning( lctx , "Failed to update the new beep code "
            + ", with code = " + beepcodeBean.getCode()
            + " , trying to remove it" );
        iterNewBeepcodeBeans.remove();
      }
    }

    // concatenate current nodes with new nodes
    List listCodes = new ArrayList();
    if ( curNumCodes > 0 ) {
      listCodes.addAll( Arrays.asList( arrCurCodes ) );
    }
    iterNewBeepcodeBeans = newBeepcodeBeans.iterator();
    while ( iterNewBeepcodeBeans.hasNext() ) {
      BeepcodeBean beepcodeBean = (BeepcodeBean) iterNewBeepcodeBeans.next();
      if ( beepcodeBean == null ) {
        continue;
      }
      listCodes.add( beepcodeBean.getCode() );
    }
    String[] arr = (String[]) listCodes.toArray( new String[0] );
    if ( arr != null ) {
      codes = StringUtils.join( arr , "," );
    }

    return codes;
  } // allocateCodes()

  public BeepcodeBean addPrimeCode( EventBean eventBean , String primeCode )
      throws IOException {

    EventService eventService = new EventService();

    long eventID = eventBean.getEventID();
    boolean recycled = false;

    // check if prime code already exists
    BeepcodeBean beepcodeBean = dao.select( primeCode );
    if ( beepcodeBean != null ) {
      if ( beepcodeBean.getActive() ) {
        EventBean beepcodeEvent = eventService.select( beepcodeBean
            .getEventID() );

        ClientService clientService = new ClientService();
        ClientBean beepcodeClient = clientService.select( beepcodeEvent
            .getClientID() );
        throw new IOException( "Prime Code [" + primeCode
            + "] is already owned by Client ["
            + beepcodeClient.getCompanyName() + "] under Event Name ["
            + beepcodeEvent.getEventName() + "]." );
      }
      recycled = true;
    }

    // validate new prime code
    if ( primeCode.length() < 3 || primeCode.length() > 10 )
      throw new IOException( "Prime Code [" + primeCode
          + "] length must be 3-10 chars." );
    if ( "who,what,when,where,how,yes,thanks,sure,great".indexOf( primeCode
        .toLowerCase() ) != -1 )
      throw new IOException( "Prime Code [" + primeCode + "] is not valid." );

    // create/update prime code
    if ( !recycled ) {
      beepcodeBean = new BeepcodeBean();
    }
    beepcodeBean.setCode( primeCode );
    beepcodeBean.setCodeLength( primeCode.length() );
    beepcodeBean.setEventID( eventID );
    beepcodeBean.setClientID( eventBean.getClientID() );
    beepcodeBean.setCatagoryID( eventBean.getCatagoryID() );
    beepcodeBean.setLastHitDate( new Date() );
    beepcodeBean.setDescription( "" );
    beepcodeBean.setActive( true );
    beepcodeBean.setReserved( true );
    if ( !recycled ) {
      dao.insert( beepcodeBean );
    } else {
      dao.update( beepcodeBean );
    }

    /*------------------------
      update event codes
    ------------------------*/
    String codes = selectEventCodes( eventID );
    StringTokenizer st = new StringTokenizer( codes , "," );
    int numCodes = st.countTokens();
    eventBean.setCodes( codes );
    eventBean.setNumCodes( numCodes );
    eventService.update( eventBean );

    // return beepcode of new or existing prime code
    return beepcodeBean;

  } // addPrimeCode()

  public String selectEventCodes( long eventID ) {
    String codes = "";
    Hashtable beepcodes = null;
    try {
      beepcodes = dao.selectEventCodes( eventID );
    } catch ( IOException e ) {
    }
    if ( beepcodes == null ) {
      return codes;
    }
    Enumeration num = beepcodes.elements();
    while ( num.hasMoreElements() ) {
      BeepcodeBean beepcode = (BeepcodeBean) num.nextElement();
      codes += beepcode.getCode() + ",";
    }
    if ( codes.length() > 0 ) { // remove trailing comma
      codes = codes.substring( 0 , codes.length() - 1 );
    }
    return codes;
  }

  public List selectNextAvailableCode( int codeLength , int limit ) {
    return dao.selectNextAvailableCode( codeLength , limit );
  }

  public int getMinLength() {
    return dao.readMinLength();
  }

  public int getMaxLength() {
    return dao.readMaxLength();
  }

  public int getQuantity( int codeLength ) {
    return dao.readQuantity( codeLength , BeepcodeDAO.TOTAL_QUANTITY );
  }

  public int getActiveQuantity( int codeLength ) {
    return dao.readQuantity( codeLength , BeepcodeDAO.ACTIVE_QUANTITY );
  }

  public int getInactiveQuantity( int codeLength ) {
    return dao.readQuantity( codeLength , BeepcodeDAO.INACTIVE_QUANTITY );
  }

  public int getReservedQuantity( int codeLength ) {
    return dao.readQuantity( codeLength , BeepcodeDAO.RESERVED_QUANTITY );
  }

  public int getAvailableQuantity( int codeLength ) {
    return dao.readQuantity( codeLength , BeepcodeDAO.AVAILABLE_QUANTITY );
  }

  public String getLastCode( int codeLength ) throws IOException {
    String lastCode = dao.readLastCode( codeLength );
    lastCode = ( lastCode == null ) ? "" : lastCode;
    return lastCode;
  }

  public boolean updateLastHitDate( String code ) throws IOException {
    return dao.updateLastHitDate( code , new Date() );
  }

  public Vector getClientReservedCodes( int codeLength , long clientID )
      throws IOException {
    return dao.getClientReservedCodes( codeLength , clientID );
  }

  public BeepcodeBean[] generateBeepcodes( int codeLength , int numCodes ) {
    BeepcodeBean[] beepcodes = null;

    if ( codeLength < 1 ) {
      DLog.warning( lctx , "Failed to generate beepcodes "
          + ", found zero code length" );
      return beepcodes;
    }

    if ( numCodes < 1 ) {
      DLog.warning( lctx , "Failed to generate beepcodes "
          + ", found zero num codes" );
      return beepcodes;
    }

    // generate array of beepcode
    beepcodes = new BeepcodeBean[numCodes];
    DLog.debug( lctx , "Created array of beepcode with size = " + numCodes );

    // read last code from db
    String lastCode = readLastCode( codeLength );
    DLog.debug( lctx , "Found last code = " + lastCode );

    // assign for each beepcode
    for ( int i = 0 ; i < beepcodes.length ; i++ ) {
      // generate new code
      char c[] = lastCode.toCharArray();
      boolean foundLast[] = new boolean[1];
      String code = generateCode( c , 0 , lastCode , foundLast );
      if ( code == null ) {
        DLog.warning( lctx , "Failed to generate code "
            + ", this is the last beepcode generated for length of "
            + codeLength );
        break;
      }
      // validate the code is exist or not
      if ( dao.select( code ) != null ) {
        i--;
      } else {
        beepcodes[i] = createBeepcode( code );
      }
      // re-update new last code
      lastCode = code;
    }

    // randomize array of beepcode
    Random random = new Random();
    BeepcodeBean beepcodeBeanTemp = null;
    for ( int i = 0 ; i < ( beepcodes.length / 2 ) ; i++ ) {
      int j = random.nextInt( beepcodes.length );
      beepcodeBeanTemp = beepcodes[j];
      beepcodes[j] = beepcodes[i];
      beepcodes[i] = beepcodeBeanTemp;
    }

    // insert into db for each beepcode
    for ( int i = 0 ; i < beepcodes.length ; i++ ) {
      if ( beepcodes[i] == null ) {
        continue;
      }
      if ( dao.insert( beepcodes[i] ) ) {
        DLog.debug(
            lctx ,
            "Successfully saved new beepcode , code = "
                + beepcodes[i].getCode() + " , eventId = "
                + beepcodes[i].getEventID() );
      } else {
        DLog.warning( lctx , "Failed to save new beepcode , code = "
            + beepcodes[i].getCode() );
      }
    }

    // re-update new last code in db
    dao.updateLastCode( lastCode );
    DLog.debug( lctx , "Updated new last code = " + lastCode );

    return beepcodes;
  }

  private String generateCode( char c[] , int i , String lastCode ,
      boolean foundLast[] ) {

    String code = null;

    // for each letter
    for ( ; c[i] <= 'Z' ; c[i]++ ) {

      // recurse through letters
      if ( i < c.length - 1 ) {
        code = generateCode( c , i + 1 , lastCode , foundLast );
        if ( code != null ) // new code is generated
          return code; // return recursively
      }

      // if past last code, check for consecutive key usage
      else {
        String tempStr = new String( c );
        if ( foundLast[0] ) {
          if ( validKeys( tempStr ) ) {
            code = tempStr; // this is the new code
            return code; // return recursively
          }
        } else if ( tempStr.equals( lastCode ) )
          foundLast[0] = true;
      }
    }

    // reset this char position
    c[i] = 'A';
    return code;
  }

  private boolean validKeys( String newCode ) {

    String keys[] = { "ABC" , "DEF" , "GHI" , "JKL" , "MNO" , "PQRS" , "TUV" ,
        "WXYZ" };

    for ( int i = 0 ; i < newCode.length() - 1 ; i++ ) {
      String c1 = newCode.substring( i , i + 1 );
      String c2 = newCode.substring( i + 1 , i + 2 );
      for ( int k = 0 ; k < keys.length ; k++ )
        if ( keys[k].indexOf( c1 ) != -1 && keys[k].indexOf( c2 ) != -1 )
          return false;
    }

    return true;
  }

  private String readLastCode( int codeLength ) {
    String lastCode = dao.readLastCode( codeLength );
    if ( lastCode == null ) {
      lastCode = "";
      for ( int i = 0 ; i < codeLength ; i++ ) {
        if ( i % 2 == 0 ) {
          lastCode += "A"; // odd char
        } else {
          lastCode += "D"; // even char
        }
      }
    }
    if ( lastCode.endsWith( "AD" ) ) {
      lastCode = lastCode.substring( 0 , lastCode.length() - 1 ) + "C";
    } else if ( lastCode.endsWith( "DA" ) ) {
      lastCode = lastCode.substring( 0 , lastCode.length() - 2 ) + "CZ";
    }
    return lastCode;
  }

  private BeepcodeBean createBeepcode( String code ) {
    BeepcodeBean beepcodeBean = null;
    if ( StringUtils.isBlank( code ) ) {
      return beepcodeBean;
    }
    beepcodeBean = new BeepcodeBean();
    beepcodeBean.setCode( code );
    beepcodeBean.setCodeLength( code.length() );
    return beepcodeBean;
  }

} // eof
