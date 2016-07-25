package com.beepcast.model.merchant;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class MerchantDAO {

  static final DLogContext lctx = new SimpleContext( "MerchantDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert new merchant record.
   * <p>
   * 
   * @param merchant
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( MerchantBean merchant ) throws IOException {

    /*--------------------------
      verify unique store name
    --------------------------*/
    String storeName = merchant.getStoreName();
    if ( select( storeName ) != null )
      throw new IOException( "Store name [" + storeName + "] already exists." );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into merchant "
        + "(STORE_NAME,ADDRESS,PHONE) values (" + "'"
        + StringEscapeUtils.escapeSql( storeName ) + "'," + "'"
        + StringEscapeUtils.escapeSql( merchant.getAddress() ) + "'," + "'"
        + merchant.getPhone() + "')";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select merchant record.
   * <p>
   * 
   * @param merchantID
   * @return MerchantBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public MerchantBean select( long merchantID ) throws IOException {

    MerchantBean merchant = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM merchant WHERE merchant_id = " + merchantID;

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        merchant = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return merchant;

  } // select(long)

  /*****************************************************************************
   * Select merchant record.
   * <p>
   * 
   * @param store
   *          name
   * @return MerchantBean instance if found, null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public MerchantBean select( String storeName ) throws IOException {

    MerchantBean merchant = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM merchant WHERE store_name = '"
        + StringEscapeUtils.escapeSql( storeName ) + "' ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        merchant = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return merchant;

  } // select(string)

  /*****************************************************************************
   * Select all merchant records.
   * <p>
   * 
   * @return Hashtable of MerchantBean, null if none found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public Hashtable select() throws IOException {

    Hashtable merchants = new Hashtable( 100 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "select * from merchant";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        MerchantBean merchant = populateBean( rs );
        merchants.put( "" + merchant.getMerchantID() , merchant );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    return merchants;

  } // select()

  /*****************************************************************************
   * Update merchant record.
   * <p>
   * 
   * @param MerchantBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update( MerchantBean merchant ) throws IOException {

    long merchantID = merchant.getMerchantID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "update merchant set " + "STORE_NAME='"
        + StringEscapeUtils.escapeSql( merchant.getStoreName() ) + "',"
        + "ADDRESS='" + StringEscapeUtils.escapeSql( merchant.getAddress() )
        + "'," + "PHONE='" + merchant.getPhone() + "' " + "where merchant_id="
        + merchantID;

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // update()

  /*****************************************************************************
   * Populate merchant bean.
   * <p>
   ****************************************************************************/
  private MerchantBean populateBean( ResultSet rs ) throws SQLException {
    MerchantBean merchant = new MerchantBean();
    merchant.setMerchantID( (long) rs.getDouble( "merchant_id" ) );
    merchant.setStoreName( rs.getString( "store_name" ) );
    merchant.setAddress( rs.getString( "address" ) );
    merchant.setPhone( rs.getString( "phone" ) );
    return merchant;
  }

} // eof
