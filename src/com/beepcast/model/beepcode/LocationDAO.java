package com.beepcast.model.beepcode;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

/*******************************************************************************
 * Location DAO.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class LocationDAO {

  static final DLogContext lctx = new SimpleContext( "LocationDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert a new location record.
   * <p>
   * 
   * @param location
   * @return location ID
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public long insert( LocationBean location ) throws IOException {

    /*--------------------------
      get location string
    --------------------------*/
    String locationStr = location.getLocation();

    /*--------------------------
      return existing location ID
    --------------------------*/
    LocationBean lb = new LocationBean().select( locationStr );
    if ( lb != null )
      return lb.getLocationID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into location " + "(LOCATION)" + " values (" + "'"
        + StringEscapeUtils.escapeSql( locationStr ) + "')";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

    // return location id
    lb = new LocationBean().select( locationStr );
    return lb.getLocationID();

  } // insert()

  /*****************************************************************************
   * Select location record.
   * <p>
   * 
   * @param locationID
   * @return LocationBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public LocationBean select( long locationID ) throws IOException {

    LocationBean location = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM location WHERE location_id = " + locationID;

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        location = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return location;

  } // select(long)

  /*****************************************************************************
   * Select location record.
   * <p>
   * 
   * @param locationStr
   * @return LocationBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public LocationBean select( String locationStr ) throws IOException {

    LocationBean location = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM location " + "WHERE location = '"
        + StringEscapeUtils.escapeSql( locationStr ) + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        location = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return location;

  } // select(String)

  /*---------------------------
    populate location bean
  ---------------------------*/
  private LocationBean populateBean( ResultSet rs ) throws SQLException {
    LocationBean location = new LocationBean();
    location.setLocationID( (long) rs.getDouble( "location_id" ) );
    location.setLocation( rs.getString( "location" ) );
    return location;
  }

} // eof

