package com.beepcast.model.userLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.model.util.DateTimeFormat;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;
import com.mysql.jdbc.CommunicationsException;

public class UserLogViewFileDAO {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "UserLogViewFileDAO" );

  static final int BUFFER = 2048;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private DatabaseLibrary dbLib;
  private String sql;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public UserLogViewFileDAO( int clientType , int masterClientId ,
      String findUserId , String findActionText , Date dateStarted ,
      Date dateFinished ) {
    dbLib = DatabaseLibrary.getInstance();

    // compose sql
    sql = UserLogViewQuery.sql( clientType , masterClientId , findUserId ,
        findActionText , dateStarted , dateFinished );
    DLog.debug( lctx , "Composed sql " + sql );

  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public long exportToFile( String reportFileNameWithoutExt ) {
    long totalRecords = 0;

    long deltaTime = System.currentTimeMillis();
    DLog.debug( lctx , "Started exporting to file" );

    DataOutputStream dos = openFile( reportFileNameWithoutExt );
    if ( dos == null ) {
      DLog.warning( lctx , "Failed to open report file" );
      return totalRecords;
    }

    if ( !writeHeader( dos ) ) {
      DLog.warning( lctx , "Failed to write header report" );
    } else {
      totalRecords += writeRecords( dos );
    }

    if ( !closeFile( reportFileNameWithoutExt , dos ) ) {
      DLog.warning( lctx , "Failed to close report file" );
      return totalRecords;
    }

    deltaTime = System.currentTimeMillis() - deltaTime;
    DLog.debug( lctx , "Finished exporting to file ( took " + deltaTime
        + " ms ) " );
    return totalRecords;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  private long writeRecord( DataOutputStream dos , ResultSet rs ) {
    long totalRecords = 0;
    if ( dos == null ) {
      return totalRecords;
    }
    if ( rs == null ) {
      return totalRecords;
    }
    try {

      // read params
      int id = rs.getInt( "ul.id" );
      String companyName = rs.getString( "c.company_name" );
      String userId = rs.getString( "u.user_id" );
      String actionText = rs.getString( "ul.action" );
      Date actionDate = DateTimeFormat.convertToDate( rs
          .getString( "ul.date_inserted" ) );

      // clean params
      String idStr = Integer.toString( id );
      companyName = ( companyName == null ) ? "" : companyName.trim();
      userId = ( userId == null ) ? "" : userId.trim();
      actionText = ( actionText == null ) ? "" : actionText.trim();
      String actionDateStr = DateTimeFormat.convertToString( actionDate );
      actionDateStr = ( actionDateStr == null ) ? "" : actionDateStr.trim();

      // prepare the csv format
      StringBuffer csvLines = new StringBuffer();
      csvLines.append( StringEscapeUtils.escapeCsv( idStr ) );
      csvLines.append( "," );
      csvLines.append( StringEscapeUtils.escapeCsv( companyName ) );
      csvLines.append( "," );
      csvLines.append( StringEscapeUtils.escapeCsv( userId ) );
      csvLines.append( "," );
      csvLines.append( StringEscapeUtils.escapeCsv( actionText ) );
      csvLines.append( "," );
      csvLines.append( StringEscapeUtils.escapeCsv( actionDateStr ) );
      csvLines.append( "\n" );

      // write records into file
      dos.writeBytes( csvLines.toString() );

      // increase total records
      totalRecords = totalRecords + 1;

    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to write record , " + e );
    }
    return totalRecords;
  }

  private long writeRecords( DataOutputStream dos ) {
    long totalRecords = 0;
    if ( dos == null ) {
      return totalRecords;
    }
    String databaseName = "transactiondb";
    ConnectionWrapper conn = dbLib.getReaderConnection( databaseName );
    if ( ( conn != null ) && ( conn.isConnected() ) ) {
      Statement statement = null;
      ResultSet resultSet = null;
      try {
        statement = conn.createStatement();
        if ( statement != null ) {
          resultSet = statement.executeQuery( sql );
        }
        if ( resultSet != null ) {
          while ( resultSet.next() ) {
            totalRecords += writeRecord( dos , resultSet );
          }
        }
      } catch ( CommunicationsException communicationsException ) {
        DLog.error( lctx , "[" + databaseName + "] Database query failed , "
            + communicationsException );
      } catch ( SQLException sqlException ) {
        DLog.error( lctx , "[" + databaseName + "] Database query failed , "
            + sqlException );
      }
      try {
        if ( resultSet != null ) {
          resultSet.close();
          resultSet = null;
        }
      } catch ( SQLException e ) {
        DLog.error( lctx , "[" + databaseName
            + "] Failed to close the ResultSet object , " + e );
      }
      try {
        if ( statement != null ) {
          statement.close();
          statement = null;
        }
      } catch ( SQLException e ) {
        DLog.error( lctx , "[" + databaseName
            + "] Failed to close the Statement object , " + e );
      }
      conn.disconnect( true );
    }
    return totalRecords;
  }

  private boolean writeHeader( DataOutputStream dos ) {
    boolean result = false;
    if ( dos == null ) {
      return result;
    }
    try {
      dos.writeBytes( "TrackId,CompanyName,UserId" );
      dos.writeBytes( ",ActionText,ActionDate\n" );
      result = true;
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to write file , " + e );
    }
    return result;
  }

  private DataOutputStream openFile( String reportFileNameWithoutExt ) {
    DataOutputStream dos = null;
    try {

      // put txt extension
      String txtFileName = reportFileNameWithoutExt + ".csv";
      DLog.debug( lctx , "Create text csv file = " + txtFileName );

      // prepare txt file object
      File file = new File( txtFileName );

      // open and return txt stream file
      FileOutputStream fos = new FileOutputStream( file );
      dos = new DataOutputStream( fos );

    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to open file , " + e );
    }
    return dos;
  }

  private boolean closeFile( String reportFileNameWithoutExt ,
      DataOutputStream dos ) {
    boolean result = false;
    if ( dos == null ) {
      return result;
    }
    try {

      // put txt extension
      String txtFileName = reportFileNameWithoutExt + ".csv";

      // save txt file
      DLog.debug( lctx , "Saved text file = " + txtFileName );
      dos.close();

      // prepare text file object
      File file = new File( txtFileName );

      // generate to zip file
      String zipFilename = "";
      zipFilename += FilenameUtils.removeExtension( file.getAbsolutePath() );
      zipFilename += ".zip";

      DLog.debug( lctx , "Convert text file to zip file = " + zipFilename );

      FileOutputStream dst = new FileOutputStream( zipFilename );
      CheckedOutputStream cos = new CheckedOutputStream( dst , new Adler32() );
      ZipOutputStream zos = new ZipOutputStream( new BufferedOutputStream( cos ) );

      byte data[] = new byte[BUFFER];
      FileInputStream fis = new FileInputStream( file );
      BufferedInputStream ori = new BufferedInputStream( fis , BUFFER );
      ZipEntry zipEntry = new ZipEntry( FilenameUtils.getName( file
          .getAbsolutePath() ) );
      zos.putNextEntry( zipEntry );
      int count = 0;
      while ( ( count = ori.read( data , 0 , BUFFER ) ) != -1 ) {
        zos.write( data , 0 , count );
      }
      ori.close();

      // save zip file
      DLog.debug( lctx , "Saved zip file = " + zipFilename );
      zos.close();

      // delete text file
      DLog.debug( lctx , "Delete text file = " + txtFileName );
      file.delete();

      result = true;
    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to open file , " + e );
    }
    return result;
  }

}
