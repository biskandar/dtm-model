package com.beepcast.model.global;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.util.Util;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class GlobalDAO {

  static final DLogContext lctx = new SimpleContext( "GlobalDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert new global record.
   * <p>
   * 
   * @param global
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( GlobalBean global ) throws IOException {

    /*--------------------------
      verify unique global name
    --------------------------*/
    String name = global.getName();
    if ( select( name ) != null )
      throw new IOException( "Global [" + name + "] already exists." );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into global "
        + "(NAME,DESCRIPTION,STRING_VALUE,NUMERIC_VALUE,"
        + "BOOLEAN_VALUE,DATE_VALUE) values (" + "'"
        + StringEscapeUtils.escapeSql( name ) + "'," + "'"
        + StringEscapeUtils.escapeSql( global.getDescription() ) + "'," + "'"
        + StringEscapeUtils.escapeSql( global.getStringValue() ) + "',"
        + global.getNumericValue() + ","
        + ( ( global.getBooleanValue() == true ) ? 1 : 0 ) + "," + "'"
        + Util.strFormat( global.getDateValue() , "yyyy-mm-dd hh:nn:ss" )
        + "')";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select global record.
   * <p>
   * 
   * @param name
   * @return GlobalBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public GlobalBean select( String name ) throws IOException {

    GlobalBean global = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM global WHERE name = '"
        + StringEscapeUtils.escapeSql( name ) + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        global = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return global;

  } // select()

  /*****************************************************************************
   * Update global record.
   * <p>
   * 
   * @param GlobalBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update( GlobalBean global ) throws IOException {

    String name = global.getName();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "update global set " + "DESCRIPTION='"
        + StringEscapeUtils.escapeSql( global.getDescription() ) + "',"
        + "STRING_VALUE='"
        + StringEscapeUtils.escapeSql( global.getStringValue() ) + "',"
        + "NUMERIC_VALUE=" + global.getNumericValue() + "," + "BOOLEAN_VALUE="
        + ( ( global.getBooleanValue() == true ) ? 1 : 0 ) + ","
        + "DATE_VALUE='"
        + Util.strFormat( global.getDateValue() , "yyyy-mm-dd hh:nn:ss" )
        + "' " + "where name='" + name + "'";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // update()

  /*****************************************************************************
   * Delete global record.
   * <p>
   * 
   * @param GlobalBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( GlobalBean global ) throws IOException {

    String name = global.getName();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "DELETE FROM global WHERE name = '" + name + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // delete()

  /*****************************************************************************
   * Populate global bean.
   * <p>
   ****************************************************************************/
  private GlobalBean populateBean( ResultSet rs ) throws SQLException {
    GlobalBean global = new GlobalBean();
    global.setName( rs.getString( "name" ) );
    global.setDescription( rs.getString( "description" ) );
    global.setStringValue( rs.getString( "string_value" ) );
    global.setNumericValue( rs.getDouble( "numeric_value" ) );
    global.setBooleanValue( ( rs.getDouble( "boolean_value" ) == 1 ) ? true
        : false );
    global.setDateValue( Util.getUtilDate( rs.getDate( "date_value" ) ,
        rs.getTime( "date_value" ) ) );
    return global;
  }

} // eof

