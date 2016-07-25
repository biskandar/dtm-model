package com.beepcast.model.exchange;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.util.Util;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

/*******************************************************************************
 * Sponsor DAO.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class SponsorDAO {

  static final DLogContext lctx = new SimpleContext( "SponsorDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert a new sponsor record.
   * <p>
   * 
   * @param sponsor
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( SponsorBean sponsor ) throws IOException {

    /*--------------------------
      get uppercase code
    --------------------------*/
    String code = sponsor.getCode();
    if ( code != null ) {
      code = code.toUpperCase();
    }

    /*--------------------------
      verify unique sponsor ID
    --------------------------*/
    if ( select( code ) != null ) {
      throw new IOException( "Sponsor code [" + code + "] already exists." );
    }

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into sponsor " + "(CODE,LAST_HIT_DATE,HIT_COUNT)"
        + " values ( '" + StringEscapeUtils.escapeSql( code ) + "','"
        + Util.strFormat( sponsor.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "'," + sponsor.getHitCount() + ")";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select sponsor record.
   * <p>
   * 
   * @param sponsorID
   * @return SponsorBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public SponsorBean select( long sponsorID ) throws IOException {

    SponsorBean sponsor = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM sponsor WHERE sponsor_id = " + sponsorID + " ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        sponsor = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return sponsor;

  } // select(long)

  /*****************************************************************************
   * Select sponsor record.
   * <p>
   * 
   * @param code
   * @return SponsorBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public SponsorBean select( String code ) throws IOException {

    SponsorBean sponsor = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM sponsor WHERE code = '"
        + StringEscapeUtils.escapeSql( code ) + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        sponsor = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return sponsor;

  } // select(String)

  /*****************************************************************************
   * Select all sponsor records.
   * <p>
   * 
   * @return Hashtable of SponsorBean, null if none found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Hashtable select() throws IOException {

    Hashtable sponsors = new Hashtable( 100 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM sponsor";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        SponsorBean sponsor = populateBean( rs );
        sponsors.put( "" + sponsor.getSponsorID() , sponsor );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return sponsors;

  } // select()

  /*****************************************************************************
   * Select sponsor codes.
   * <p>
   * 
   * @return All defined sponsor codes.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector selectCodes() throws IOException {

    Vector codes = new Vector( 10 , 10 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT code FROM sponsor ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        codes.addElement( rs.getString( "code" ) );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return codes;

  } // selectCodes()

  /*****************************************************************************
   * Update sponsor record.
   * <p>
   * 
   * @param SponsorBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update( SponsorBean sponsor ) throws IOException {

    long sponsorID = sponsor.getSponsorID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "update sponsor set " + "CODE='"
        + StringEscapeUtils.escapeSql( sponsor.getCode() ) + "',"
        + "LAST_HIT_DATE='"
        + Util.strFormat( sponsor.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "'," + "HIT_COUNT=" + sponsor.getHitCount() + " "
        + "where sponsor_id=" + sponsorID;

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // update()

  /*****************************************************************************
   * Delete sponsor record.
   * <p>
   * 
   * @param SponsorBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( SponsorBean sponsor ) throws IOException {

    long sponsorID = sponsor.getSponsorID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "DELETE FROM sponsor " + "WHERE sponsor_id = " + sponsorID
        + " ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // delete()

  /*****************************************************************************
   * Populate sponsor bean.
   * <p>
   ****************************************************************************/
  private SponsorBean populateBean( ResultSet rs ) throws SQLException {
    SponsorBean sponsor = new SponsorBean();
    sponsor.setSponsorID( (long) rs.getDouble( "sponsor_id" ) );
    sponsor.setCode( rs.getString( "code" ) );
    sponsor.setLastHitDate( Util.getUtilDate( rs.getDate( "last_hit_date" ) ,
        rs.getTime( "last_hit_date" ) ) );
    sponsor.setHitCount( (long) rs.getDouble( "hit_count" ) );
    return sponsor;
  }

} // eof
