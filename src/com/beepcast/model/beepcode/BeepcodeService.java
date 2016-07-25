package com.beepcast.model.beepcode;

import java.util.List;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class BeepcodeService {

  static final DLogContext lctx = new SimpleContext( "BeepcodeService" );

  private BeepcodeDAO dao;

  public BeepcodeService() {
    dao = new BeepcodeDAO();
  }

  public boolean insert( BeepcodeBean beepcodeBean ) {
    return dao.insert( beepcodeBean );
  }

  public BeepcodeBean select( String code ) {
    return dao.select( code );
  }

  public boolean update( BeepcodeBean beepcodeBean ) {
    return dao.update( beepcodeBean );
  }

  public List listActiveCodesKeywordsFromEventId( int eventId ) {
    return dao.listActiveCodesKeywordsFromEventId( eventId );
  }

  public int totalActiveCodes( int clientId ) {
    return dao.totalActiveCodes( clientId );
  }

  public List listActiveCodes( int clientId ) {
    return dao.listActiveCodes( clientId );
  }

  public int totalActiveKeywords( int clientId ) {
    return dao.totalActiveKeywords( clientId );
  }

  public List listActiveKeywords( int clientId ) {
    return dao.listActiveKeywords( clientId );
  }

}
