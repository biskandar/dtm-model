package com.beepcast.model.clientFile;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientFileDAO {

  static final DLogContext lctx = new SimpleContext( "ClientFileDAO" );

  private DatabaseLibrary dbLib;

  public ClientFileDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insertBean( ClientFileBean bean ) {
    boolean result = false;

    int clientId = bean.getClientId();
    String caption = bean.getCaption();
    String fileName = bean.getFileName();
    String fileType = bean.getFileType();
    String webLink = bean.getWebLink();
    int sizeBytes = bean.getSizeBytes();
    int length = bean.getLength();
    int width = bean.getWidth();

    caption = ( caption == null ) ? "" : caption.trim();
    fileName = ( fileName == null ) ? "" : fileName.trim();
    fileType = ( fileType == null ) ? "" : fileType.trim();
    webLink = ( webLink == null ) ? "" : webLink.trim();

    String sqlInsert = "INSERT INTO client_file ( client_id , caption ";
    sqlInsert += ", file_name , file_type , web_link , size_bytes ";
    sqlInsert += ", length , width , active , date_inserted , date_updated ) ";
    String sqlValues = "VALUES ( " + clientId + " , '"
        + StringEscapeUtils.escapeSql( caption ) + "' , '"
        + StringEscapeUtils.escapeSql( fileName ) + "' , '"
        + StringEscapeUtils.escapeSql( fileType ) + "' , '"
        + StringEscapeUtils.escapeSql( webLink ) + "' , " + sizeBytes + " , "
        + length + " , " + width + " , 1 , NOW() , NOW() ) ";

    String sql = sqlInsert + sqlValues;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public ClientFileBean selectBean( int clientFileId ) {
    ClientFileBean bean = null;

    String sqlSelectFrom = ClientFileQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( id = " + clientFileId + " ) ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelectFrom + sqlWhere + sqlLimit;

    bean = populateRecord( sql );

    return bean;
  }

  public ClientFileBean selectBeanByCaption( int clientId , String caption ) {
    ClientFileBean bean = null;

    String sqlSelectFrom = ClientFileQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    sqlWhere += "AND ( caption = '" + StringEscapeUtils.escapeSql( caption )
        + "' ) ";
    String sqlOrder = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    bean = populateRecord( sql );

    return bean;
  }

  public ClientFileBean selectBeanByWebLink( int clientId , String webLink ) {
    ClientFileBean bean = null;

    String sqlSelectFrom = ClientFileQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    sqlWhere += "AND ( web_link = '" + StringEscapeUtils.escapeSql( webLink )
        + "' ) ";
    String sqlOrder = "ORDER BY id DESC ";
    String sqlLimit = "LIMIT 1 ";

    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    bean = populateRecord( sql );

    return bean;
  }

  public boolean updateBean( ClientFileBean bean ) {
    boolean result = false;

    int id = bean.getId();
    String caption = bean.getCaption();
    String fileName = bean.getFileName();
    String fileType = bean.getFileType();
    String webLink = bean.getWebLink();
    int sizeBytes = bean.getSizeBytes();
    int length = bean.getLength();
    int width = bean.getWidth();

    caption = ( caption == null ) ? "" : caption.trim();
    fileName = ( fileName == null ) ? "" : fileName.trim();
    fileType = ( fileType == null ) ? "" : fileType.trim();
    webLink = ( webLink == null ) ? "" : webLink.trim();

    String sqlUpdate = "UPDATE client_file ";
    String sqlSet = "SET caption = '" + StringEscapeUtils.escapeSql( caption )
        + "' , file_name = '" + StringEscapeUtils.escapeSql( fileName )
        + "' , file_type = '" + StringEscapeUtils.escapeSql( fileType )
        + "' , web_link = '" + StringEscapeUtils.escapeSql( webLink )
        + "' , size_bytes = " + sizeBytes + " , length = " + length
        + " , width = " + width + " ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    DLog.debug( lctx , "Perform " + sql );

    Integer irslt = dbLib.executeQuery( "transactiondb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  private ClientFileBean populateRecord( String sql ) {
    ClientFileBean bean = null;

    QueryResult qr = dbLib.simpleQuery( "transactiondb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }

    QueryItem qi = null;
    Iterator it = qr.iterator();
    if ( it.hasNext() ) {
      qi = (QueryItem) it.next();
    }
    if ( qi == null ) {
      return bean;
    }

    bean = ClientFileQuery.populateRecord( qi );

    return bean;
  }

}
