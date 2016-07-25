package com.beepcast.model.beepcode;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.model.util.DateTimeFormat;
import com.beepcast.util.Util;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

/*******************************************************************************
 * Beepcode DAO.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class BeepcodeDAO {

  static final DLogContext lctx = new SimpleContext( "BeepcodeDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  protected static final int TOTAL_QUANTITY = 0;
  protected static final int ACTIVE_QUANTITY = 1;
  protected static final int INACTIVE_QUANTITY = 2;
  protected static final int RESERVED_QUANTITY = 3;
  protected static final int AVAILABLE_QUANTITY = 4;

  /*****************************************************************************
   * Insert a new beepcode record.
   * <p>
   * 
   * @param beepcode
   * @return -1 if code already exists, 0 if ok.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public boolean insert( BeepcodeBean beepcodeBean ) {
    boolean result = false;

    // verify unique code
    String code = beepcodeBean.getCode();
    if ( StringUtils.isBlank( code ) ) {
      return result;
    }
    if ( select( code ) != null ) {
      return result;
    }

    // build SQL string
    String sqlInsert = "INSERT INTO beepcode "
        + "(code,code_length,client_id,event_id,catagory_id,"
        + "medium_id,description,last_hit_date,active,reserved,"
        + "location_id) ";
    String sqlValues = "VALUES ('"
        + StringEscapeUtils.escapeSql( code )
        + "',"
        + beepcodeBean.getCodeLength()
        + ","
        + beepcodeBean.getClientID()
        + ","
        + beepcodeBean.getEventID()
        + ","
        + beepcodeBean.getCatagoryID()
        + ","
        + beepcodeBean.getMediumID()
        + ","
        + "'"
        + StringEscapeUtils.escapeSql( beepcodeBean.getDescription() )
        + "',"
        + "'"
        + Util
            .strFormat( beepcodeBean.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "'," + ( ( beepcodeBean.getActive() == true ) ? 1 : 0 ) + ","
        + ( ( beepcodeBean.getReserved() == true ) ? 1 : 0 ) + ","
        + beepcodeBean.getLocationID() + ") ";
    String sql = sqlInsert + sqlValues;

    // execute query
    DLog.debug( lctx , "Perform " + sql );
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    // success
    return result;
  }

  public BeepcodeBean select( String code ) {
    BeepcodeBean beepcode = null;

    if ( code == null ) {
      return beepcode;
    }

    // build SQL string
    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ( code = '" + StringEscapeUtils.escapeSql( code )
        + "' ) ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery( sql );
      if ( rs.next() ) {
        beepcode = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      // ...
    } finally {
      conn.disconnect( true );
    }

    return beepcode;
  } // select()

  public BeepcodeBean select( String code , boolean active ) throws IOException {
    BeepcodeBean beepcode = null;

    if ( code == null ) {
      return beepcode;
    }

    int iactive = active ? 1 : 0;

    // build SQL string
    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ( code = '" + StringEscapeUtils.escapeSql( code )
        + "' ) ";
    sqlWhere += "AND ( active = " + iactive + " ) ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      if ( rs.next() ) {
        beepcode = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return beepcode;
  }

  /*****************************************************************************
   * Update beepcode record.
   * <p>
   * 
   * @param BeepcodeBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public boolean update( BeepcodeBean beepcodeBean ) {
    boolean result = false;

    // validate parameters
    if ( beepcodeBean == null ) {
      DLog.warning( lctx , "Failed to update , found null beepcode bean" );
      return result;
    }

    // get and validate code
    String code = beepcodeBean.getCode();
    if ( StringUtils.isBlank( code ) ) {
      DLog.warning( lctx , "Failed to update , found empty code" );
      return result;
    }

    // compose sql
    String sqlUpdate = "UPDATE beepcode ";
    String sqlSet = "SET code_length = "
        + beepcodeBean.getCodeLength()
        + " , client_id = "
        + beepcodeBean.getClientID()
        + " , event_id = "
        + beepcodeBean.getEventID()
        + " , catagory_id = "
        + beepcodeBean.getCatagoryID()
        + " , medium_id = "
        + beepcodeBean.getMediumID()
        + " , description = '"
        + StringEscapeUtils.escapeSql( beepcodeBean.getDescription() )
        + "' , last_hit_date = '"
        + Util
            .strFormat( beepcodeBean.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "' , active = " + ( ( beepcodeBean.getActive() == true ) ? 1 : 0 )
        + " , reserved = " + ( ( beepcodeBean.getReserved() == true ) ? 1 : 0 )
        + " , location_id = " + beepcodeBean.getLocationID() + " ";
    String sqlWhere = "WHERE ( code = '" + StringEscapeUtils.escapeSql( code )
        + "' ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    // execute sql
    DLog.debug( lctx , "Perform " + sql );
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  } // update()

  public boolean updateLastHitDate( String code , Date lastHitDate ) {
    boolean result = false;

    // validate parameters
    if ( StringUtils.isBlank( code ) ) {
      DLog.warning( lctx , "Failed to update last hit date , found empty code" );
      return result;
    }

    // clean parameters
    lastHitDate = ( lastHitDate == null ) ? new Date() : lastHitDate;
    String strLastHitDate = DateTimeFormat.convertToString( lastHitDate );

    // compose sql
    String sqlUpdate = "UPDATE beepcode ";
    String sqlSet = "SET last_hit_date = '" + strLastHitDate + "' ";
    String sqlWhere = "WHERE ( code = '" + StringEscapeUtils.escapeSql( code )
        + "' ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    // execute sql
    DLog.debug( lctx , "Perform " + sql );
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  /*****************************************************************************
   * Read last code generated for a given code length.
   * <p>
   * 
   * @param codeLength
   *          Determines which table record to read.
   * @return String The last code generated for the given length.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public String readLastCode( int codeLength ) {
    String code = null;

    if ( codeLength < 1 ) {
      return code;
    }

    // compose sql
    String sqlSelect = "SELECT last_code ";
    String sqlFrom = "FROM last_code ";
    String sqlWhere = "WHERE ( code_length = " + codeLength + " ) ";
    String sqlLimit = "LIMIT 1";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;

    // execute sql
    DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      QueryItem qi = null;
      Iterator iter = qr.iterator();
      if ( iter.hasNext() ) {
        qi = (QueryItem) iter.next();
      }
      if ( qi != null ) {
        code = qi.getFirstValue();
      }
    }

    return code;
  } // readLastCode()

  /*****************************************************************************
   * Update last code generated for a given code length.
   * <p>
   * 
   * @param code
   *          The last code generated for the given length.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public boolean updateLastCode( String code ) {
    boolean result = false;

    if ( code == null ) {
      return result;
    }

    int codeLength = code.length();

    // update code for existing length
    if ( readLastCode( codeLength ) != null ) {

      String sqlUpdate = "UPDATE last_code ";
      String sqlSet = "SET last_code = '" + StringEscapeUtils.escapeSql( code )
          + "' , date_updated = NOW() ";
      String sqlWhere = "WHERE ( code_length = " + codeLength + " ) ";

      String sql = sqlUpdate + sqlSet + sqlWhere;

      DLog.debug( lctx , "Perform " + sql );

      Integer irslt = dbLib.executeQuery( "profiledb" , sql );
      if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
        result = true;
      }

      return result;
    }

    // insert code for new length
    String sqlInsert = "INSERT INTO last_code "
        + "( code_length , last_code , date_inserted , date_updated ) ";
    String sqlValues = "VALUES ( " + codeLength + " , '"
        + StringEscapeUtils.escapeSql( code ) + "' , NOW() , NOW() ) ";

    String sql = sqlInsert + sqlValues;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  } // updateLastCode()

  public int readMinLength() {
    int length = -1;
    String sql = "SELECT MIN(code_length) FROM beepcode ";
    DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      QueryItem qi = null;
      Iterator iter = qr.iterator();
      if ( iter.hasNext() ) {
        qi = (QueryItem) iter.next();
      }
      if ( qi != null ) {
        try {
          length = Integer.parseInt( qi.getFirstValue() );
        } catch ( NumberFormatException e ) {
          length = -1;
        }
      }
    }
    return length;
  } // readMinLength()

  public int readMaxLength() {
    int length = -1;
    String sql = "SELECT MAX(code_length) FROM beepcode ";
    DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      QueryItem qi = null;
      Iterator iter = qr.iterator();
      if ( iter.hasNext() ) {
        qi = (QueryItem) iter.next();
      }
      if ( qi != null ) {
        try {
          length = Integer.parseInt( qi.getFirstValue() );
        } catch ( NumberFormatException e ) {
          length = -1;
        }
      }
    }
    return length;
  } // readMaxLength()

  /*****************************************************************************
   * Read quantity of codes in the database of a given length.
   * <p>
   * 
   * @param codeLength
   *          The given code length, or 0 for all lengths.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public int readQuantity( int codeLength , int type ) {
    int quantity = 0;

    String sqlSelect = "SELECT COUNT(code) AS total ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ";
    if ( codeLength > 0 ) {
      sqlWhere += "( code_length = " + codeLength + " ) ";
    }
    if ( type == ACTIVE_QUANTITY ) {
      sqlWhere += "AND ( active = 1 ) AND ( reserved = 0 ) ";
    }
    if ( type == RESERVED_QUANTITY ) {
      sqlWhere += "AND ( reserved = 1 ) ";
    }
    if ( type == AVAILABLE_QUANTITY ) {
      sqlWhere += "AND ( active = 0 ) AND ( reserved = 0 ) ";
      sqlWhere += "AND ( last_hit_date < '"
          + Util.strFormat( maxInactiveDate() , "yyyy-mm-dd hh:nn:ss" )
          + "' ) ";
    }
    if ( type == INACTIVE_QUANTITY ) {
      sqlWhere += "AND ( active = 0 ) AND ( reserved = 0 ) ";
      sqlWhere += "AND ( last_hit_date >= '"
          + Util.strFormat( maxInactiveDate() , "yyyy-mm-dd hh:nn:ss" )
          + "' ) ";
    }

    String sql = sqlSelect + sqlFrom + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );
    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr != null ) && ( qr.size() > 0 ) ) {
      QueryItem qi = null;
      Iterator iter = qr.iterator();
      if ( iter.hasNext() ) {
        qi = (QueryItem) iter.next();
      }
      if ( qi != null ) {
        try {
          quantity = Integer.parseInt( qi.getFirstValue() );
        } catch ( NumberFormatException e ) {
        }
      }
    }
    return quantity;
  } // readQuantity()

  /*****************************************************************************
   * Get reserved codes for a given client.
   * <p>
   * 
   * @param codeLength
   * @param clientID
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector getClientReservedCodes( int codeLength , long clientID )
      throws IOException {

    Vector beepcodes = new Vector( 1000 , 1000 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "select * from beepcode where active=0 and reserved=1 "
        + "and code_length=" + codeLength + " and client_id=" + clientID
        + " order by code";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        beepcodes.addElement( populateBean( rs ) );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    /*--------------------------
      return vector
    --------------------------*/
    return beepcodes;

  } // getClientReservedCodes()

  /*****************************************************************************
   * Select next available beepcode of the given code length.
   * <p>
   * 
   * @param codeLength
   * @return BeepcodeBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public List selectNextAvailableCode( int codeLength , int limit ) {
    List list = new ArrayList();

    if ( limit < 1 ) {
      return list;
    }

    // build sql
    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ( active = 0 ) AND ( reserved = 0 ) ";
    sqlWhere += "AND ( code_length = " + codeLength + " ) ";
    sqlWhere += "AND ( last_hit_date < '"
        + Util.strFormat( maxInactiveDate() , "yyyy-mm-dd hh:nn:ss" ) + "' ) ";
    String sqlOrderBy = "ORDER BY id ASC ";
    String sqlLimit = "LIMIT " + limit + " ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrderBy + sqlLimit;

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        BeepcodeBean beepcodeBean = populateBean( rs );
        if ( beepcodeBean != null ) {
          list.add( beepcodeBean );
        }
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      // ...
    } finally {
      conn.disconnect( true );
    }

    return list;
  }

  /*****************************************************************************
   * Select all beepcode records for the given event ID.
   * <p>
   * 
   * @param eventID
   * @return Hashtable of BeepcodeBean, null if none found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Hashtable selectEventCodes( long eventID ) throws IOException {

    Hashtable beepcodes = new Hashtable( 100 );

    // build SQL string
    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( event_id = " + eventID + " ) ";
    String sqlOrderBy = "ORDER BY id ASC ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrderBy;

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        BeepcodeBean beepcode = populateBean( rs );
        beepcodes.put( beepcode.getCode() , beepcode );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return beepcodes;

  } // selectEventCodes()

  public List listActiveCodesKeywordsFromEventId( int eventId ) {
    List list = new ArrayList();

    String sqlSelect = "SELECT code ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    sqlWhere += "AND ( event_id = " + eventId + " ) ";
    String sqlOrder = "ORDER BY code ASC ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return list;
    }
    Iterator iter = qr.iterator();
    while ( iter.hasNext() ) {
      QueryItem qi = (QueryItem) iter.next();
      if ( qi == null ) {
        continue;
      }
      String code = qi.getFirstValue();
      if ( StringUtils.isBlank( code ) ) {
        continue;
      }
      list.add( code );
    }

    return list;
  }

  public int totalActiveCodes( int clientId ) {
    int totalRecords = 0;

    String sqlSelect = "SELECT COUNT(id) AS total ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ( active = 1 ) AND ( reserved = 0 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";

    String sql = sqlSelect + sqlFrom + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return totalRecords;
    }
    Iterator iter = qr.iterator();
    if ( !iter.hasNext() ) {
      return totalRecords;
    }
    QueryItem qi = (QueryItem) iter.next();
    try {
      totalRecords = Integer.parseInt( qi.getFirstValue() );
    } catch ( NumberFormatException e ) {
    }

    return totalRecords;
  }

  public List listActiveCodes( int clientId ) {
    List list = new ArrayList();

    String sqlSelect = "SELECT code ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ( active = 1 ) AND ( reserved = 0 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";
    String sqlOrder = "ORDER BY code ASC ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return list;
    }
    Iterator iter = qr.iterator();
    while ( iter.hasNext() ) {
      QueryItem qi = (QueryItem) iter.next();
      if ( qi == null ) {
        continue;
      }
      String code = qi.getFirstValue();
      if ( StringUtils.isBlank( code ) ) {
        continue;
      }
      list.add( code );
    }

    return list;
  }

  public int totalActiveKeywords( int clientId ) {
    int totalRecords = 0;

    String sqlSelect = "SELECT COUNT(id) AS total ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ( active = 1 ) AND ( reserved = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";

    String sql = sqlSelect + sqlFrom + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return totalRecords;
    }
    Iterator iter = qr.iterator();
    if ( !iter.hasNext() ) {
      return totalRecords;
    }
    QueryItem qi = (QueryItem) iter.next();
    try {
      totalRecords = Integer.parseInt( qi.getFirstValue() );
    } catch ( NumberFormatException e ) {
    }

    return totalRecords;
  }

  public List listActiveKeywords( int clientId ) {
    List list = new ArrayList();

    String sqlSelect = "SELECT code ";
    String sqlFrom = "FROM beepcode ";
    String sqlWhere = "WHERE ( active = 1 ) AND ( reserved = 1 ) ";
    sqlWhere += "AND ( client_id = " + clientId + " ) ";
    String sqlOrder = "ORDER BY code ASC ";

    String sql = sqlSelect + sqlFrom + sqlWhere + sqlOrder;

    DLog.debug( lctx , "Perform " + sql );

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return list;
    }
    Iterator iter = qr.iterator();
    while ( iter.hasNext() ) {
      QueryItem qi = (QueryItem) iter.next();
      if ( qi == null ) {
        continue;
      }
      String code = qi.getFirstValue();
      if ( StringUtils.isBlank( code ) ) {
        continue;
      }
      list.add( code );
    }

    return list;
  }

  /*****************************************************************************
   * Beepcodes must have been deactivated before this date in order to qualify
   * for recycling.
   * <p>
   * 
   * @return Max inactive date.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  private java.util.Date maxInactiveDate() {

    int inactiveDays = 30;

    java.util.Date now = new java.util.Date();
    Calendar c = new GregorianCalendar();
    c.setTime( now );
    c.add( Calendar.DATE , -1 * inactiveDays );

    return c.getTime();

  } // maxInactiveDate()

  /*****************************************************************************
   * populate beepcode bean
   ****************************************************************************/
  private BeepcodeBean populateBean( ResultSet rs ) throws SQLException {

    BeepcodeBean beepcode = new BeepcodeBean();

    beepcode.setCode( rs.getString( "code" ) );
    beepcode.setCodeLength( (int) rs.getDouble( "code_length" ) );
    beepcode.setClientID( (long) rs.getDouble( "client_id" ) );
    beepcode.setEventID( (long) rs.getDouble( "event_id" ) );
    beepcode.setCatagoryID( (long) rs.getDouble( "catagory_id" ) );
    beepcode.setMediumID( (long) rs.getDouble( "medium_id" ) );
    beepcode.setDescription( rs.getString( "description" ) );
    beepcode.setLastHitDate( Util.getUtilDate( rs.getDate( "last_hit_date" ) ,
        rs.getTime( "last_hit_date" ) ) );
    beepcode.setActive( ( rs.getDouble( "active" ) == 1 ) ? true : false );
    beepcode.setReserved( ( rs.getDouble( "reserved" ) == 1 ) ? true : false );
    beepcode.setLocationID( (long) rs.getDouble( "location_id" ) );

    return beepcode;
  }

}
