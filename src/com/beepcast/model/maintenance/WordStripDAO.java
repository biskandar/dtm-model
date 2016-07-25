package com.beepcast.model.maintenance;

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

/*******************************************************************************
 * Wrod Strip DAO.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class WordStripDAO {

  static final DLogContext lctx = new SimpleContext( "WordStripDAO" );

  private DatabaseLibrary dbLib = DatabaseLibrary.getInstance();

  /*****************************************************************************
   * Select all words to be filtered.
   * <p>
   * 
   * @return WordStripBean instance
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public WordStripBean select() throws IOException {

    Vector v = new Vector( 100 , 100 );

    /*--------------------------
      build SQL string
    --------------------------*/
    String sql = "SELECT * FROM word_strip ";

    /*--------------------------
      execute query
    --------------------------*/
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        v.addElement( rs.getString( "word" ) );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException sqle ) {
      throw new IOException( sqle.getMessage() + " SQL=" + sql );
    } finally {
      conn.disconnect( true );
    }

    /*--------------------------
      return bean
    --------------------------*/
    String words[] = new String[v.size()];
    for ( int i = 0 ; i < words.length ; i++ )
      words[i] = (String) v.elementAt( i );
    WordStripBean wordStrip = new WordStripBean();
    wordStrip.setWords( words );

    return wordStrip;

  } // select()

} // eof
