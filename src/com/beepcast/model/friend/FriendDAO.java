package com.beepcast.model.friend;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class FriendDAO {

  static final DLogContext lctx = new SimpleContext( "FriendDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert new friend record.
   * <p>
   * 
   * @param friend
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( FriendBean friend ) throws IOException {

    /*--------------------------
      verify unique record
    --------------------------*/
    String senderPhone = friend.getSenderPhone();
    String friendPhone = friend.getFriendPhone();
    long eventID = friend.getEventID();
    long exchangeID = friend.getExchangeID();
    String criteria = "sender_phone='" + senderPhone + "' and friend_phone='"
        + friendPhone + "' and event_id=" + eventID + " and exchange_id="
        + exchangeID;
    Vector friends = select( criteria );
    if ( friends.size() != 0 )
      return;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into friend "
        + "(SENDER_PHONE,FRIEND_PHONE,EVENT_ID,EXCHANGE_ID) " + "values ("
        + "'" + senderPhone + "'," + "'" + friendPhone + "'," + eventID + ","
        + exchangeID + ")";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select a Vector of friend records.
   * <p>
   * 
   * @param criteria
   *          Example: "sender_phone='+6596690924'"
   * @return A Vector of FriendBean objects.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Vector select( String criteria ) throws IOException {

    Vector friends = new Vector( 1000 , 1000 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM friend ";
    if ( criteria != null && criteria.length() > 0 )
      sql += "WHERE " + criteria;

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        friends.addElement( populateBean( rs ) );
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
    return friends;

  } // select(String)

  /*****************************************************************************
   * Populate mobile user bean.
   * <p>
   ****************************************************************************/
  private FriendBean populateBean( ResultSet rs ) throws SQLException {
    FriendBean friend = new FriendBean();
    friend.setSenderPhone( rs.getString( "sender_phone" ) );
    friend.setFriendPhone( rs.getString( "friend_phone" ) );
    friend.setEventID( (long) rs.getDouble( "event_id" ) );
    friend.setExchangeID( (long) rs.getDouble( "exchange_id" ) );
    return friend;
  }

} // eof
