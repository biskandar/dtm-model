package com.beepcast.model.specialMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpecialMessageName {

  public static final String INVALID_MENU_CHOICE = "Invalid menu choice";
  public static final String UNRECOGNIZED_CODE = "Unrecognized code";
  public static final String YOU_HAVE_UNSUBSCRIBED = "You have unsubscribed";
  public static final String UPLOAD_PHONE_LIST_OPTION = "UPLOAD_PHONE_LIST_OPTION";
  public static final String ADVANCED_EVENT_OPTION = "ADVANCED_EVENT_OPTION";
  public static final String ADVANCED_CHANNEL_OPTION = "ADVANCED_CHANNEL_OPTION";
  public static final String MAINTENANCE_NOTICES_FRONT_PAGE = "Maintenance Notices Front Page";
  public static final String MAINTENANCE_NOTICES_TOP_PAGE = "Maintenance Notices Top Page";
  public static final String UNRECOGNIZED_CLIENT_OR_EVENT = "Unrecognized client or event";
  public static final String SUPPORT_NOTIFICATION_TEMPLATE_1 = "Support Notification Template 1";
  public static final String SUPPORT_NOTIFICATION_TEMPLATE_2 = "Support Notification Template 2";
  public static final String SUPPORT_NOTIFICATION_TEMPLATE_3 = "Support Notification Template 3";

  public static List list() {
    List list = new ArrayList();

    list.add( INVALID_MENU_CHOICE );
    list.add( UNRECOGNIZED_CODE );
    list.add( YOU_HAVE_UNSUBSCRIBED );
    list.add( UPLOAD_PHONE_LIST_OPTION );
    list.add( ADVANCED_EVENT_OPTION );
    list.add( ADVANCED_CHANNEL_OPTION );
    list.add( MAINTENANCE_NOTICES_FRONT_PAGE );
    list.add( MAINTENANCE_NOTICES_TOP_PAGE );
    list.add( UNRECOGNIZED_CLIENT_OR_EVENT );
    list.add( SUPPORT_NOTIFICATION_TEMPLATE_1 );
    list.add( SUPPORT_NOTIFICATION_TEMPLATE_2 );
    list.add( SUPPORT_NOTIFICATION_TEMPLATE_3 );

    SpecialMessagesService service = new SpecialMessagesService();
    List listNames = service.selectActiveNames();
    Iterator iterNames = listNames.iterator();
    while ( iterNames.hasNext() ) {
      String name = (String) iterNames.next();
      if ( name == null ) {
        continue;
      }
      if ( list.indexOf( name ) > -1 ) {
        continue;
      }
      list.add( name );
    }

    return list;
  }

}
