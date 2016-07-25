package com.beepcast.model.exchange;

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

/*******************************************************************************
 * Subscriber DAO.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class SubscriberDAO {

  static final DLogContext lctx = new SimpleContext( "SubscriberDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert a new subscriber record.
   * <p>
   * 
   * @param subscriber
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( SubscriberBean subscriber ) throws IOException {

    String phone = subscriber.getPhone();
    long exchangeID = subscriber.getExchangeID();

    /*--------------------------
      verify unique subscriber
    --------------------------*/
    if ( select( phone , exchangeID ) != null )
      throw new IOException( "Subscriber phone/exchangeID [" + phone + "/"
          + exchangeID + "] already exists." );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "INSERT INTO exchange_subscriber ( phone , exchange_id ) "
        + "VALUES ( '" + StringEscapeUtils.escapeSql( phone ) + "' , "
        + exchangeID + " ) ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select subscriber record.
   * <p>
   * 
   * @param phone
   * @param exchangeID
   * @return SubscriberBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public SubscriberBean select( String phone , long exchangeID )
      throws IOException {

    SubscriberBean subscriber = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM exchange_subscriber WHERE ( phone = '"
        + StringEscapeUtils.escapeSql( phone ) + "' ) AND ( exchange_id = "
        + exchangeID + " ) ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        subscriber = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return subscriber;

  } // select(String,long)

  /*****************************************************************************
   * Select subscriber phones for a given exchange id.
   * <p>
   * 
   * @param exchangeID
   * @return Vector of subscriber phones, or null if none found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector select( long exchangeID ) throws IOException {

    Vector phones = new Vector( 100 , 100 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT phone " + "FROM exchange_subscriber "
        + "WHERE exchange_id = " + exchangeID + " ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        phones.addElement( rs.getString( "phone" ) );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return phones;

  } // select(long)

  /*****************************************************************************
   * Delete subscriber record.
   * <p>
   * 
   * @param SubscriberBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( SubscriberBean subscriber ) throws IOException {

    String phone = subscriber.getPhone();
    long exchangeID = subscriber.getExchangeID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "DELETE FROM exchange_subscriber " + "WHERE ( phone = '"
        + StringEscapeUtils.escapeSql( phone ) + "' ) AND ( exchange_id = "
        + exchangeID + " ) ";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // delete()

  /*****************************************************************************
   * Populate subscriber bean.
   * <p>
   ****************************************************************************/
  private SubscriberBean populateBean( ResultSet rs ) throws SQLException {
    SubscriberBean subscriber = new SubscriberBean();
    subscriber.setPhone( rs.getString( "phone" ) );
    subscriber.setExchangeID( (long) rs.getDouble( "exchange_id" ) );
    return subscriber;
  }

} // eof
