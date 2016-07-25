package com.beepcast.model.exchange;

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
import com.beepcast.util.StrTok;
import com.beepcast.util.Util;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ExchangeDAO {

  static final DLogContext lctx = new SimpleContext( "ExchangeDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert new exchange record.
   * <p>
   * 
   * @param exchange
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( ExchangeBean exchange ) throws IOException {

    /*--------------------------
      verify unique nickname & password
    --------------------------*/
    String nickname = exchange.getNickname();
    String password = exchange.getPassword();
    if ( select( nickname , password ) != null )
      throw new IOException( "Nickname/Password [" + nickname + "/" + password
          + "] already exists." );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into exchange "
        + "(NICKNAME,PASSWORD,MESSAGE,SPONSOR_CODE,LAST_HIT_DATE,"
        + "HIT_COUNT) " + "values (" + "'"
        + StringEscapeUtils.escapeSql( nickname ) + "'," + "'"
        + StringEscapeUtils.escapeSql( password ) + "'," + "'"
        + StringEscapeUtils.escapeSql( exchange.getMessage() ) + "'," + "'"
        + exchange.getSponsorCode() + "'," + "'"
        + Util.strFormat( exchange.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "'," + exchange.getHitCount() + ")";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select exchange record.
   * <p>
   * 
   * @param nickname
   * @param password
   * @return ExchangeBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public ExchangeBean select( String nickname , String password )
      throws IOException {

    ExchangeBean exchange = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM exchange " + "WHERE ( nickname = '"
        + StringEscapeUtils.escapeSql( nickname ) + "' ) AND ( password = '"
        + StringEscapeUtils.escapeSql( password ) + "' ) ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        exchange = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return exchange;

  } // select(nickname,password)

  /*****************************************************************************
   * Select a Vector of passwords for the given nickname.
   * <p>
   * 
   * @param nickname
   * @return A Vector of passwords.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector selectPasswords( String nickname ) throws IOException {

    Vector passwords = new Vector( 10 , 10 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT password " + "FROM exchange " + "WHERE nickname = '"
        + StringEscapeUtils.escapeSql( nickname ) + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        passwords.addElement( rs.getString( "password" ) );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    /*--------------------------
      success
    --------------------------*/
    return passwords;

  } // selectPasswords(String)

  /*****************************************************************************
   * Update exchange record.
   * <p>
   * 
   * @param ExchangeBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update( ExchangeBean exchange ) throws IOException {

    long exchangeID = exchange.getExchangeID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "update exchange set " + "NICKNAME='"
        + StringEscapeUtils.escapeSql( exchange.getNickname() ) + "',"
        + "PASSWORD='" + StringEscapeUtils.escapeSql( exchange.getPassword() )
        + "'," + "MESSAGE='"
        + StringEscapeUtils.escapeSql( exchange.getMessage() ) + "',"
        + "SPONSOR_CODE='"
        + StringEscapeUtils.escapeSql( exchange.getSponsorCode() ) + "',"
        + "LAST_HIT_DATE='"
        + Util.strFormat( exchange.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "'," + "HIT_COUNT=" + exchange.getHitCount() + " "
        + "where exchange_id=" + exchangeID;

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // update()

  /*****************************************************************************
   * Delete exchange record.
   * <p>
   * 
   * @param ExchangeBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( ExchangeBean exchange ) throws IOException {

    long exchangeID = exchange.getExchangeID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "DELETE FROM exchange WHERE exchange_id = " + exchangeID + " ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // delete()

  /*****************************************************************************
   * Load bean via http request to beepadmin server.
   * <p>
   * 
   * @param exchange
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String load( ExchangeBean exchange ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + exchange.getHost()
          + ":8080/beepadmin/exchange_action" );
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
          + URLEncoder.encode( exchange.getPhone() ) + "&nickname="
          + URLEncoder.encode( exchange.getNickname() ) + "&password="
          + URLEncoder.encode( exchange.getPassword() ) );
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

      // phone not found
      if ( response.startsWith( "ERROR" ) )
        return response;

      /*---------------------------
        handle exceptions
      ---------------------------*/
    } catch ( Exception e ) {
      return "ERROR: " + e.getMessage();
    }

    /*---------------------------
      parse response into bean
    ---------------------------*/
    StrTok st = new StrTok( response , "~" );
    exchange.setMessage( st.nextTok() );
    exchange.setLastHitDate( Util.stringToDate( st.nextTok() ) );
    exchange.setHitCount( Long.parseLong( st.nextTok() ) );

    // success
    return "OK";

  } // load()

  /*****************************************************************************
   * Save bean via http request to beepadmin server.
   * <p>
   * 
   * @param exchange
   * @return 0 if record found, -1 if record not found
   ****************************************************************************/
  public int save( ExchangeBean exchange ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + exchange.getHost()
          + ":8080/beepadmin/exchange_action" );
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
          + URLEncoder.encode( exchange.getPhone() ) + "&nickname="
          + URLEncoder.encode( exchange.getNickname() ) + "&password="
          + URLEncoder.encode( exchange.getPassword() ) + "&message="
          + URLEncoder.encode( exchange.getMessage() ) );
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

      // phone not found
      if ( response.startsWith( "ERROR" ) )
        return -1;

      /*---------------------------
        handle exceptions
      ---------------------------*/
    } catch ( Exception e ) {
      return -1;
    }

    // success
    return 0;

  } // save()

  /*****************************************************************************
   * Create exchange record via http service.
   * <p>
   * 
   * @param exchange
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String create( ExchangeBean exchange ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + exchange.getHost()
          + ":8080/beepadmin/exchange_action" );
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod( "POST" );
      httpConn.setDoInput( true );
      httpConn.setDoOutput( true );
      httpConn.setUseCaches( false );

      /*---------------------------
        send the request
      ---------------------------*/
      DataOutputStream os = new DataOutputStream( httpConn.getOutputStream() );
      os.writeBytes( "mode=insert" + "&phone="
          + URLEncoder.encode( exchange.getPhone() ) + "&nickname="
          + URLEncoder.encode( exchange.getNickname() ) + "&password="
          + URLEncoder.encode( exchange.getPassword().toUpperCase() ) );
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

      // phone not found
      if ( response.startsWith( "ERROR" ) )
        return response;

      /*---------------------------
        handle exceptions
      ---------------------------*/
    } catch ( Exception e ) {
      return "ERROR: " + e.getMessage();
    }

    // success
    return "OK";

  } // create()

  /*****************************************************************************
   * Destroy exchange record via http service.
   * <p>
   * 
   * @param exchange
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String destroy( ExchangeBean exchange ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + exchange.getHost()
          + ":8080/beepadmin/exchange_action" );
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod( "POST" );
      httpConn.setDoInput( true );
      httpConn.setDoOutput( true );
      httpConn.setUseCaches( false );

      /*---------------------------
        send the request
      ---------------------------*/
      DataOutputStream os = new DataOutputStream( httpConn.getOutputStream() );
      os.writeBytes( "mode=delete" + "&phone="
          + URLEncoder.encode( exchange.getPhone() ) + "&nickname="
          + URLEncoder.encode( exchange.getNickname() ) + "&password="
          + URLEncoder.encode( exchange.getPassword().toUpperCase() ) );
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

      // phone not found
      if ( response.startsWith( "ERROR" ) )
        return response;

      /*---------------------------
        handle exceptions
      ---------------------------*/
    } catch ( Exception e ) {
      return "ERROR: " + e.getMessage();
    }

    // success
    return "OK";

  } // destroy()

  /*****************************************************************************
   * Broadcast exchange record via http service.
   * <p>
   * 
   * @param exchange
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String broadcast( ExchangeBean exchange ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + exchange.getHost()
          + ":8080/beepadmin/exchange_action" );
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod( "POST" );
      httpConn.setDoInput( true );
      httpConn.setDoOutput( true );
      httpConn.setUseCaches( false );

      /*---------------------------
        send the request
      ---------------------------*/
      DataOutputStream os = new DataOutputStream( httpConn.getOutputStream() );
      os.writeBytes( "mode=broadcast" + "&phone="
          + URLEncoder.encode( exchange.getPhone() ) + "&nickname="
          + URLEncoder.encode( exchange.getNickname() ) + "&password="
          + URLEncoder.encode( exchange.getPassword().toUpperCase() ) );
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

      // phone not found
      if ( response.startsWith( "ERROR" ) )
        return response;

      /*---------------------------
        handle exceptions
      ---------------------------*/
    } catch ( Exception e ) {
      return "ERROR: " + e.getMessage();
    }

    // success
    return "OK";

  } // broadcast()

  /*****************************************************************************
   * Get passwords via http service.
   * <p>
   * 
   * @param exchange
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String getPasswords( ExchangeBean exchange ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + exchange.getHost()
          + ":8080/beepadmin/exchange_action" );
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod( "POST" );
      httpConn.setDoInput( true );
      httpConn.setDoOutput( true );
      httpConn.setUseCaches( false );

      /*---------------------------
        send the request
      ---------------------------*/
      DataOutputStream os = new DataOutputStream( httpConn.getOutputStream() );
      os.writeBytes( "mode=selectPasswords" + "&phone="
          + URLEncoder.encode( exchange.getPhone() ) + "&nickname="
          + URLEncoder.encode( exchange.getNickname() ) );
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

  } // getPasswords()

  /*****************************************************************************
   * Rename password via http service.
   * <p>
   * 
   * @param exchange
   * @param newPasscode
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String renamePasscode( ExchangeBean exchange , String newPasscode ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + exchange.getHost()
          + ":8080/beepadmin/exchange_action" );
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod( "POST" );
      httpConn.setDoInput( true );
      httpConn.setDoOutput( true );
      httpConn.setUseCaches( false );

      /*---------------------------
        send the request
      ---------------------------*/
      DataOutputStream os = new DataOutputStream( httpConn.getOutputStream() );
      os.writeBytes( "mode=rename" + "&phone="
          + URLEncoder.encode( exchange.getPhone() ) + "&nickname="
          + URLEncoder.encode( exchange.getNickname() ) + "&password="
          + URLEncoder.encode( exchange.getPassword() ) + "&param1="
          + URLEncoder.encode( newPasscode ) );
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

      // phone not found
      if ( response.startsWith( "ERROR" ) )
        return response;

      /*---------------------------
        handle exceptions
      ---------------------------*/
    } catch ( Exception e ) {
      return "ERROR: " + e.getMessage();
    }

    // success
    return "OK";

  } // renamePasscode()

  /*****************************************************************************
   * Populate mobile user bean.
   * <p>
   ****************************************************************************/
  private ExchangeBean populateBean( ResultSet rs ) throws SQLException {
    ExchangeBean exchange = new ExchangeBean();
    exchange.setExchangeID( (long) rs.getDouble( "exchange_id" ) );
    exchange.setNickname( rs.getString( "nickname" ) );
    exchange.setPassword( rs.getString( "password" ) );
    exchange.setMessage( rs.getString( "message" ) );
    exchange.setSponsorCode( rs.getString( "sponsor_code" ) );
    exchange.setLastHitDate( Util.getUtilDate( rs.getDate( "last_hit_date" ) ,
        rs.getTime( "last_hit_date" ) ) );
    exchange.setHitCount( (long) rs.getDouble( "hit_count" ) );
    return exchange;
  }

} // eof
