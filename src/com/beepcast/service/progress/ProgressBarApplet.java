package com.beepcast.service.progress;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.beepcast.util.StrTok;
import com.beepcast.util.Util;

/*******************************************************************************
 * Progress Bar Applet.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class ProgressBarApplet extends JApplet implements Runnable {

  String version = "Version: 2007-10-07";

  // components
  private JProgressBar progressBar;
  private JLabel lStatus = new JLabel( " " );

  // applet parameters
  private String backgroundColor = "f0f0f0";
  private String host = "localhost";
  private String progressID = "1";

  // other private attributes
  private String status = "";
  private boolean thisIsAnApplet = true;
  private boolean active = true;
  private int progressBarValue = 0;

  /*****************************************************************************
   * Init.
   * <p>
   ****************************************************************************/
  public void init() {

    /*-------------------------
      set system look and feel
    -------------------------*/
    try {
      UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
    } catch ( Exception e ) {
    }

    /*-------------------------
      get applet parameters
    -------------------------*/
    if ( thisIsAnApplet ) {
      backgroundColor = getParameter( "backgroundColor" );
      host = getParameter( "host" );
      progressID = getParameter( "progressID" );
    }

    /*--------------------------
      set background color
    --------------------------*/
    int rgb = Integer.parseInt( backgroundColor , 16 );

    /*--------------------------
      setup progress bar
    --------------------------*/
    progressBar = new JProgressBar();
    progressBar.setStringPainted( true );

    /*--------------------------
      setup center panel
    --------------------------*/
    JPanel pCenter = new JPanel( new GridBagLayout() );
    Util.addGB( pCenter , progressBar , 0 , 0 , 1 , 1 , 0 , 0 ,
        GridBagConstraints.NONE , GridBagConstraints.CENTER );
    pCenter.setBackground( new Color( rgb ) );

    /*--------------------------
      setup north panel
    --------------------------*/
    JPanel pNorth = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
    pNorth.add( new JLabel( " " ) );
    pNorth.setBackground( new Color( rgb ) );

    /*--------------------------
      setup south panel
    --------------------------*/
    JPanel pSouth = new JPanel( new FlowLayout( FlowLayout.CENTER ) );
    pSouth.add( lStatus );
    pSouth.setBackground( new Color( rgb ) );

    /*-------------------------
      set up content pane
    -------------------------*/
    JPanel pMain = new JPanel( new BorderLayout() );
    pMain.add( pNorth , BorderLayout.NORTH );
    pMain.add( pSouth , BorderLayout.SOUTH );
    pMain.add( pCenter , BorderLayout.CENTER );
    pMain.setBackground( new Color( rgb ) );

    /*-------------------------
      show frame, maximize
    -------------------------*/
    setContentPane( pMain );
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize( screenSize.width , screenSize.height );
    setVisible( true );
    setBackground( new Color( rgb ) );

    /*-------------------------
      show progress
    -------------------------*/
    if ( progressID != null && !progressID.equals( "" ) ) {
      Thread t = new Thread( this );
      t.start();
    }

  } // init()

  /*****************************************************************************
   * Show progress.
   * <p>
   ****************************************************************************/
  public void run() {

    /*----------------------------
     initialize progress bar
    ----------------------------*/
    ProgressBean progress = getProgress( progressID );
    progressBar.setMinimum( (int) progress.getMinimum() );
    progressBar.setMaximum( (int) progress.getMaximum() );
    progressBar.setValue( (int) progress.getValue() );

    /*----------------------------
     progress bar updater
    ----------------------------*/
    Runnable runner = new Runnable() {
      public void run() {
        progressBar.setValue( progressBarValue );
        lStatus.setText( status );
      }
    };

    /*----------------------------
     while progress
    ----------------------------*/
    int maximum = (int) progress.getMaximum();
    while ( progressBarValue < maximum ) {

      /*-----------------------
        sleep for 100 seconds
      -----------------------*/
      try {
        Thread.sleep( 100 ); // 100 milliseconds
      } catch ( InterruptedException e ) {
      }

      /*---------------------------
        get progress
      ---------------------------*/
      progress = getProgress( progressID );
      if ( !active )
        break;
      progressBarValue = (int) progress.getValue();
      status = "Records=" + progressBarValue;

      /*---------------------------
        update progress bar
      ---------------------------*/
      try {
        SwingUtilities.invokeAndWait( runner );
      } catch ( InvocationTargetException e ) {
        break;
      } catch ( InterruptedException e ) {
        // Ignore Exception
      }

    } // while progress...

  } // run()

  /*****************************************************************************
   * Get progress.
   * <p>
   ****************************************************************************/
  private ProgressBean getProgress( String progressID ) {

    ProgressBean progress = new ProgressBean();

    try {

      /*--------------------------
        "get_progress"
      --------------------------*/
      String command = "get_progress";
      String payload = "command=" + URLEncoder.encode( command )
          + "&progress_id=" + URLEncoder.encode( progressID ) + "";

      /*--------------------------
        open url conn to beepcast server
      --------------------------*/
      URL url = new URL( "http://" + host + ":8080/beepadmin/progress_action" );
      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
      httpConn.setRequestMethod( "POST" );
      httpConn.setDoInput( true );
      httpConn.setDoOutput( true );
      httpConn.setUseCaches( false );

      /*--------------------------
        send payload to beepcast server
      --------------------------*/
      DataOutputStream os = new DataOutputStream( httpConn.getOutputStream() );
      os.writeBytes( payload );
      os.flush();
      os.close();

      /*--------------------------
        get response from beepadmin server
      --------------------------*/
      BufferedReader in = new BufferedReader( new InputStreamReader(
          httpConn.getInputStream() ) );
      String response = "";
      String line = "";
      while ( ( line = in.readLine() ) != null ) {
        response += line;
      }
      in.close();

      /*--------------------------
        tokenize the response string
      --------------------------*/
      StrTok st = new StrTok( response , "&" );
      int numTokens = st.countTokens();
      st.nextTok(); // discard leading "&"
      for ( int i = 1 ; i < numTokens ; i++ ) {
        StrTok param = new StrTok( st.nextTok() , "=" );
        String name = param.nextTok();
        String value = param.nextTok();
        if ( name.equals( "ERROR" ) ) {
          status = response;
          lStatus.setText( status );
          active = false;
          break;
        } else if ( name.equals( "status" ) && !value.equals( "1000" ) ) {
          status = response;
          lStatus.setText( status );
          active = false;
          break;
        } else if ( name.equals( "minimum" ) )
          progress.setMinimum( Long.parseLong( value ) );
        else if ( name.equals( "maximum" ) )
          progress.setMaximum( Long.parseLong( value ) );
        else if ( name.equals( "value" ) )
          progress.setValue( Long.parseLong( value ) );
        else if ( name.equals( "error_message" ) ) {
          progress.setErrorMessage( value );
          if ( !value.equals( "" ) ) {
            status = value;
            lStatus.setText( status );
            active = false;
            break;
          }
        }

      } // for each token...

      /*--------------------------
        handle exceptions
      --------------------------*/
    } catch ( Exception e ) {
      status = e.getMessage();
      lStatus.setText( status );
      active = false;
    }

    /*--------------------------
      success
    --------------------------*/
    return progress;

  } // getProgress()

} // eof
