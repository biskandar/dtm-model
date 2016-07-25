package com.beepcast.model.mobileUser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

/*******************************************************************************
 * Interest DAO.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class InterestDAO {

  static final DLogContext lctx = new SimpleContext( "InterestDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert a new interest record.
   * <p>
   * 
   * @param interest
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( InterestBean interest ) throws IOException {

    String phone = interest.getPhone();
    long catagoryID = interest.getCatagoryID();

    /*--------------------------
      verify unique interest
    --------------------------*/
    if ( select( phone , catagoryID ) != null ) {
      return;
    }

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "INSERT INTO interest ( phone , catagory_id )" + " VALUES ( '"
        + StringEscapeUtils.escapeSql( phone ) + "' , " + catagoryID + " ) ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select interest record for the given phone and catagoryID.
   * <p>
   * 
   * @param phone
   * @param catagoryID
   * @return InterestBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public InterestBean select( String phone , long catagoryID )
      throws IOException {

    InterestBean interest = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM interest " + "WHERE ( phone = '"
        + StringEscapeUtils.escapeSql( phone ) + "' ) AND ( catagory_id = "
        + catagoryID + " ) ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        interest = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return interest;

  } // select(string,long)

  /*****************************************************************************
   * Select all interest records for the given phone.
   * <p>
   * 
   * @param phone
   * @return Vector of InterestBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector select( String phone ) throws IOException {

    Vector interests = new Vector( 100 , 100 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM interest " + "WHERE phone = '"
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
        interests.addElement( populateBean( rs ) );
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
    return interests;

  } // select(String)

  /*****************************************************************************
   * Delete all interest records for the given phone.
   * <p>
   * 
   * @param phone
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( String phone ) throws IOException {

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "DELETE FROM interest " + "WHERE phone = '"
        + StringEscapeUtils.escapeSql( phone ) + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // delete()

  /*****************************************************************************
   * Get interests via http service.
   * <p>
   * 
   * @param InterestBean
   * @return List of interests, or "ERROR:" + error message
   ****************************************************************************/
  public String getInterests( InterestBean interest ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + interest.getHost()
          + ":8080/beepadmin/interest_action" );
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod( "POST" );
      httpConn.setDoInput( true );
      httpConn.setDoOutput( true );
      httpConn.setUseCaches( false );

      /*---------------------------
        send the request
      ---------------------------*/
      DataOutputStream os = new DataOutputStream( httpConn.getOutputStream() );
      os.writeBytes( "mode=select" + "&phone="
          + URLEncoder.encode( interest.getPhone() ) );
      os.flush();
      os.close();

      /*---------------------------
        get response from web server
      ---------------------------*/
      BufferedReader in = new BufferedReader( new InputStreamReader(
          httpConn.getInputStream() ) );
      String line = "";
      while ( ( line = in.readLine() ) != null ) {
        response += line + "\n";
      }
      if ( response.length() > 0 )
        response = response.substring( 0 , response.length() - 1 );
      in.close();

      /*---------------------------
        handle exceptions
      ---------------------------*/
    } catch ( Exception e ) {
      return "ERROR: " + e.getMessage();
    }

    // success
    return response;

  } // getInterests()

  /*****************************************************************************
   * Set interests via http service.
   * <p>
   * 
   * @param InterestBean
   * @param interestList
   * @return "OK" if success, or "ERROR:" + error message
   ****************************************************************************/
  public String setInterests( InterestBean interest , String interestList ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + interest.getHost()
          + ":8080/beepadmin/interest_action" );
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod( "POST" );
      httpConn.setDoInput( true );
      httpConn.setDoOutput( true );
      httpConn.setUseCaches( false );

      /*---------------------------
        send the request
      ---------------------------*/
      DataOutputStream os = new DataOutputStream( httpConn.getOutputStream() );
      os.writeBytes( "mode=update" + "&phone="
          + URLEncoder.encode( interest.getPhone() ) + "&interestList="
          + URLEncoder.encode( interestList ) );
      os.flush();
      os.close();

      /*---------------------------
        get response from web server
      ---------------------------*/
      BufferedReader in = new BufferedReader( new InputStreamReader(
          httpConn.getInputStream() ) );
      String line = "";
      while ( ( line = in.readLine() ) != null ) {
        response += line + "\n";
      }
      if ( response.length() > 0 )
        response = response.substring( 0 , response.length() - 1 );
      in.close();

      /*---------------------------
        handle exceptions
      ---------------------------*/
    } catch ( Exception e ) {
      return "ERROR: " + e.getMessage();
    }

    // success
    return response;

  } // setInterests()

  /*****************************************************************************
   * Populate interest bean.
   * <p>
   ****************************************************************************/
  private InterestBean populateBean( ResultSet rs ) throws SQLException {
    InterestBean interest = new InterestBean();
    interest.setPhone( rs.getString( "phone" ) );
    interest.setCatagoryID( (long) rs.getDouble( "catagory_id" ) );
    return interest;
  }

} // eof
