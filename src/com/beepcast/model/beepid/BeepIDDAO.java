package com.beepcast.model.beepid;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.util.Util;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class BeepIDDAO {

  static final DLogContext lctx = new SimpleContext( "BeepIDDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert new beep id record.
   * <p>
   * 
   * @param beepID
   * @return -1 if beepID already exists, -2 if phone already has beepID, 0 if
   *         ok.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public int insert( BeepIDBean beepIDBean ) throws IOException {

    /*--------------------------
      verify unique phone number
    --------------------------*/
    String beepID = beepIDBean.getBeepID();
    if ( select( beepID , true ) != null )
      return -1; // beep id already exists
    String phone = beepIDBean.getPhone();
    /*
     * if (select(phone)!=null) return -2; // phone already has beep id
     */

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into beep_id "
        + "(BEEP_ID,PHONE,PLAIN_TEXT,CLIENT_ID,EVENT_ID,LAST_HIT_DATE) "
        + "values (" + "'" + StringEscapeUtils.escapeSql( beepID ) + "'," + "'"
        + StringEscapeUtils.escapeSql( phone ) + "'," + "'"
        + StringEscapeUtils.escapeSql( beepIDBean.getPlainText() ) + "',"
        + beepIDBean.getClientID() + "," + beepIDBean.getEventID() + "," + "'"
        + Util.strFormat( beepIDBean.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "')";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

    // success
    return 0;

  } // insert()

  /*****************************************************************************
   * Select beepID record.
   * <p>
   * 
   * @param phone
   * @return BeepIDBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public BeepIDBean select( String phone ) throws IOException {

    BeepIDBean beepIDBean = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM beep_id " + "WHERE phone = '"
        + StringEscapeUtils.escapeSql( phone ) + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        beepIDBean = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return beepIDBean;

  } // select(string)

  /*****************************************************************************
   * Select beepID record.
   * <p>
   * 
   * @param beepID
   * @param anything
   *          if true or false, selects where beepID
   * @return BeepIDBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public BeepIDBean select( String beepID , boolean anything )
      throws IOException {

    BeepIDBean beepIDBean = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM beep_id " + "WHERE beep_id = '"
        + StringEscapeUtils.escapeSql( beepID ) + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        beepIDBean = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return beepIDBean;

  } // select(string,boolean)

  /*****************************************************************************
   * Select a Vector of beepid records.
   * <p>
   * 
   * @param criteria
   *          Example: "phone='+6596690924'"
   * @return A Vector of BeepIDBean objects.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector selectCriteria( String criteria ) throws IOException {

    Vector beepIDs = new Vector( 1000 , 1000 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM beep_id ";
    if ( criteria != null && criteria.length() > 0 )
      sql += " WHERE " + criteria;

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        beepIDs.addElement( populateBean( rs ) );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    /*--------------------------
      return enumeration
    --------------------------*/
    return beepIDs;

  } // selectCriteria(String)

  /*****************************************************************************
   * Update beepID record.
   * <p>
   * 
   * @param BeepIDBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update( BeepIDBean beepIDBean ) throws IOException {

    String beepID = beepIDBean.getBeepID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "update beep_id set " + "PHONE='"
        + StringEscapeUtils.escapeSql( beepIDBean.getPhone() ) + "',"
        + "PLAIN_TEXT='"
        + StringEscapeUtils.escapeSql( beepIDBean.getPlainText() ) + "',"
        + "CLIENT_ID=" + beepIDBean.getClientID() + "," + "EVENT_ID="
        + beepIDBean.getEventID() + "," + "LAST_HIT_DATE='"
        + Util.strFormat( beepIDBean.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "' " + "where beep_id='" + beepID + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // update()

  /*****************************************************************************
   * Delete beep id record.
   * <p>
   * 
   * @param BeepIDBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( BeepIDBean beepIDBean ) throws IOException {

    String beepID = beepIDBean.getBeepID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "DELETE FROM beep_id WHERE beep_id = '"
        + StringEscapeUtils.escapeSql( beepID ) + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // delete()

  /*****************************************************************************
   * Read last BEEPid generated.
   * <p>
   * 
   * @return String The last plain text BEEPid generated.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public String readLastPlainText() throws IOException {

    String lastPlainText = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT last_plain_text " + "FROM last_plain_text ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        lastPlainText = rs.getString( "LAST_PLAIN_TEXT" );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return lastPlainText;

  } // readLastPlainText()

  /*****************************************************************************
   * Update last plain text BEEPid generated.
   * <p>
   * 
   * @param lastPlainText
   *          Last plain text BEEPid generated.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void updateLastPlainText( String lastPlainText ) throws IOException {

    if ( lastPlainText == null )
      return;

    /*--------------------------
      update beepid
    --------------------------*/
    if ( ( readLastPlainText() ) != null ) {
      String sql = "UPDATE last_plain_text SET last_plain_text = '"
          + StringEscapeUtils.escapeSql( lastPlainText ) + "' ";

      DLog.debug( lctx , "Perform " + sql );
      dbLib.executeQuery( "profiledb" , sql );
      // new SQL().executeUpdate( sql );

    }
    /*--------------------------
      insert beepID
    --------------------------*/
    else {
      String sql = "INSERT INTO last_plain_text " + "( last_plain_text ) "
          + "VALUES ( '" + StringEscapeUtils.escapeSql( lastPlainText )
          + "' ) ";

      DLog.debug( lctx , "Perform " + sql );
      dbLib.executeQuery( "profiledb" , sql );
      // new SQL().executeUpdate( sql );

    }

  } // updateLastPlainText()

  /*****************************************************************************
   * Select next available BEEPid.
   * <p>
   * 
   * @return BeepIDBean, or null if none available.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public BeepIDBean selectNextAvailableBeepID() throws IOException {

    BeepIDBean beepIDBean = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM beep_id "
        + "WHERE ( phone = '' ) AND ( client_id = 0 ) ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        beepIDBean = populateBean( rs );
        break;
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return beepIDBean;

  } // selectNextAvailableBeepID()

  /*****************************************************************************
   * Populate beep id bean.
   * <p>
   ****************************************************************************/
  private BeepIDBean populateBean( ResultSet rs ) throws SQLException {
    BeepIDBean beepIDBean = new BeepIDBean();
    beepIDBean.setBeepID( rs.getString( "beep_id" ) );
    beepIDBean.setPhone( rs.getString( "phone" ) );
    beepIDBean.setPlainText( rs.getString( "plain_text" ) );
    beepIDBean.setClientID( (long) rs.getDouble( "client_id" ) );
    beepIDBean.setEventID( (long) rs.getDouble( "event_id" ) );
    beepIDBean.setLastHitDate( Util.getUtilDate( rs.getDate( "last_hit_date" ) ,
        rs.getTime( "last_hit_date" ) ) );
    // System.out.println(beepIDBean.getEventID());
    return beepIDBean;

  }

} // eof
