package com.beepcast.model.event;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class CatagoryDAO {

  static final DLogContext lctx = new SimpleContext( "CatagoryDAO" );

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  public CatagoryDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();
  }

  public boolean insert( CatagoryBean catagory ) throws IOException {
    boolean result = false;

    // get uppercase catagory name
    String catagoryName = catagory.getCatagoryName();
    if ( catagoryName != null )
      catagoryName = catagoryName.toUpperCase();

    // verify unique catagory ID
    if ( select( catagoryName ) != null ) {
      throw new IOException( "Catagory [" + catagoryName + "] already exists." );
    }

    // build SQL string
    String sqlInsert = "INSERT INTO catagory( catagory_name ) ";
    String sqlValues = "VALUES ('" + StringEscapeUtils.escapeSql( catagoryName )
        + "' ) ";
    String sql = sqlInsert + sqlValues;

    // execute query
    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public CatagoryBean select( long catagoryID ) throws IOException {

    CatagoryBean catagory = null;

    // build SQL string
    String sql = "SELECT * FROM catagory WHERE catagory_id = " + catagoryID;

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        catagory = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return catagory;

  }

  public CatagoryBean select( String catagoryName ) throws IOException {

    CatagoryBean catagory = null;

    // build SQL string
    String sql = "SELECT * " + "FROM catagory " + "WHERE catagory_name = '"
        + StringEscapeUtils.escapeSql( catagoryName ) + "' ";

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        catagory = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return catagory;

  } // select(String)

  public Hashtable select() throws IOException {

    Hashtable catagories = new Hashtable( 100 );

    // build SQL string
    String sql = "SELECT * FROM catagory ";

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        CatagoryBean catagory = populateBean( rs );
        catagories.put( "" + catagory.getCatagoryID() , catagory );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return catagories;

  } // select()

  public Vector selectCatagoryNames() throws IOException {

    Vector catagoryNames = new Vector( 10 , 10 );

    // build SQL string
    String sql = "SELECT catagory_name FROM catagory ";

    // execute query
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        catagoryNames.addElement( rs.getString( "catagory_name" ) );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return catagoryNames;

  }

  private CatagoryBean populateBean( ResultSet rs ) throws SQLException {
    CatagoryBean catagory = new CatagoryBean();
    catagory.setCatagoryID( (long) rs.getDouble( "catagory_id" ) );
    catagory.setCatagoryName( rs.getString( "catagory_name" ) );
    return catagory;
  }

}
