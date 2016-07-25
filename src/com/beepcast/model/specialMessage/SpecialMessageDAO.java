package com.beepcast.model.specialMessage;

import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class SpecialMessageDAO {

  static final DLogContext lctx = new SimpleContext( "SpecialMessageDAO" );

  private DatabaseLibrary dbLib;

  public SpecialMessageDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public boolean insert( SpecialMessageBean bean ) {
    boolean result = false;

    // clean params

    String type = bean.getType();
    String name = bean.getName();
    String content = bean.getContent();
    String description = bean.getDescription();

    type = ( type == null ) ? "" : type;
    name = ( name == null ) ? "" : name;
    content = ( content == null ) ? "" : content;
    description = ( description == null ) ? "" : description;

    // compose sql

    String sqlInsert = "INSERT INTO special_message ";
    sqlInsert += "(`type` , name , content , description ";
    sqlInsert += ", active , date_inserted , date_updated ) ";
    String sqlValues = "VALUES ('" + StringEscapeUtils.escapeSql( type )
        + "','" + StringEscapeUtils.escapeSql( name ) + "','"
        + StringEscapeUtils.escapeSql( content ) + "','"
        + StringEscapeUtils.escapeSql( description ) + "',1,NOW(),NOW()) ";
    String sql = sqlInsert + sqlValues;

    // execute sql

    DLog.debug( lctx , "Perform " + sql );
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public SpecialMessageBean select( String type , String name ) {
    SpecialMessageBean bean = null;
    if ( type == null ) {
      return bean;
    }
    if ( name == null ) {
      return bean;
    }

    // compose sql

    String sqlSelectFrom = SpecialMessageQuery.sqlSelectFrom();
    String sqlWhere = "WHERE ( `type` = '" + StringEscapeUtils.escapeSql( type )
        + "' ) ";
    sqlWhere += "AND ( `name` = '" + StringEscapeUtils.escapeSql( name )
        + "' ) ";
    String sqlLimit = "LIMIT 1 ";
    String sqlOrder = "ORDER BY id ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    // execute sql

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return bean;
    }
    Iterator it = qr.iterator();
    if ( !it.hasNext() ) {
      return bean;
    }
    bean = SpecialMessageQuery.populateRecord( (QueryItem) it.next() );

    return bean;
  }

  public boolean updateContent( SpecialMessageBean bean ) {
    boolean result = false;

    // clean params

    int id = bean.getId();
    String content = bean.getContent();
    String description = bean.getDescription();

    content = ( content == null ) ? "" : content;
    description = ( description == null ) ? "" : description;

    // compose sql

    String sqlUpdate = "UPDATE special_message ";
    String sqlSet = "SET content = '" + StringEscapeUtils.escapeSql( content )
        + "' , description = '" + StringEscapeUtils.escapeSql( description )
        + "' , date_updated = NOW() ";
    String sqlWhere = "WHERE ( id = " + id + " ) ";
    String sql = sqlUpdate + sqlSet + sqlWhere;

    // execute sql

    DLog.debug( lctx , "Perform " + sql );
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

}
