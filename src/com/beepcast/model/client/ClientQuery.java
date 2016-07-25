package com.beepcast.model.client;

import java.sql.ResultSet;

import com.beepcast.model.util.DateTimeFormat;
import com.firsthop.common.log.DLog;
import com.firsthop.common.log.DLogContext;
import com.firsthop.common.log.SimpleContext;

public class ClientQuery {

  static final DLogContext lctx = new SimpleContext( "ClientQuery" );

  public static String sqlSelectFrom() {
    String sqlSelect = "SELECT * ";
    String sqlFrom = "FROM client ";
    String sqlSelectFrom = sqlSelect + sqlFrom;
    return sqlSelectFrom;
  }

  public static ClientBean populateBean( ResultSet rs ) {
    ClientBean clientBean = new ClientBean();
    try {

      // primary and parent key
      clientBean.setClientID( (long) rs.getLong( "client_id" ) );
      clientBean.setMasterClientID( (long) rs.getDouble( "master_client_id" ) );

      // profile
      clientBean.setCompanyName( rs.getString( "company_name" ) );
      clientBean.setCompanySizeId( rs.getInt( "company_size_id" ) );
      clientBean.setAddress( rs.getString( "address" ) );
      clientBean.setCompanyCity( rs.getString( "company_city" ) );
      clientBean.setCompanyState( rs.getString( "company_state" ) );
      clientBean.setCompanyPostcode( rs.getString( "company_postcode" ) );
      clientBean.setCompanyCountryId( rs.getInt( "company_country_id" ) );
      clientBean.setCompanyCountryName( "" );
      clientBean.setOfficePhone( rs.getString( "office_phone" ) );
      clientBean.setWebAddress( rs.getString( "web_address" ) );
      clientBean.setManager( rs.getString( "manager" ) );
      clientBean.setPhone( rs.getString( "phone" ) );
      clientBean.setEmail( rs.getString( "email" ) );
      clientBean.setCountry( rs.getString( "country" ) );
      clientBean.setUrlLink( "" );

      // feature
      clientBean.setBitFlags( (long) rs.getDouble( "bit_flags" ) );
      clientBean.setBeepme( rs.getDouble( "beepme" ) == 1 );
      clientBean.setBudget( new Double( rs.getString( "budget" ) )
          .doubleValue() );
      clientBean.setPaymentType( rs.getInt( "payment_type" ) );
      clientBean.setBalanceThreshold( rs.getInt( "balance_threshold" ) );
      clientBean.setNotificationEmailAddresses( rs
          .getString( "notification_email_addresses" ) );
      clientBean.setNotificationMobileNumbers( rs
          .getString( "notification_mobile_numbers" ) );
      clientBean.setFromEmailAddresses( rs.getString( "from_email_addresses" ) );
      clientBean.setChargeLocked( rs.getDouble( "charge_locked" ) == 1 );
      clientBean.setDiscount( rs.getString( "discount" ) );
      clientBean.setSmsCharge( new Double( rs.getString( "sms_charge" ) )
          .doubleValue() );
      clientBean.setState( rs.getString( "state" ) );
      clientBean
          .setGroupConnectionId( (int) rs.getInt( "group_connection_id" ) );
      clientBean.setGroupConnectionName( "" );
      clientBean.setBandId( (int) rs.getInt( "band_id" ) );
      clientBean.setBandName( "" );
      clientBean.setClientLevelId( (int) rs.getInt( "client_level_id" ) );
      clientBean.setClientLevelName( "" );
      clientBean.setEnableClientApi( (boolean) rs
          .getBoolean( "enable_client_api" ) );
      clientBean.setPasswordRecycleInterval( (int) rs
          .getInt( "password_recycle_interval" ) );
      clientBean.setAllowIpAddresses( rs.getString( "allow_ip_addresses" ) );

      // flag profile
      clientBean.setDisplay( (boolean) rs.getBoolean( "display" ) );
      clientBean.setExpired( (boolean) rs.getBoolean( "expired" ) );
      clientBean.setActive( (boolean) rs.getBoolean( "active" ) );

      // date profile
      clientBean.setDateExpired( DateTimeFormat.convertToDate( rs
          .getString( "date_expired" ) ) );
      clientBean.setDateInserted( DateTimeFormat.convertToDate( rs
          .getString( "date_inserted" ) ) );
      clientBean.setDateUpdated( DateTimeFormat.convertToDate( rs
          .getString( "date_updated" ) ) );

    } catch ( Exception e ) {
      DLog.warning( lctx , "Failed to populate client record , " + e );
    }
    return clientBean;
  }

}
