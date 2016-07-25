package com.beepcast.model.provider;

import java.util.ArrayList;
import java.util.List;

public class ProviderType {

  public static final String TYPE_MODEM = "MODEM";
  public static final String TYPE_INTERNAL = "INTERNAL";
  public static final String TYPE_EXTERNAL = "EXTERNAL";

  public static List list() {
    List list = new ArrayList();
    list.add( TYPE_MODEM );
    list.add( TYPE_INTERNAL );
    list.add( TYPE_EXTERNAL );
    return list;
  }

}
