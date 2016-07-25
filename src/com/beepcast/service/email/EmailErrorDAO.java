package com.beepcast.service.email;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

/*******************************************************************************
 * Email Error DAO.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class EmailErrorDAO {

  static final DLogContext lctx = new SimpleContext( "EmailErrorDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Insert a new email error record.
   * <p>
   * 
   * @param emailError
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert( EmailBean emailError ) throws IOException {

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "insert into email_error "
        + "(TO_LIST,_FROM,SUBJECT,BODY,ATTACHMENT,NUM_RETRIES,ERROR_MESSAGE)"
        + " values (" + "'" + StringUtils.join( emailError.getToList() , "," )
        + "'," + "'" + emailError.getFrom() + "'," + "'"
        + emailError.getSubject() + "'," + "'" + emailError.getBody() + "',"
        + "'" + emailError.getAttachment() + "'," + emailError.getNumRetries()
        + "," + "'" + emailError.getErrorMessage() + "')";

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // insert()

  /*****************************************************************************
   * Select all email error records.
   * <p>
   * 
   * @return Array of EmailBean, or null if none found.
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public EmailBean[] select() throws IOException {

    Vector v = new Vector( 100 , 100 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "select * from email_error";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        EmailBean emailError = populateBean( rs );
        v.addElement( emailError );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    /*--------------------------
      return array of email errors
    --------------------------*/
    EmailBean emailErrors[] = null;
    if ( v.size() > 0 ) {
      emailErrors = new EmailBean[v.size()];
      for ( int i = 0 ; i < emailErrors.length ; i++ )
        emailErrors[i] = (EmailBean) v.elementAt( i );
    }
    return emailErrors;

  } // select()

  /*****************************************************************************
   * Update email error record.
   * <p>
   * 
   * @param EmailBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update( EmailBean emailError ) throws IOException {

    long emailErrorID = emailError.getEmailErrorID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "update email_error set " + "TO_LIST='"
        + StringUtils.join( emailError.getToList() , "," ) + "'," + "_FROM='"
        + emailError.getFrom() + "'," + "SUBJECT='" + emailError.getSubject()
        + "'," + "BODY='" + emailError.getBody() + "'," + "ATTACHMENT='"
        + emailError.getAttachment() + "'," + "NUM_RETRIES="
        + emailError.getNumRetries() + "," + "ERROR_MESSAGE='"
        + emailError.getErrorMessage() + "' " + "where email_error_id="
        + emailErrorID;

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // update()

  /*****************************************************************************
   * Delete email error record.
   * <p>
   * 
   * @param EmailBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void delete( EmailBean emailError ) throws IOException {

    long emailErrorID = emailError.getEmailErrorID();

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "delete from email_error where email_error_id=" + emailErrorID;

    /*--------------------------
      execute query
    --------------------------*/
    DLog.debug( lctx , "Perform " + sql );
    dbLib.executeQuery( "profiledb" , sql );
    // new SQL().executeUpdate( sql );

  } // delete()

  /*****************************************************************************
   * Populate email error bean.
   * <p>
   ****************************************************************************/
  private EmailBean populateBean( ResultSet rs ) throws SQLException {

    EmailBean emailError = new EmailBean();

    emailError.setEmailErrorID( (int) rs.getDouble( "email_error_id" ) );
    emailError.setToList( StringUtils.split( rs.getString( "to_list" ) , "," ) );
    emailError.setFrom( rs.getString( "_from" ) );
    emailError.setSubject( rs.getString( "subject" ) );
    emailError.setBody( rs.getString( "body" ) );
    emailError.setNumRetries( (int) rs.getDouble( "num_retries" ) );
    emailError.setErrorMessage( rs.getString( "error_messaage" ) );

    return emailError;
  }

} // eof

