package com.beepcast.service.progress;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

/*******************************************************************************
 * Progress DAO.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class ProgressDAO {

  static final DLogContext lctx = new SimpleContext( "ProgressDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert new progress record.
   * <p>
   * 
   * @param progressRecord
   * @return progressID
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public long insert( ProgressBean progressRecord ) throws IOException {

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into progress "
        + "(MINIMUM,MAXIMUM,VALUE,ERROR_MESSAGE) " + "values ("
        + progressRecord.getMinimum() + "," + progressRecord.getMaximum() + ","
        + progressRecord.getValue() + "," + "'"
        + progressRecord.getErrorMessage() + "')";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

    /*--------------------------
      return progress ID
    --------------------------*/
    return getMaxProgressID();

  } // insert()

  /*****************************************************************************
   * Get max progress id.
   * <p>
   * 
   * @return Max progress id.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public long getMaxProgressID() throws IOException {

    long maxProgressID = 0L;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "select max(progress_id) from progress";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        maxProgressID = (long) rs.getDouble( 1 );
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
    return maxProgressID;

  } // getMaxProgressID()

  /*****************************************************************************
   * Select progress record.
   * <p>
   * 
   * @param progressID
   * @return ProgressBean, or null if not found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public ProgressBean select( long progressID ) throws IOException {

    ProgressBean progressRecord = null;

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "select * from progress where progress_ID=" + progressID;

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        progressRecord = populateBean( rs );
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
    return progressRecord;

  } // select

  /*****************************************************************************
   * Update progress record.
   * <p>
   * 
   * @param ProgressBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update( ProgressBean progressRecord ) throws IOException {

    long progressID = progressRecord.getProgressID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "update progress set " + "MINIMUM="
        + progressRecord.getMinimum() + "," + "MAXIMUM="
        + progressRecord.getMaximum() + "," + "VALUE="
        + progressRecord.getValue() + "," + "ERROR_MESSAGE='"
        + progressRecord.getErrorMessage() + "' " + "where progress_id="
        + progressID;

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // update()

  /*****************************************************************************
   * Delete progress record.
   * <p>
   * 
   * @param ProgressBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( ProgressBean progressRecord ) throws IOException {

    long progressID = progressRecord.getProgressID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "delete from progress where progress_id=" + progressID;

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // delete()

  /*****************************************************************************
   * Populate gateway log bean.
   * <p>
   ****************************************************************************/
  private ProgressBean populateBean( ResultSet rs ) throws SQLException {

    ProgressBean progressRecord = new ProgressBean();

    progressRecord.setProgressID( (long) rs.getDouble( "progress_id" ) );
    progressRecord.setMinimum( (long) rs.getDouble( "minimum" ) );
    progressRecord.setMaximum( (long) rs.getDouble( "maximum" ) );
    progressRecord.setValue( (long) rs.getDouble( "value" ) );
    progressRecord.setErrorMessage( rs.getString( "error_message" ) );

    return progressRecord;
  }

} // eof
