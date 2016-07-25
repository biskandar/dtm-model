package com.beepcast.model.signup;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class NewClientUserService {

  static final DLogContext lctx = new SimpleContext( "NewClientUserService" );

  private NewClientUserDAO dao;

  public NewClientUserService() {
    dao = new NewClientUserDAO();
  }

  public boolean generateLoginActivationParams( NewClientUserBean bean ) {
    boolean result = false;

    result = NewClientUserUtils.generateRandomLoginUsername( bean );
    if ( !result ) {
      DLog.warning( lctx , "Failed to generate login activation params "
          + ", found failed to generate login username" );
      return result;
    }

    result = NewClientUserUtils.generateRandomLoginPassword( bean );
    if ( !result ) {
      DLog.warning( lctx , "Failed to generate login activation params "
          + ", found failed to generate login password" );
      return result;
    }

    result = NewClientUserUtils.generateActivationKey( bean );
    if ( !result ) {
      DLog.warning( lctx , "Failed to generate login activation params "
          + ", found failed to generate activation key" );
      return result;
    }

    return result;
  }

  public boolean insertNewClientUser( NewClientUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to insert NewClientUser "
          + ", found null NewClientUser" );
      return result;
    }

    // validate

    String userFirstName = bean.getUserFirstName();
    if ( ( userFirstName == null ) || ( userFirstName.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to insert NewClientUser "
          + ", found null userFirstName" );
      return result;
    }
    String userLastName = bean.getUserLastName();
    if ( ( userLastName == null ) || ( userLastName.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to insert NewClientUser "
          + ", found null userLastName" );
      return result;
    }
    String userPhone = bean.getUserPhone();
    if ( ( userPhone == null ) || ( userPhone.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to insert NewClientUser "
          + ", found null userPhone" );
      return result;
    }
    String userEmail = bean.getUserEmail();
    if ( ( userEmail == null ) || ( userEmail.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to insert NewClientUser "
          + ", found null userEmail" );
      return result;
    }
    String companyName = bean.getCompanyName();
    if ( ( companyName == null ) || ( companyName.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to insert NewClientUser "
          + ", found null companyName" );
      return result;
    }
    int companyCountryId = bean.getCompanyCountryId();
    if ( companyCountryId < 1 ) {
      DLog.warning( lctx , "Failed to insert NewClientUser "
          + ", found null companyCountry" );
      return result;
    }

    result = dao.insertNewClientUser( bean );
    return result;
  }

  public NewClientUserBean findNewClientUserBasedOnKey( String key ) {
    NewClientUserBean bean = null;
    if ( ( key == null ) || ( key.equals( "" ) ) ) {
      DLog.warning( lctx , "Failed to find new client user "
          + ", found null key" );
      return bean;
    }
    bean = dao.findNewClientUserBasedOnKey( key );
    return bean;
  }

  public NewClientUserBean findNewClientUserBasedOnId( int id ) {
    NewClientUserBean bean = null;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to find new client user "
          + ", found zero id" );
      return bean;
    }
    bean = dao.findNewClientUserBasedOnId( id );
    return bean;
  }

  public boolean deleteNewClientUserBasedOnId( int id ) {
    boolean result = false;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to delete new client user "
          + ", found zero id" );
      return result;
    }
    result = dao.deleteNewClientUserBasedOnId( id );
    return result;
  }

  public boolean activeNewClientUserBasedOnId( int id ) {
    boolean result = false;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to active new client user "
          + ", found zero id" );
      return result;
    }
    result = dao.setActiveNewClientUserBasedOnId( id , true );
    return result;
  }

  public boolean inactiveNewClientUserBasedOnId( int id ) {
    boolean result = false;
    if ( id < 1 ) {
      DLog.warning( lctx , "Failed to inactive new client user "
          + ", found zero id" );
      return result;
    }
    result = dao.setActiveNewClientUserBasedOnId( id , false );
    return result;
  }

}
