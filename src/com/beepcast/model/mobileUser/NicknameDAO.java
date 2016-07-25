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
import com.beepcast.util.StrTok;
import com.beepcast.util.Util;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class NicknameDAO {

  static final DLogContext lctx = new SimpleContext( "NicknameDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert new nickname record.
   * <p>
   * 
   * @param nicknameBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( NicknameBean nicknameBean ) throws IOException {

    /*--------------------------
      verify unique record
    --------------------------*/
    String nickname = nicknameBean.getNickname();
    if ( select( nickname ) != null )
      throw new IOException( "Nickname [" + nickname + "] already exists." );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "INSERT INTO nickname "
        + "(nickname,phone,last_hit_date,hit_count) "
        + "VALUES ( '"
        + StringEscapeUtils.escapeSql( nickname )
        + "','"
        + StringEscapeUtils.escapeSql( nicknameBean.getPhone() )
        + "','"
        + Util
            .strFormat( nicknameBean.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "'," + nicknameBean.getHitCount() + " ) ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select mobile user record.
   * <p>
   * 
   * @param nickname
   * @return NicknameBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public NicknameBean select( String nickname ) throws IOException {

    NicknameBean nicknameBean = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * " + "FROM nickname " + "WHERE nickname = '"
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
        nicknameBean = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return nicknameBean;

  } // select(nickname)

  /*****************************************************************************
   * Select a Vector of nicknames for the given phone.
   * <p>
   * 
   * @param phone
   * @return A Vector of nicknames.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector selectNicknames( String phone ) throws IOException {

    Vector nicknames = new Vector( 10 , 10 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT nickname FROM nickname WHERE phone = '"
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
        nicknames.addElement( rs.getString( "nickname" ) );
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
    return nicknames;

  } // selectNicknames(String)

  /*****************************************************************************
   * Update nickname record.
   * <p>
   * 
   * @param NicknameBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update( NicknameBean nicknameBean ) throws IOException {

    long nicknameID = nicknameBean.getNicknameID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "UPDATE nickname "
        + "SET NICKNAME = '"
        + StringEscapeUtils.escapeSql( nicknameBean.getNickname() )
        + "' , PHONE = '"
        + nicknameBean.getPhone()
        + "' , LAST_HIT_DATE = '"
        + Util
            .strFormat( nicknameBean.getLastHitDate() , "yyyy-mm-dd hh:nn:ss" )
        + "' , HIT_COUNT = " + nicknameBean.getHitCount() + " "
        + "WHERE nickname_id = " + nicknameID + " ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // update()

  /*****************************************************************************
   * Delete nickname record.
   * <p>
   * 
   * @param NicknameBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( NicknameBean nicknameBean ) throws IOException {

    String nickname = nicknameBean.getNickname();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "DELETE FROM nickname WHERE nickname = '" + nickname + "' ";

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
   * @param nicknameBean
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String load( NicknameBean nickname ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + nickname.getHost()
          + ":8080/beepadmin/nickname_action" );
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
          + URLEncoder.encode( nickname.getPhone() ) + "&nickname="
          + URLEncoder.encode( nickname.getNickname() ) );
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
    StrTok st = new StrTok( response , "," );
    nickname.setLastHitDate( Util.stringToDate( st.nextTok() ) );
    nickname.setHitCount( Long.parseLong( st.nextTok() ) );

    // success
    return "OK";

  } // load()

  /*****************************************************************************
   * Get nicknames via http service.
   * <p>
   * 
   * @param nicknameBean
   * @return List of nicknames, or "ERROR:" + error message
   ****************************************************************************/
  public String getNicknames( NicknameBean nicknameBean ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + nicknameBean.getHost()
          + ":8080/beepadmin/nickname_action" );
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod( "POST" );
      httpConn.setDoInput( true );
      httpConn.setDoOutput( true );
      httpConn.setUseCaches( false );

      /*---------------------------
        send the request
      ---------------------------*/
      DataOutputStream os = new DataOutputStream( httpConn.getOutputStream() );
      os.writeBytes( "mode=selectNicknames" + "&phone="
          + URLEncoder.encode( nicknameBean.getPhone() ) );
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

  } // getNicknames()

  /*****************************************************************************
   * Create nickname record via http service.
   * <p>
   * 
   * @param nicknameBean
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String create( NicknameBean nicknameBean ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + nicknameBean.getHost()
          + ":8080/beepadmin/nickname_action" );
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
          + URLEncoder.encode( nicknameBean.getPhone() ) + "&nickname="
          + URLEncoder.encode( nicknameBean.getNickname().toUpperCase() ) );
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
   * Destroy nickname record via http service.
   * <p>
   * 
   * @param nicknameBean
   * @return "OK" if record created, or "ERROR:" + error message
   ****************************************************************************/
  public String destroy( NicknameBean nicknameBean ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + nicknameBean.getHost()
          + ":8080/beepadmin/nickname_action" );
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
          + URLEncoder.encode( nicknameBean.getPhone() ) + "&nickname="
          + URLEncoder.encode( nicknameBean.getNickname().toUpperCase() ) );
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
   * Rename password via http service.
   * <p>
   * 
   * @param nicknameBean
   * @param newNickname
   * @return "OK" if record found, or "ERROR:" + error message
   ****************************************************************************/
  public String renameNickname( NicknameBean nicknameBean , String newNickname ) {

    String response = "";

    try {
      /*---------------------------
        connect to web server
      ---------------------------*/
      URL url = new URL( "http://" + nicknameBean.getHost()
          + ":8080/beepadmin/nickname_action" );
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
          + URLEncoder.encode( nicknameBean.getPhone() ) + "&nickname="
          + URLEncoder.encode( nicknameBean.getNickname().toUpperCase() )
          + "&param1=" + URLEncoder.encode( newNickname.toUpperCase() ) );
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

  } // renameNickname()

  /*****************************************************************************
   * Populate mobile user bean.
   * <p>
   ****************************************************************************/
  private NicknameBean populateBean( ResultSet rs ) throws SQLException {
    NicknameBean nicknameBean = new NicknameBean();
    nicknameBean.setNicknameID( (long) rs.getDouble( "nickname_id" ) );
    nicknameBean.setNickname( rs.getString( "nickname" ) );
    nicknameBean.setPhone( rs.getString( "phone" ) );
    nicknameBean.setLastHitDate( Util.getUtilDate(
        rs.getDate( "last_hit_date" ) , rs.getTime( "last_hit_date" ) ) );
    nicknameBean.setHitCount( (long) rs.getDouble( "hit_count" ) );
    return nicknameBean;
  }

} // eof
