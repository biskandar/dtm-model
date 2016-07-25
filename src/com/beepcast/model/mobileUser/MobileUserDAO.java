package com.beepcast.model.mobileUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.database.ConnectionWrapper;
import com.beepcast.database.DatabaseLibrary;
import com.beepcast.database.DatabaseLibrary.QueryItem;
import com.beepcast.database.DatabaseLibrary.QueryResult;
import com.beepcast.encrypt.EncryptApp;
import com.beepcast.model.util.DateTimeFormat;
import com.beepcast.oproperties.OnlinePropertiesApp;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class MobileUserDAO {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "MobileUserDAO" );

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private DatabaseLibrary dbLib;
  private OnlinePropertiesApp opropsApp;

  private final String keyPhoneNumber;
  private final String keyIc;
  private final String keyLastName;
  private final String keyName;
  private final String keyEmail;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public MobileUserDAO() {
    dbLib = DatabaseLibrary.getInstance();
    opropsApp = OnlinePropertiesApp.getInstance();

    EncryptApp encryptApp = EncryptApp.getInstance();
    keyPhoneNumber = encryptApp.getKeyValue( EncryptApp.KEYNAME_PHONENUMBER );
    keyIc = encryptApp.getKeyValue( EncryptApp.KEYNAME_USERID );
    keyLastName = encryptApp.getKeyValue( EncryptApp.KEYNAME_USERNAME );
    keyName = encryptApp.getKeyValue( EncryptApp.KEYNAME_USERNAME );
    keyEmail = encryptApp.getKeyValue( EncryptApp.KEYNAME_EMAIL );

  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insert( MobileUserBean bean ) {
    boolean result = false;

    String strBirthDate = DateTimeFormat.convertToString( bean.getBirthDate() );
    String sqlBirthDate = ( strBirthDate == null ) ? "NULL" : "'"
        + StringEscapeUtils.escapeSql( strBirthDate ) + "'";

    String sqlInsert = "INSERT INTO mobile_user (";
    sqlInsert += "client_id,client_db_key_1,client_db_key_2,";
    sqlInsert += "marital_status,monthly_income,industry,occupation,";
    sqlInsert += "education,mobile_model,mobile_brand,mobile_operator,";
    sqlInsert += "mobile_ccnc,num_children,country,dwelling,";
    sqlInsert += "office_zip,office_street,office_unit,office_blk,";
    sqlInsert += "home_zip,home_street,home_unit,home_blk,";
    sqlInsert += "nationality,ic,encrypt_ic,company_name,last_code,";
    sqlInsert += "last_name,encrypt_last_name,client_beep_id,password,";
    sqlInsert += "phone,encrypt_phone,name,encrypt_name,email,encrypt_email,";
    sqlInsert += "personal_beep_id,birth_date,gender,salutation,";
    sqlInsert += "date_inserted,date_updated) ";

    String sqlValues = "VALUES (";
    sqlValues += bean.getClientId() + ",'"
        + StringEscapeUtils.escapeSql( bean.getClientDBKey1() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getClientDBKey2() ) + "',";
    sqlValues += "'" + StringEscapeUtils.escapeSql( bean.getMaritalStatus() )
        + "','" + StringEscapeUtils.escapeSql( bean.getMonthlyIncome() )
        + "','" + StringEscapeUtils.escapeSql( bean.getIndustry() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getOccupation() ) + "',";
    sqlValues += "'" + StringEscapeUtils.escapeSql( bean.getEducation() )
        + "','" + StringEscapeUtils.escapeSql( bean.getMobileModel() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getMobileBrand() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getMobileOperator() ) + "',";
    sqlValues += "'" + StringEscapeUtils.escapeSql( bean.getMobileCcnc() )
        + "'," + bean.getNumChildren() + ",'"
        + StringEscapeUtils.escapeSql( bean.getCountry() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getDwelling() ) + "',";
    sqlValues += "'" + StringEscapeUtils.escapeSql( bean.getOfficeZip() )
        + "','" + StringEscapeUtils.escapeSql( bean.getOfficeStreet() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getOfficeUnit() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getOfficeBlk() ) + "',";
    sqlValues += "'" + StringEscapeUtils.escapeSql( bean.getHomeZip() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getHomeStreet() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getHomeUnit() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getHomeBlk() ) + "',";
    sqlValues += "'" + StringEscapeUtils.escapeSql( bean.getNationality() )
        + "',''," + sqlEncryptIc( bean.getIc() ) + ",'"
        + StringEscapeUtils.escapeSql( bean.getCompanyName() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getLastCode() ) + "',";
    sqlValues += "''," + sqlEncryptLastName( bean.getLastName() ) + ",'"
        + StringEscapeUtils.escapeSql( bean.getClientBeepID() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getPassword() ) + "',";
    sqlValues += "''," + sqlEncryptPhoneNumber( bean.getPhone() ) + ",'',"
        + sqlEncryptName( bean.getName() ) + ",'',"
        + sqlEncryptEmail( bean.getEmail() ) + ",";
    sqlValues += "'" + StringEscapeUtils.escapeSql( bean.getPersonalBeepID() )
        + "'," + sqlBirthDate + ",'"
        + StringEscapeUtils.escapeSql( bean.getGender() ) + "','"
        + StringEscapeUtils.escapeSql( bean.getSalutation() ) + "',";
    sqlValues += "'" + DateTimeFormat.convertToString( bean.getDateInserted() )
        + "','" + DateTimeFormat.convertToString( bean.getDateUpdated() )
        + "') ";

    String sql = sqlInsert + sqlValues;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlInsert" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }

    return result;
  }

  public MobileUserBean select( int clientId , String phoneNumber ) {
    MobileUserBean bean = null;

    String sqlSelectFrom = sqlSelectFrom();
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    sqlWhere += "AND ( encrypt_phone = " + sqlEncryptPhoneNumber( phoneNumber )
        + " ) ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelectFrom + sqlWhere + sqlLimit;

    bean = populateBean( sql );
    return bean;
  }

  public MobileUserBean selectByEmail( int clientId , String emailAddress ) {
    MobileUserBean bean = null;

    String sqlSelectFrom = sqlSelectFrom();
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    sqlWhere += "AND ( encrypt_email = " + sqlEncryptEmail( emailAddress )
        + " ) ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelectFrom + sqlWhere + sqlLimit;

    bean = populateBean( sql );
    return bean;
  }

  public int selectMobileUserId( int clientId , String phoneNumber ) {
    int mobileUserId = 0;

    // compose sql

    String sqlSelect = "SELECT id ";
    String sqlFrom = "FROM mobile_user ";
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    sqlWhere += "AND ( encrypt_phone = " + sqlEncryptPhoneNumber( phoneNumber )
        + " ) ";
    String sqlLimit = "LIMIT 1 ";
    String sql = sqlSelect + sqlFrom + sqlWhere + sqlLimit;

    // execute sql

    QueryResult qr = dbLib.simpleQuery( "profiledb" , sql );
    if ( ( qr == null ) || ( qr.size() < 1 ) ) {
      return mobileUserId;
    }

    // populate record

    Iterator it = qr.iterator();
    if ( !it.hasNext() ) {
      return mobileUserId;
    }
    QueryItem qi = (QueryItem) it.next();
    if ( qi == null ) {
      return mobileUserId;
    }
    try {
      mobileUserId = Integer.parseInt( qi.getFirstValue() );
    } catch ( NumberFormatException e ) {
    }

    return mobileUserId;
  }

  public Vector selectCriteria( int clientId , String criteria , int limit ) {
    Vector vector = null;

    String sqlSelectFrom = sqlSelectFrom();
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    if ( !StringUtils.isBlank( criteria ) ) {
      sqlWhere += "AND " + criteria + " ";
    }
    String sqlOrder = "ORDER BY id ASC ";
    String sqlLimit = "LIMIT " + limit + " ";
    String sql = sqlSelectFrom + sqlWhere + sqlOrder + sqlLimit;

    vector = populateBeans( sql );
    return vector;
  }

  public boolean update( MobileUserBean bean ) {
    boolean result = false;

    String strBirthDate = DateTimeFormat.convertToString( bean.getBirthDate() );
    String sqlBirthDate = ( strBirthDate == null ) ? "NULL" : "'"
        + StringEscapeUtils.escapeSql( strBirthDate ) + "'";

    String sqlUpdate = "UPDATE mobile_user ";
    String sqlWhere = "WHERE ( id = " + bean.getId() + " ) ";
    String sqlSet = "SET date_updated = NOW() ";
    sqlSet += ", client_id = " + bean.getClientId() + " ";
    sqlSet += ", client_db_key_1 = '"
        + StringEscapeUtils.escapeSql( bean.getClientDBKey1() ) + "' ";
    sqlSet += ", client_db_key_2 = '"
        + StringEscapeUtils.escapeSql( bean.getClientDBKey2() ) + "' ";
    sqlSet += ", marital_status = '"
        + StringEscapeUtils.escapeSql( bean.getMaritalStatus() ) + "' ";
    sqlSet += ", monthly_income = '"
        + StringEscapeUtils.escapeSql( bean.getMonthlyIncome() ) + "' ";
    sqlSet += ", industry = '"
        + StringEscapeUtils.escapeSql( bean.getIndustry() ) + "' ";
    sqlSet += ", occupation = '"
        + StringEscapeUtils.escapeSql( bean.getOccupation() ) + "' ";
    sqlSet += ", education = '"
        + StringEscapeUtils.escapeSql( bean.getEducation() ) + "' ";
    sqlSet += ", mobile_model = '"
        + StringEscapeUtils.escapeSql( bean.getMobileModel() ) + "' ";
    sqlSet += ", mobile_brand = '"
        + StringEscapeUtils.escapeSql( bean.getMobileBrand() ) + "' ";
    sqlSet += ", mobile_operator = '"
        + StringEscapeUtils.escapeSql( bean.getMobileOperator() ) + "' ";
    sqlSet += ", mobile_ccnc = '"
        + StringEscapeUtils.escapeSql( bean.getMobileCcnc() ) + "' ";
    sqlSet += ", num_children = " + bean.getNumChildren() + " ";
    sqlSet += ", country = '" + StringEscapeUtils.escapeSql( bean.getCountry() )
        + "' ";
    sqlSet += ", dwelling = '"
        + StringEscapeUtils.escapeSql( bean.getDwelling() ) + "' ";
    sqlSet += ", office_zip = '"
        + StringEscapeUtils.escapeSql( bean.getOfficeZip() ) + "' ";
    sqlSet += ", office_street = '"
        + StringEscapeUtils.escapeSql( bean.getOfficeStreet() ) + "' ";
    sqlSet += ", office_unit = '"
        + StringEscapeUtils.escapeSql( bean.getOfficeUnit() ) + "' ";
    sqlSet += ", office_blk = '"
        + StringEscapeUtils.escapeSql( bean.getOfficeBlk() ) + "' ";
    sqlSet += ", home_zip = '"
        + StringEscapeUtils.escapeSql( bean.getHomeZip() ) + "' ";
    sqlSet += ", home_street = '"
        + StringEscapeUtils.escapeSql( bean.getHomeStreet() ) + "' ";
    sqlSet += ", home_unit = '"
        + StringEscapeUtils.escapeSql( bean.getHomeUnit() ) + "' ";
    sqlSet += ", home_blk = '"
        + StringEscapeUtils.escapeSql( bean.getHomeBlk() ) + "' ";
    sqlSet += ", nationality = '"
        + StringEscapeUtils.escapeSql( bean.getNationality() ) + "' ";
    sqlSet += ", ic = '' ";
    sqlSet += ", encrypt_ic = " + sqlEncryptIc( bean.getIc() ) + " ";
    sqlSet += ", company_name = '"
        + StringEscapeUtils.escapeSql( bean.getCompanyName() ) + "' ";
    sqlSet += ", last_code = '"
        + StringEscapeUtils.escapeSql( bean.getLastCode() ) + "' ";
    sqlSet += ", last_name = '' ";
    sqlSet += ", encrypt_last_name = "
        + sqlEncryptLastName( bean.getLastName() ) + " ";
    sqlSet += ", client_beep_id = '"
        + StringEscapeUtils.escapeSql( bean.getClientBeepID() ) + "' ";
    sqlSet += ", password = '"
        + StringEscapeUtils.escapeSql( bean.getPassword() ) + "' ";
    sqlSet += ", phone = '' ";
    sqlSet += ", encrypt_phone = " + sqlEncryptPhoneNumber( bean.getPhone() )
        + " ";
    sqlSet += ", name = '' ";
    sqlSet += ", encrypt_name = " + sqlEncryptName( bean.getName() ) + " ";
    sqlSet += ", email = '' ";
    sqlSet += ", encrypt_email = " + sqlEncryptEmail( bean.getEmail() ) + " ";
    sqlSet += ", personal_beep_id = '"
        + StringEscapeUtils.escapeSql( bean.getPersonalBeepID() ) + "' ";
    sqlSet += ", birth_date = " + sqlBirthDate + " ";
    sqlSet += ", gender = '" + StringEscapeUtils.escapeSql( bean.getGender() )
        + "' ";
    sqlSet += ", salutation = '"
        + StringEscapeUtils.escapeSql( bean.getSalutation() ) + "' ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }
    return result;
  }

  public boolean updateLastCode( MobileUserBean bean ) {
    boolean result = false;

    String sqlUpdate = "UPDATE mobile_user ";
    String sqlSet = "SET date_updated = NOW() ";
    sqlSet += ", last_code = '"
        + StringEscapeUtils.escapeSql( bean.getLastCode() ) + "' ";
    String sqlWhere = "WHERE ( id = " + bean.getId() + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }
    return result;
  }

  public boolean updateMobileCcnc( MobileUserBean bean ) {
    boolean result = false;

    String sqlUpdate = "UPDATE mobile_user ";
    String sqlSet = "SET date_updated = NOW() ";
    sqlSet += ", mobile_ccnc = '"
        + StringEscapeUtils.escapeSql( bean.getMobileCcnc() ) + "' ";
    String sqlWhere = "WHERE ( id = " + bean.getId() + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }
    return result;
  }

  public boolean updateMobileCcnc( int clientId , String phoneNumber ,
      String mobileCcnc ) {
    boolean result = false;

    String sqlUpdate = "UPDATE mobile_user ";
    String sqlSet = "SET date_updated = NOW() ";
    sqlSet += ", mobile_ccnc = '" + StringEscapeUtils.escapeSql( mobileCcnc )
        + "' ";
    String sqlWhere = "WHERE ( client_id = " + clientId + " ) ";
    sqlWhere += "AND ( encrypt_phone = " + sqlEncryptPhoneNumber( phoneNumber )
        + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }
    return result;
  }

  public boolean updateEmail( MobileUserBean bean ) {
    boolean result = false;

    String sqlUpdate = "UPDATE mobile_user ";
    String sqlSet = "SET date_updated = NOW() ";
    sqlSet += ", email = '' ";
    sqlSet += ", encrypt_email = " + sqlEncryptEmail( bean.getEmail() ) + " ";
    String sqlWhere = "WHERE ( id = " + bean.getId() + " ) ";

    String sql = sqlUpdate + sqlSet + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlUpdate" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }
    return result;
  }

  public boolean delete( int mobileUserId ) {
    boolean result = false;

    String sqlDelete = "DELETE FROM mobile_user ";
    String sqlWhere = "WHERE ( id = " + mobileUserId + " ) ";
    String sql = sqlDelete + sqlWhere;

    if ( opropsApp.getBoolean( "Model.DebugAllSqlDelete" , false ) ) {
      DLog.debug( lctx , "Perform " + sql );
    }
    Integer irslt = dbLib.executeQuery( "profiledb" , sql );
    if ( ( irslt != null ) && ( irslt.intValue() > 0 ) ) {
      result = true;
    }
    return result;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  private Vector populateBeans( String sql ) {
    Vector vector = new Vector( 1000 , 1000 );
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      Statement stmt = conn.createStatement();
      // DLog.debug( lctx , "Perform " + sql );
      ResultSet rs = stmt.executeQuery( sql );
      while ( rs.next() ) {
        MobileUserBean bean = populateBean( rs );
        if ( bean == null ) {
          continue;
        }
        vector.add( bean );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException e ) {
      DLog.warning( lctx , "Failed to query mobile user bean , " + e );
    } finally {
      conn.disconnect( true );
    }
    return vector;
  }

  private MobileUserBean populateBean( String sql ) {
    MobileUserBean bean = null;
    ConnectionWrapper conn = dbLib.getReaderConnection( "profiledb" );
    try {
      // DLog.debug( lctx , "Performed " + sql );
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery( sql );
      if ( rs.next() ) {
        bean = populateBean( rs );
      }
      rs.close();
      stmt.close();
    } catch ( SQLException e ) {
      DLog.warning( lctx , "Failed to query mobile user bean , " + e );
    } finally {
      conn.disconnect( true );
    }
    return bean;
  }

  private String sqlSelectFrom() {

    String sqlSelect = "SELECT `id`,`client_id`,`client_db_key_1`,`client_db_key_2`";
    sqlSelect += ",`marital_status`,`monthly_income`,`industry`,`occupation`,`education`";
    sqlSelect += ",`mobile_model`,`mobile_brand`,`mobile_operator`,`mobile_ccnc`,`num_children`";
    sqlSelect += ",`country`,`dwelling`,`office_zip`,`office_street`,`office_unit`";
    sqlSelect += ",`office_blk`,`home_zip`,`home_street`,`home_unit`,`home_blk`,`nationality`";
    sqlSelect += "," + sqlDecryptIc() + ",`company_name`,`last_code`";
    sqlSelect += "," + sqlDecryptLastName() + ",`client_beep_id`,`password`";
    sqlSelect += "," + sqlDecryptPhoneNumber() + "," + sqlDecryptName();
    sqlSelect += "," + sqlDecryptEmail() + ",`personal_beep_id`,`birth_date`";
    sqlSelect += ",`gender`,`salutation`,`date_inserted`,`date_updated` ";

    String sqlFrom = "FROM mobile_user ";

    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  private MobileUserBean populateBean( ResultSet rs ) throws SQLException {
    MobileUserBean mobileUser = new MobileUserBean();

    mobileUser.setId( rs.getInt( "id" ) );
    mobileUser.setClientId( rs.getInt( "client_id" ) );
    mobileUser.setClientDBKey1( rs.getString( "client_db_key_1" ) );
    mobileUser.setClientDBKey2( rs.getString( "client_db_key_2" ) );
    mobileUser.setMaritalStatus( rs.getString( "marital_status" ) );
    mobileUser.setMonthlyIncome( rs.getString( "monthly_income" ) );
    mobileUser.setIndustry( rs.getString( "industry" ) );
    mobileUser.setOccupation( rs.getString( "occupation" ) );
    mobileUser.setEducation( rs.getString( "education" ) );
    mobileUser.setMobileModel( rs.getString( "mobile_model" ) );
    mobileUser.setMobileBrand( rs.getString( "mobile_brand" ) );
    mobileUser.setMobileOperator( rs.getString( "mobile_operator" ) );
    mobileUser.setMobileCcnc( rs.getString( "mobile_ccnc" ) );
    mobileUser.setNumChildren( rs.getInt( "num_children" ) );
    mobileUser.setCountry( rs.getString( "country" ) );
    mobileUser.setDwelling( rs.getString( "dwelling" ) );
    mobileUser.setOfficeZip( rs.getString( "office_zip" ) );
    mobileUser.setOfficeStreet( rs.getString( "office_street" ) );
    mobileUser.setOfficeUnit( rs.getString( "office_unit" ) );
    mobileUser.setOfficeBlk( rs.getString( "office_blk" ) );
    mobileUser.setHomeZip( rs.getString( "home_zip" ) );
    mobileUser.setHomeStreet( rs.getString( "home_street" ) );
    mobileUser.setHomeUnit( rs.getString( "home_unit" ) );
    mobileUser.setHomeBlk( rs.getString( "home_blk" ) );
    mobileUser.setNationality( rs.getString( "nationality" ) );
    mobileUser.setIc( rs.getString( "ic" ) );
    mobileUser.setCompanyName( rs.getString( "company_name" ) );
    mobileUser.setLastCode( rs.getString( "last_code" ) );
    mobileUser.setLastName( rs.getString( "last_name" ) );
    mobileUser.setClientBeepID( rs.getString( "client_beep_id" ) );
    mobileUser.setPassword( rs.getString( "password" ) );
    mobileUser.setPhone( rs.getString( "phone" ) );
    mobileUser.setName( rs.getString( "name" ) );
    mobileUser.setEmail( rs.getString( "email" ) );
    mobileUser.setPersonalBeepID( rs.getString( "personal_beep_id" ) );
    mobileUser.setBirthDate( DateTimeFormat.convertToDate( rs
        .getString( "birth_date" ) ) );
    mobileUser.setGender( rs.getString( "gender" ) );
    mobileUser.setSalutation( rs.getString( "salutation" ) );
    mobileUser.setDateInserted( DateTimeFormat.convertToDate( rs
        .getString( "date_inserted" ) ) );
    mobileUser.setDateUpdated( DateTimeFormat.convertToDate( rs
        .getString( "date_updated" ) ) );

    return mobileUser;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Util Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  private String sqlEncryptPhoneNumber( String phoneNumber ) {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_ENCRYPT('" );
    sb.append( phoneNumber );
    sb.append( "','" );
    sb.append( keyPhoneNumber );
    sb.append( "')" );
    return sb.toString();
  }

  private String sqlDecryptPhoneNumber() {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_DECRYPT(encrypt_phone,'" );
    sb.append( keyPhoneNumber );
    sb.append( "') AS phone" );
    return sb.toString();
  }

  private String sqlEncryptIc( String ic ) {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_ENCRYPT('" );
    sb.append( ic );
    sb.append( "','" );
    sb.append( keyIc );
    sb.append( "')" );
    return sb.toString();
  }

  private String sqlDecryptIc() {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_DECRYPT(encrypt_ic,'" );
    sb.append( keyIc );
    sb.append( "') AS ic" );
    return sb.toString();
  }

  private String sqlEncryptLastName( String lastName ) {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_ENCRYPT('" );
    sb.append( lastName );
    sb.append( "','" );
    sb.append( keyLastName );
    sb.append( "')" );
    return sb.toString();
  }

  private String sqlDecryptLastName() {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_DECRYPT(encrypt_last_name,'" );
    sb.append( keyLastName );
    sb.append( "') AS last_name" );
    return sb.toString();
  }

  private String sqlEncryptName( String name ) {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_ENCRYPT('" );
    sb.append( name );
    sb.append( "','" );
    sb.append( keyName );
    sb.append( "')" );
    return sb.toString();
  }

  private String sqlDecryptName() {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_DECRYPT(encrypt_name,'" );
    sb.append( keyName );
    sb.append( "') AS name" );
    return sb.toString();
  }

  private String sqlEncryptEmail( String email ) {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_ENCRYPT('" );
    sb.append( email );
    sb.append( "','" );
    sb.append( keyEmail );
    sb.append( "')" );
    return sb.toString();
  }

  private String sqlDecryptEmail() {
    StringBuffer sb = new StringBuffer();
    sb.append( "AES_DECRYPT(encrypt_email,'" );
    sb.append( keyEmail );
    sb.append( "') AS email" );
    return sb.toString();
  }

}
