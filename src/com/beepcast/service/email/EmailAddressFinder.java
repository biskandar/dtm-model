package com.beepcast.service.email;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class EmailAddressFinder {

  static final DLogContext lctx = new SimpleContext( "EmailAddressFinder" );

  private static final String REGEX_FIND_EMAIL_ADDRESSES = "([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";

  public static List findEmailAddressesToList( String str ) {
    List listEmailAddresses = null;

    if ( str == null ) {
      DLog.warning( lctx , "Failed to find email addresses "
          + ", found null str" );
      return listEmailAddresses;
    }

    listEmailAddresses = new ArrayList();

    Pattern pattern = Pattern.compile( REGEX_FIND_EMAIL_ADDRESSES );
    Matcher matcher = pattern.matcher( str );
    while ( matcher.find() ) {
      listEmailAddresses.add( matcher.group() );
    }

    return listEmailAddresses;
  }

  public static String[] findEmailAddressesToArray( String str ) {
    String[] arrEmailAddresses = null;

    List listEmailAddresses = findEmailAddressesToList( str );
    if ( listEmailAddresses == null ) {
      return arrEmailAddresses;
    }

    int sizeEmailAddresses = listEmailAddresses.size();
    if ( sizeEmailAddresses < 1 ) {
      return arrEmailAddresses;
    }

    int idx = 0;
    arrEmailAddresses = new String[sizeEmailAddresses];
    Iterator iterEmailAddresses = listEmailAddresses.iterator();
    while ( iterEmailAddresses.hasNext() ) {
      arrEmailAddresses[idx] = (String) iterEmailAddresses.next();
      idx = idx + 1;
    }

    return arrEmailAddresses;
  }

}
