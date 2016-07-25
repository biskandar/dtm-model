package com.beepcast.model.mobileUser;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class MobileUserFactory {

  static final DLogContext lctx = new SimpleContext( "MobileUserFactory" );

  public static MobileUserBean createMobileUserBean( int clientId ,
      String phoneNumber ) {
    MobileUserBean bean = null;

    if ( clientId < 1 ) {
      DLog.warning( lctx , "Failed to create mobile user bean "
          + ", found zero client id" );
      return bean;
    }

    if ( StringUtils.isBlank( phoneNumber ) ) {
      DLog.warning( lctx , "Failed to create mobile user bean "
          + ", found empty phone number" );
      return bean;
    }

    bean = new MobileUserBean();
    bean.setClientId( clientId );
    bean.setPhone( phoneNumber );
    bean.setPassword( generatePassword() );

    return bean;
  }

  private static String generatePassword() {
    return RandomStringUtils.randomNumeric( 6 );
  }

}
