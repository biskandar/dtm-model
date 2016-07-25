package com.beepcast.model.specialMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpecialMessageType {

  public static final String SMS = "SMS";
  public static final String WEB = "WEB";
  public static final String EMAIL = "EMAIL";
  public static final String EMAIL_ADDRESS = "EMAIL.ADDRESS";

  public static List list() {
    List list = new ArrayList();

    list.add( SMS );
    list.add( WEB );
    list.add( EMAIL );

    SpecialMessagesService service = new SpecialMessagesService();
    List listTypes = service.selectActiveTypes();
    Iterator iterTypes = listTypes.iterator();
    while ( iterTypes.hasNext() ) {
      String type = (String) iterTypes.next();
      if ( type == null ) {
        continue;
      }
      if ( list.indexOf( type ) > -1 ) {
        continue;
      }
      list.add( type );
    }

    return list;
  }

}
