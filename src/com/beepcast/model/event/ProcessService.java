package com.beepcast.model.event;

import org.apache.commons.lang.StringUtils;

import com.beepcast.util.StrTok;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ProcessService {

  static final DLogContext lctx = new SimpleContext( "ProcessService" );

  public ProcessService() {

  }

  public ProcessBean[] decode( String strProcessBeans ) {
    ProcessBean[] arr = null;
    if ( StringUtils.isBlank( strProcessBeans ) ) {
      return arr;
    }
    try {

      // find total number of process beans
      StrTok st1 = new StrTok( strProcessBeans );
      int tot = Integer.parseInt( st1.nextTok( ":" ).trim() );
      if ( tot < 1 ) {
        DLog.warning( lctx , "Found empty array of process beans" );
        return arr;
      }

      // prepare array of process beans
      arr = new ProcessBean[tot];

      // iterate all process beans inside
      String strProcessBean = null;
      for ( int idx = 0 ; idx < tot ; idx++ ) {

        // get and validate str process bean
        strProcessBean = st1.nextTok( "~" ).trim();
        if ( StringUtils.isBlank( strProcessBean ) ) {
          continue;
        }

        // parse and extract each of process bean

        StrTok st2 = new StrTok( strProcessBean , "^" );
        ProcessBean processBean = new ProcessBean();
        processBean.setStep( st2.nextTok().trim() );
        processBean.setType( st2.nextTok().trim() );
        processBean.setParamLabel( st2.nextTok().trim() );

        String strNames = st2.nextTok().trim();
        if ( !StringUtils.isBlank( strNames ) ) {
          String[] arrNames = strNames.split( "," );
          for ( int idxNames = 0 ; idxNames < arrNames.length ; idxNames++ ) {
            arrNames[idxNames] = arrNames[idxNames].trim();
          }
          processBean.setNames( arrNames );
        }

        processBean.setResponse( st2.nextTok().trim() );
        processBean.setNextStep( st2.nextTok().trim() );
        processBean.setRfa( st2.nextTok().trim() );

        // store into array of process beans
        arr[idx] = processBean;
      }

    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to decode str process beans , " + e );
    }
    return arr;
  }

  public String encode( ProcessBean[] arrProcessBeans ) {
    String str = null;
    if ( arrProcessBeans == null ) {
      return str;
    }
    int idx , len = arrProcessBeans.length;
    StringBuffer strBuffer = new StringBuffer();
    strBuffer.append( len );
    strBuffer.append( ":" );
    for ( idx = 0 ; idx < len ; idx++ ) {
      strBuffer.append( arrProcessBeans[idx].getStep().trim() );
      strBuffer.append( "^" );
      strBuffer.append( arrProcessBeans[idx].getType().trim() );
      strBuffer.append( "^" );
      strBuffer.append( arrProcessBeans[idx].getParamLabel().trim() );
      strBuffer.append( "^" );
      strBuffer
          .append( StringUtils.join( arrProcessBeans[idx].getNames() , "," ) );
      strBuffer.append( "^" );
      strBuffer.append( arrProcessBeans[idx].getResponse().trim() );
      strBuffer.append( "^" );
      strBuffer.append( arrProcessBeans[idx].getNextStep().trim() );
      strBuffer.append( "^" );
      strBuffer.append( arrProcessBeans[idx].getRfa().trim() );
      strBuffer.append( "~" );
    }
    str = strBuffer.toString();
    if ( idx > 0 ) {
      // remove last trail character
      str = str.substring( 0 , str.length() - 1 );
    }
    return str;
  }
}
