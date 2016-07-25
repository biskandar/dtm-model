package com.beepcast.model.gateway;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.beepcast.util.NamingService;
import com.beepcast.util.Util;

/*******************************************************************************
 * Provider Initializer.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class ProviderInitializer implements ServletContextListener {

  /*****************************************************************************
   * context initializer
   ****************************************************************************/
  public void contextInitialized( ServletContextEvent sce ) {

    ServletContext context = sce.getServletContext();
    String logStr = "";

    /*-----------------------
      get system config path        // FileSystemInitializer must run first
    -----------------------*/
    NamingService nameSvc = NamingService.getInstance();
    String systemConfigPath = (String) nameSvc
        .getAttribute( "systemConfigPath" );

    /*
     * ==================================================== commzgate
     * ====================================================
     */
    /*-----------------------
      commzgate enabled
    -----------------------*/
    String commzgateEnabled = Util.readINI( systemConfigPath + "provider.ini" ,
        "commzgate" , "commzgateEnabled" );
    context.setAttribute( "commzgateEnabled" , commzgateEnabled );
    logStr = "Commzgate Enabled = " + commzgateEnabled;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
      number of agents
    -----------------------*/
    String commzgateNumAgents = Util.readINI(
        systemConfigPath + "provider.ini" , "commzgate" , "commzgateNumAgents" );
    context.setAttribute( "commzgateNumAgents" , commzgateNumAgents );
    logStr = "Commzgate Number of Agents = " + commzgateNumAgents;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
      commzgate provider link
    -----------------------*/
    // TODO:use for mblox
    String commzgateProviderLink = Util.readINI( systemConfigPath
        + "provider.ini" , "commzgate" , "commzgateProviderLink" );
    context.setAttribute( "commzgateProviderLink" , commzgateProviderLink );
    logStr = "Commzgate Provider Link = " + commzgateProviderLink;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
      commzgate account url
    -----------------------*/
    String commzgateAccountUrl = Util.readINI( systemConfigPath
        + "provider.ini" , "commzgate" , "commzgateAccountUrl" );
    context.setAttribute( "commzgateAccountUrl" , commzgateAccountUrl );
    logStr = "Commzgate Account URL = " + commzgateAccountUrl;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
    commzgate CheckBalance link
    ----------------------*/
    String commzgateCheckBalanceLink = Util.readINI( systemConfigPath
        + "provider.ini" , "commzgate" , "commzgateCheckBalanceLink" );
    context.setAttribute( "commzgateCheckBalanceLink" ,
        commzgateCheckBalanceLink );
    logStr = "Commzgate CheckBalance Link = " + commzgateCheckBalanceLink;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
      commzgate CheckBalance URL
    -----------------------*/
    String commzgateCheckBalanceUrl = Util.readINI( systemConfigPath
        + "provider.ini" , "commzgate" , "commzgateCheckBalanceUrl" );
    context
        .setAttribute( "commzgateCheckBalanceUrl" , commzgateCheckBalanceUrl );
    logStr = "Commzgate CheckBalance URL = " + commzgateCheckBalanceUrl;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
      commzgate login ID
    -----------------------*/
    String commzgateLoginID = Util.readINI( systemConfigPath + "provider.ini" ,
        "commzgate" , "commzgateLoginID" );
    context.setAttribute( "commzgateLoginID" , commzgateLoginID );
    // logStr = "Commzgate Login ID = "+commzgateLoginID;
    // context.log(logStr);
    System.out.println( logStr );

    /*-----------------------
      commzgate password
    -----------------------*/
    String commzgatePassword = Util.readINI( systemConfigPath + "provider.ini" ,
        "commzgate" , "commzgatePassword" );
    context.setAttribute( "commzgatePassword" , commzgatePassword );
    // logStr = "Commzgate Password = "+commzgatePassword;
    // context.log(logStr);
    System.out.println( logStr );

    /*
     * ==================================================== mBlox
     * ====================================================
     */

    // Dev0--1903
    /*-----------------------
      mblox enabled
    -----------------------*/
    String mbloxEnabled = Util.readINI( systemConfigPath + "provider.ini" ,
        "mblox" , "mbloxEnabled" );
    context.setAttribute( "mbloxEnabled" , mbloxEnabled );
    logStr = "MBlox Enabled = " + mbloxEnabled;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
      number of agents
    -----------------------*/
    String mbloxNumAgents = Util.readINI( systemConfigPath + "provider.ini" ,
        "mblox" , "mbloxNumAgents" );
    context.setAttribute( "mbloxNumAgents" , mbloxNumAgents );
    logStr = "Mblox Number of Agents = " + mbloxNumAgents;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
    mblox provider link
    -----------------------*/

    String mbloxProviderLink = Util.readINI( systemConfigPath + "provider.ini" ,
        "mblox" , "mbloxProviderLink" );
    context.setAttribute( "mbloxProviderLink" , mbloxProviderLink );
    logStr = "mblox Provider Link = " + mbloxProviderLink;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
      mblox account url First
    -----------------------*/
    String mbloxAccountUrlFirst = Util.readINI( systemConfigPath
        + "provider.ini" , "mblox" , "mbloxAccountUrlFirst" );
    context.setAttribute( "mbloxAccountUrlFirst" , mbloxAccountUrlFirst );
    logStr = "mblox Account URL First = " + mbloxAccountUrlFirst;
    context.log( logStr );
    System.out.println( logStr );
    /*-----------------------
    mblox account url Second
    -----------------------*/
    String mbloxAccountUrlSecond = Util.readINI( systemConfigPath
        + "provider.ini" , "mblox" , "mbloxAccountUrlSecond" );
    context.setAttribute( "mbloxAccountUrlSecond" , mbloxAccountUrlSecond );
    logStr = "mblox Account URL Second = " + mbloxAccountUrlSecond;
    context.log( logStr );
    System.out.println( logStr );

    /*
     * ==================================================== beepcast support
     * info ====================================================
     */
    /*-----------------------
      beepcast email
    -----------------------*/
    String beepcastEmail = context.getInitParameter( "beepcastEmail" );
    context.setAttribute( "beepcastEmail" , beepcastEmail );
    logStr = "Beepcast Email = " + beepcastEmail;
    context.log( logStr );
    System.out.println( logStr );

    /*-----------------------
      beepcast phone
    -----------------------*/
    String beepcastPhone = context.getInitParameter( "beepcastPhone" );
    context.setAttribute( "beepcastPhone" , beepcastPhone );
    logStr = "Beepcast Phone = " + beepcastPhone;
    context.log( logStr );
    System.out.println( logStr );

    /*
     * ==================================================== set global
     * attributes ====================================================
     */
    try {
      nameSvc.setAttribute( "commzgateEnabled" , commzgateEnabled );
      nameSvc.setAttribute( "commzgateNumAgents" , commzgateNumAgents );
      nameSvc.setAttribute( "commzgateProviderLink" , commzgateProviderLink );
      nameSvc.setAttribute( "commzgateAccountUrl" , commzgateAccountUrl );
      nameSvc.setAttribute( "commzgateCheckBalanceLink" ,
          commzgateCheckBalanceLink );
      nameSvc.setAttribute( "commzgateCheckBalanceUrl" ,
          commzgateCheckBalanceUrl );
      nameSvc.setAttribute( "commzgateLoginID" , commzgateLoginID );
      nameSvc.setAttribute( "commzgatePassword" , commzgatePassword );

      nameSvc.setAttribute( "mbloxEnabled" , mbloxEnabled );
      nameSvc.setAttribute( "mbloxNumAgents" , mbloxNumAgents );
      nameSvc.setAttribute( "mbloxProviderLink" , mbloxProviderLink );
      nameSvc.setAttribute( "mbloxAccountUrlFirst" , mbloxAccountUrlFirst ); // {Dev0--1903
      nameSvc.setAttribute( "mbloxAccountUrlSecond" , mbloxAccountUrlSecond );

      nameSvc.setAttribute( "beepcastEmail" , beepcastEmail );
      nameSvc.setAttribute( "beepcastPhone" , beepcastPhone );

    } catch ( IllegalArgumentException e ) {
    }

  } // contextInitialized()

  /*****************************************************************************
   * context destroyer
   ****************************************************************************/
  public void contextDestroyed( ServletContextEvent sce ) {
  }

} // eof
