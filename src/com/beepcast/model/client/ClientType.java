package com.beepcast.model.client;

public class ClientType {

  public static final int UNKNOWN = -1;
  public static final int SUPER = 0;
  public static final int MASTER = 1;
  public static final int USER = 2;

  public static String toString( int clientType ) {
    String clientTypeStr = "UNKNOWN";
    switch ( clientType ) {
    case SUPER :
      clientTypeStr = "SUPER";
      break;
    case MASTER :
      clientTypeStr = "MASTER";
      break;
    case USER :
      clientTypeStr = "USER";
      break;
    }
    return clientTypeStr;
  }

}
