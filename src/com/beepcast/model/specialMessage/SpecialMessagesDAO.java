package com.beepcast.model.specialMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class SpecialMessagesDAO {

  static final DLogContext lctx = new SimpleContext( "SpecialMessagesDAO" );

  private DatabaseLibrary dbLib;

  public SpecialMessagesDAO() {
    dbLib = DatabaseLibrary.getInstance();
  }

  public List select( String type ) {
    List list = new ArrayList();

    String sqlSelectFrom = SpecialMessageQuery.sqlSelectFrom();
    String sqlWhere = "";
    if ( !StringUtils.isBlank( type ) ) {
      sqlWhere += "WHERE ( `type` = '" + StringEscapeUtils.escapeSql( type )
          + "' ) ";
    }
    String sqlOrder = "ORDER BY `type` ASC , `name` ASC ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder;

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return list;
    }

    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi == null ) {
        continue;
      }
      SpecialMessageBean bean = SpecialMessageQuery.populateRecord( qi );
      if ( bean == null ) {
        continue;
      }
      list.add( bean );
    }

    return list;
  }

  public List selectActiveTypes() {
    String sqlSelect = "SELECT DISTINCT `type` ";
    String sqlFrom = "FROM special_message ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    String sqlGroup = "GROUP BY `type` ";
    String sqlOrder = "ORDER BY `type` ASC ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlGroup + sqlOrder;
    return selectListString( sql );
  }

  public List selectActiveNames() {
    String sqlSelect = "SELECT DISTINCT `name` ";
    String sqlFrom = "FROM special_message ";
    String sqlWhere = "WHERE ( active = 1 ) ";
    String sqlGroup = "GROUP BY `name` ";
    String sqlOrder = "ORDER BY `name` ASC ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlGroup + sqlOrder;
    return selectListString( sql );
  }

  private List selectListString( String sql ) {
    List list = new ArrayList();

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return list;
    }

    Iterator it = qr.iterator();
    while ( it.hasNext() ) {
      QueryItem qi = (QueryItem) it.next();
      if ( qi == null ) {
        continue;
      }
      String str = qi.getFirstValue();
      if ( str == null ) {
        continue;
      }
      list.add( str );
    }

    return list;
  }

}
