package com.beepcast.model.mobileUser;

import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class MobileUserService {

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constanta
  //
  // ////////////////////////////////////////////////////////////////////////////

  static final DLogContext lctx = new SimpleContext( "MobileUserService" );

  static final int MAX_LIMIT = 1000;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Data Member
  //
  // ////////////////////////////////////////////////////////////////////////////

  private MobileUserDAO dao;

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  // ////////////////////////////////////////////////////////////////////////////

  public MobileUserService() {
    dao = new MobileUserDAO();
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Support Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  public boolean insert( MobileUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert mobile user bean "
          + ", found null" );
      return result;
    }
    if ( select( bean.getClientId() , bean.getPhone() ) != null ) {
      DLog.warning( lctx , "Failed to insert mobile user bean "
          + ", found already exist" );
      return result;
    }
    clean( bean );
    result = dao.insert( bean );
    if ( !result ) {
      DLog.warning( lctx , "Failed to insert mobile user bean "
          + ", failed insert into table" );
      return result;
    }
    int mobileUserId = dao.selectMobileUserId( bean.getClientId() ,
        bean.getPhone() );
    if ( mobileUserId < 1 ) {
      DLog.warning( lctx , "Failed to insert mobile user bean "
          + ", found missing data" );
      return result;
    }
    bean.setId( mobileUserId );
    return result;
  }

  public MobileUserBean select( int clientId , String phoneNumber ) {
    MobileUserBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to select mobile user bean "
          + ", found zero client id" );
      return bean;
    }
    if ( StringUtils.isBlank( phoneNumber ) ) {
      DLog.warning( lctx , "Failed to select mobile user bean "
          + ", found empty phone number" );
      return bean;
    }
    bean = dao.select( clientId , phoneNumber );
    return bean;
  }

  public MobileUserBean selectByEmail( int clientId , String emailAddress ) {
    MobileUserBean bean = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to select mobile user bean "
          + ", found zero client id" );
      return bean;
    }
    if ( StringUtils.isBlank( emailAddress ) ) {
      DLog.warning( lctx , "Failed to select mobile user bean "
          + ", found empty email address" );
      return bean;
    }
    bean = dao.selectByEmail( clientId , emailAddress );
    return bean;
  }

  public Vector selectCriteria( int clientId , String criteria , int limit ) {
    Vector vector = null;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to select criteria mobile user bean "
          + ", found zero client id" );
      return vector;
    }
    if ( StringUtils.isBlank( criteria ) ) {
      DLog.warning( lctx , "Failed to select criteria mobile user bean "
          + ", found empty criteria" );
      return vector;
    }
    if ( limit < 1 ) {
      DLog.warning( lctx , "Failed to select criteria mobile user bean "
          + ", found zero limit" );
      return vector;
    }
    if ( limit > MAX_LIMIT ) {
      limit = MAX_LIMIT;
    }
    vector = dao.selectCriteria( clientId , criteria , limit );
    return vector;
  }

  public boolean update( MobileUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to updated mobile user bean "
          + ", found null bean" );
      return result;
    }
    if ( bean.getId() < 1 ) {
      DLog.warning( lctx , "Failed to updated mobile user bean "
          + ", found zero id" );
      return result;
    }
    clean( bean );
    result = dao.update( bean );
    return result;
  }

  public boolean updateLastCode( MobileUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to updated mobile user code "
          + ", found null bean" );
      return result;
    }
    if ( bean.getId() < 1 ) {
      DLog.warning( lctx , "Failed to updated mobile user code "
          + ", found zero id" );
      return result;
    }
    result = dao.updateLastCode( bean );
    return result;
  }

  public boolean updateMobileCcnc( MobileUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to update mobile ccnc , found null bean" );
      return result;
    }
    if ( bean.getId() < 1 ) {
      DLog.warning( lctx , "Failed to updated mobile ccnc , found zero id" );
      return result;
    }
    result = dao.updateMobileCcnc( bean );
    return result;
  }

  public boolean updateMobileCcnc( int clientId , String phoneNumber ,
      String mobileCcnc ) {
    boolean result = false;
    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to update mobile ccnc "
          + ", found zero client id" );
      return result;
    }
    if ( StringUtils.isBlank( phoneNumber ) ) {
      DLog.warning( lctx , "Failed to update mobile ccnc "
          + ", found blank phone number" );
      return result;
    }
    mobileCcnc = ( mobileCcnc == null ) ? "" : mobileCcnc.trim();
    result = dao.updateMobileCcnc( clientId , phoneNumber , mobileCcnc );
    return result;
  }

  public boolean updateEmail( MobileUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to updated mobile user code "
          + ", found null bean" );
      return result;
    }
    if ( bean.getId() < 1 ) {
      DLog.warning( lctx , "Failed to updated mobile user code "
          + ", found zero id" );
      return result;
    }
    result = dao.updateEmail( bean );
    return result;
  }

  public boolean delete( int mobileUserId ) {
    boolean result = false;
    if ( mobileUserId < 1 ) {
      return result;
    }
    result = dao.delete( mobileUserId );
    return result;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Core Function
  //
  // ////////////////////////////////////////////////////////////////////////////

  private boolean clean( MobileUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to clean mobile user bean , found null" );
      return result;
    }
    bean.setClientDBKey1( StringUtils.trimToEmpty( bean.getClientDBKey1() ) );
    bean.setClientDBKey2( StringUtils.trimToEmpty( bean.getClientDBKey2() ) );
    bean.setMaritalStatus( StringUtils.trimToEmpty( bean.getMaritalStatus() ) );
    bean.setMonthlyIncome( StringUtils.trimToEmpty( bean.getMonthlyIncome() ) );
    bean.setIndustry( StringUtils.trimToEmpty( bean.getIndustry() ) );
    bean.setOccupation( StringUtils.trimToEmpty( bean.getOccupation() ) );
    bean.setEducation( StringUtils.trimToEmpty( bean.getEducation() ) );
    bean.setMobileModel( StringUtils.trimToEmpty( bean.getMobileModel() ) );
    bean.setMobileBrand( StringUtils.trimToEmpty( bean.getMobileBrand() ) );
    bean.setMobileOperator( StringUtils.trimToEmpty( bean.getMobileOperator() ) );
    bean.setMobileCcnc( StringUtils.trimToEmpty( bean.getMobileCcnc() ) );
    bean.setCountry( StringUtils.trimToEmpty( bean.getCountry() ) );
    bean.setDwelling( StringUtils.trimToEmpty( bean.getDwelling() ) );
    bean.setOfficeZip( StringUtils.trimToEmpty( bean.getOfficeZip() ) );
    bean.setOfficeStreet( StringUtils.trimToEmpty( bean.getOfficeStreet() ) );
    bean.setOfficeUnit( StringUtils.trimToEmpty( bean.getOfficeUnit() ) );
    bean.setOfficeBlk( StringUtils.trimToEmpty( bean.getOfficeBlk() ) );
    bean.setHomeZip( StringUtils.trimToEmpty( bean.getHomeZip() ) );
    bean.setHomeStreet( StringUtils.trimToEmpty( bean.getHomeStreet() ) );
    bean.setHomeUnit( StringUtils.trimToEmpty( bean.getHomeUnit() ) );
    bean.setHomeBlk( StringUtils.trimToEmpty( bean.getHomeBlk() ) );
    bean.setNationality( StringUtils.trimToEmpty( bean.getNationality() ) );
    bean.setIc( StringUtils.trimToEmpty( bean.getIc() ) );
    bean.setCompanyName( StringUtils.trimToEmpty( bean.getCompanyName() ) );
    bean.setLastCode( StringUtils.trimToEmpty( bean.getLastCode() ) );
    bean.setLastName( StringUtils.trimToEmpty( bean.getLastName() ) );
    bean.setClientBeepID( StringUtils.trimToEmpty( bean.getClientBeepID() ) );
    bean.setPassword( StringUtils.trimToEmpty( bean.getPassword() ) );
    bean.setPhone( StringUtils.trimToEmpty( bean.getPhone() ) );
    bean.setName( StringUtils.trimToEmpty( bean.getName() ) );
    bean.setEmail( StringUtils.trimToEmpty( bean.getEmail() ) );
    bean.setPersonalBeepID( StringUtils.trimToEmpty( bean.getPersonalBeepID() ) );
    bean.setGender( StringUtils.trimToEmpty( bean.getGender() ) );
    bean.setSalutation( StringUtils.trimToEmpty( bean.getSalutation() ) );
    result = true;
    return result;
  }

}
