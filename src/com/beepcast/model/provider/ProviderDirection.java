package com.beepcast.model.provider;

import java.util.ArrayList;
import java.util.List;

public class ProviderDirection {

  public static final String DIRECTION_IN = "IN";
  public static final String DIRECTION_OU = "OU";
  public static final String DIRECTION_IO = "IO";

  public static List list() {
    List list = new ArrayList();
    list.add( DIRECTION_IN );
    list.add( DIRECTION_OU );
    list.add( DIRECTION_IO );
    return list;
  }

}
