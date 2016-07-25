package com.beepcast.model.client;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class TestPhoneDAO {

  static final DLogContext lctx = new SimpleContext( "TestPhoneDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert new test phone record.
   * <p>
   * 
   * @param testPhone
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( TestPhoneBean testPhone ) throws IOException {

    long clientID = testPhone.getClientID();
    String phone = testPhone.getPhone();

    /*--------------------------
      verify unique test phone
    --------------------------*/
    if ( select( clientID , phone ) != null )
      return;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "INSERT INTO test_phone " + "( client_id , phone ) "
        + "VALUES ( " + clientID + " , '" + StringEscapeUtils.escapeSql( phone )
        + "' ) ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select test phone record.
   * <p>
   * 
   * @param clientID
   * @param phone
   * @return TestPhoneBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public TestPhoneBean select( long clientID , String phone )
      throws IOException {

    TestPhoneBean testPhone = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM test_phone " + "WHERE client_id = ( "
        + clientID + " ) and ( phone = '" + StringEscapeUtils.escapeSql( phone )
        + "' ) ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        testPhone = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return testPhone;

  } // select(long,String)

  /*****************************************************************************
   * Select test phone records for a given client ID.
   * <p>
   * 
   * @param clientID
   * @return Array of phone numbers, or null if none found
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public String[] select( long clientID ) throws IOException {

    String testPhones[] = { "" };
    Vector vRecords = new Vector( 10 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM test_phone WHERE client_id = " + clientID + " ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        TestPhoneBean testPhone = populateBean( rs );
        vRecords.addElement( testPhone.getPhone() );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    /*--------------------------
      build array of send records
    --------------------------*/
    int size = vRecords.size();
    if ( size > 0 ) {
      testPhones = new String[size];
      for ( int i = 0 ; i < size ; i++ ) {
        testPhones[i] = (String) vRecords.elementAt( i );
      }
    }

    return testPhones;

  } // select(long)

  /*****************************************************************************
   * Delete test phone record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( TestPhoneBean testPhone ) throws IOException {

    long clientID = testPhone.getClientID();
    String phone = testPhone.getPhone();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "DELETE FROM test_phone " + "WHERE ( client_id = " + clientID
        + " ) and ( phone = '" + StringEscapeUtils.escapeSql( phone ) + "' ) ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // update()

  /*****************************************************************************
   * Populate test phone bean.
   * <p>
   ****************************************************************************/
  private TestPhoneBean populateBean( ResultSet rs ) throws SQLException {
    TestPhoneBean testPhone = new TestPhoneBean();
    testPhone.setClientID( (long) rs.getDouble( "client_id" ) );
    testPhone.setPhone( rs.getString( "phone" ) );
    return testPhone;
  }

} // eof
