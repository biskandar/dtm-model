package com.beepcast.model.signup;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.beepcast.util.SimpleMD5;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class NewClientUserUtils {

  static final DLogContext lctx = new SimpleContext( "NewClientUserUtils" );

  public static final int USERNAME_MAXLENGTH = 8;
  public static final int PASSWORD_MAXLENGTH = 6;
  public static final String ACTKEY_DELIMITER = ";";

  public static boolean generateRandomLoginUsername( NewClientUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to generate random login user "
          + ", found null bean" );
      return result;
    }

    // generate user name from company name word

    String loginUsername = StringUtils.trimToEmpty( bean.getCompanyName() );
    if ( StringUtils.isBlank( loginUsername ) ) {
      loginUsername = RandomStringUtils.randomAlphabetic( USERNAME_MAXLENGTH );
    } else {
      loginUsername = StringUtils.deleteWhitespace( loginUsername );
      loginUsername = StringUtils.left( loginUsername , USERNAME_MAXLENGTH );
      int gapLength = USERNAME_MAXLENGTH - StringUtils.length( loginUsername );
      if ( gapLength > 0 ) {
        loginUsername += RandomStringUtils.randomAlphabetic( gapLength );
      }
    }

    // verify the validity of login user name

    if ( StringUtils.isBlank( loginUsername ) ) {
      DLog.warning( lctx , "Failed to generate random login user" );
      return result;
    }

    // update login user name into the bean

    bean.setLoginUsr( loginUsername );
    result = true;
    return result;
  }

  public static boolean generateRandomLoginPassword( NewClientUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to generate random login password "
          + ", found null bean" );
      return result;
    }

    // generate random password
    String loginPassword = RandomStringUtils
        .randomAlphanumeric( PASSWORD_MAXLENGTH );

    // verify the validity of login password
    if ( StringUtils.isBlank( loginPassword ) ) {
      DLog.warning( lctx , "Failed to generate random login password" );
      return result;
    }

    // update login password into the bean
    bean.setLoginPwd( loginPassword );
    result = true;
    return result;
  }

  public static boolean generateActivationKey( NewClientUserBean bean ) {
    boolean result = false;
    if ( bean == null ) {
      DLog.warning( lctx , "Failed to generate activation key "
          + ", found null bean" );
      return result;
    }

    // load params from bean

    String userFirstName = bean.getUserFirstName();
    String userLastName = bean.getUserLastName();
    String loginUsr = bean.getLoginUsr();
    String loginPwd = bean.getLoginPwd();

    // add additional key ( 5 chars )

    String randomKey = RandomStringUtils.randomAlphanumeric( 5 );

    // generate activation key

    StringBuffer sbActKey = new StringBuffer();
    sbActKey.append( userFirstName );
    sbActKey.append( ACTKEY_DELIMITER );
    sbActKey.append( userLastName );
    sbActKey.append( ACTKEY_DELIMITER );
    sbActKey.append( loginUsr );
    sbActKey.append( ACTKEY_DELIMITER );
    sbActKey.append( loginPwd );
    sbActKey.append( ACTKEY_DELIMITER );
    sbActKey.append( randomKey );

    // encoded activation key with md5 method

    String actKey = SimpleMD5.convertMD5( sbActKey.toString() );

    // verify the validity of activation key
    if ( StringUtils.isBlank( actKey ) ) {
      DLog.warning( lctx , "Failed to generate random activation key" );
      return result;
    }

    // update activation key into the bean
    bean.setActivationKey( actKey );
    result = true;
    return result;
  }

}
