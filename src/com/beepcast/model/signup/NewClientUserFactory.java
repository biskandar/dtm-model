package com.beepcast.model.signup;

import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class NewClientUserFactory {

  static final DLogContext lctx = new SimpleContext( "NewClientUserFactory" );

  public static NewClientUserBean createNewClientUserBean(
      String userFirstName , String userLastName , String userPhone ,
      String userEmail , String companyName , int companySizeId ,
      String companyAddress , String companyCity , String companyState ,
      String companyPostcode , int companyCountryId , String companyWeb ) {

    NewClientUserBean bean = new NewClientUserBean();

    bean.setUserFirstName( userFirstName );
    bean.setUserLastName( userLastName );
    bean.setUserPhone( userPhone );
    bean.setUserEmail( userEmail );

    bean.setCompanyName( companyName );
    bean.setCompanySizeId( companySizeId );
    bean.setCompanyAddr( companyAddress );
    bean.setCompanyCity( companyCity );
    bean.setCompanyState( companyState );
    bean.setCompanyPostcode( companyPostcode );
    bean.setCompanyCountryId( companyCountryId );
    bean.setCompanyWww( companyWeb );

    bean.setSentEmail( false );
    bean.setActive( false );

    return bean;
  }

}
