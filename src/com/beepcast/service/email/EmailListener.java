package com.beepcast.service.email;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EmailListener extends Thread implements ServletContextListener {

  private final int SLEEP_TIME = 10000; // 10 seconds
  private final String BEEPCAST_SMTP_HOST = "smtp.beepcast.com";

  private String host = BEEPCAST_SMTP_HOST;
  private final String username = "services";
  private final String password = "ser1235";

  private boolean active;
  private ServletContext context;

  public void run() {

  } // run()

  public void saveError( EmailBean email , String errorMessage ) {

    System.out.println( "EMAIL ERROR: " + errorMessage );
    try {
      if ( email.getEmailErrorID() == 0L ) {
        email.setErrorMessage( errorMessage );
        // email.insert();
      } else {
        email.setNumRetries( email.getNumRetries() + 1 );
        email.setErrorMessage( errorMessage );
        // email.update();
      }
    } catch ( Exception e ) {
      System.out.println( e.getMessage() );
    }

  }

  public void contextInitialized( ServletContextEvent sce ) {
    try {
      context = sce.getServletContext();
      active = true;
      this.start();
      String logStr = "Email listener started ...";
      System.out.println( logStr );
      context.log( logStr );
    } catch ( Exception e ) {
      String msg = "EmailListener.contextInitialized(): " + e.getMessage();
      System.out.println( msg );
      context.log( msg );
    }
  }

  public void contextDestroyed( ServletContextEvent sce ) {
    try {
      context = sce.getServletContext();
      active = false;
      String logStr = "Email listener shutdown ...";
      System.out.println( logStr );
      context.log( logStr );
    } catch ( Exception e ) {
      String msg = "EmailListener.contextDestroyed(): " + e.getMessage();
      System.out.println( msg );
      context.log( msg );
    }
  }

}
