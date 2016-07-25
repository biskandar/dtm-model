package com.beepcast.model.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public class ClientsBean {

  public static final int SORT_BY_CLIENTID_ASC = 0;
  public static final int SORT_BY_CLIENTID_DESC = 1;
  public static final int SORT_BY_COMPANYNAME_ASC = 2;
  public static final int SORT_BY_COMPANYNAME_DESC = 3;

  private HashMap clientMap;

  public ClientsBean() {
    clientMap = new HashMap();
  }

  public void reset() {
    clientMap.clear();
  }

  public long totalRecords() {
    return clientMap.size();
  }

  public boolean addClientBean( ClientBean clientBean ) {
    boolean result = false;
    if ( !verifyClientBean( clientBean ) ) {
      return result;
    }
    clientMap.put( keyClientBean( clientBean ) , clientBean );
    result = true;
    return result;
  }

  public ClientBean getClientBean( String companyName ) {
    ClientBean clientBean = null;
    if ( !StringUtils.isBlank( companyName ) ) {
      clientBean = (ClientBean) clientMap.get( companyName );
    }
    return clientBean;
  }

  public ArrayList listClientBean( int sortBy ) {
    ArrayList arrayList = new ArrayList( clientMap.values() );
    switch ( sortBy ) {
    case SORT_BY_CLIENTID_ASC :
      Collections.sort( arrayList , new SortedByClientId( true ) );
      break;
    case SORT_BY_CLIENTID_DESC :
      Collections.sort( arrayList , new SortedByClientId( false ) );
      break;
    case SORT_BY_COMPANYNAME_ASC :
      Collections.sort( arrayList , new SortedByCompanyName( true ) );
      break;
    case SORT_BY_COMPANYNAME_DESC :
      Collections.sort( arrayList , new SortedByCompanyName( false ) );
      break;
    }
    return arrayList;
  }

  private boolean verifyClientBean( ClientBean clientBean ) {
    boolean verify = false;
    if ( clientBean == null ) {
      return verify;
    }
    String companyName = clientBean.getCompanyName();
    if ( StringUtils.isBlank( companyName ) ) {
      return verify;
    }
    verify = true;
    return verify;
  }

  private String keyClientBean( ClientBean clientBean ) {
    String key = null;
    if ( clientBean != null ) {
      key = clientBean.getCompanyName();
    }
    return key;
  }

  class SortedByClientId implements Comparator {

    private boolean asc;

    public SortedByClientId( boolean asc ) {
      this.asc = asc;
    }

    public int compare( Object left , Object right ) {
      int result = 0;

      if ( !( left instanceof ClientBean ) ) {
        return result;
      }
      if ( !( right instanceof ClientBean ) ) {
        return result;
      }

      if ( ( left == null ) && ( right == null ) ) {
        return result;
      }
      if ( ( left != null ) && ( right == null ) ) {
        return compval( -1 );
      }
      if ( ( left == null ) && ( right != null ) ) {
        return compval( +1 );
      }

      ClientBean clientBeanLeft = (ClientBean) left;
      ClientBean clientBeanRight = (ClientBean) right;

      long clientIdLeft = clientBeanLeft.getClientID();
      long clientIdRight = clientBeanRight.getClientID();

      if ( clientIdLeft != clientIdRight ) {
        if ( clientIdLeft < clientIdRight ) {
          result = compval( -1 );
        } else {
          result = compval( +1 );
        }
      }

      return result;
    }

    private int compval( int val ) {
      int result = val;
      if ( !asc ) {
        if ( val == +1 ) {
          result = -1;
        }
        if ( val == -1 ) {
          result = +1;
        }
      }
      return result;
    }

  } // SortedByClientId

  class SortedByCompanyName implements Comparator {

    private boolean asc;

    public SortedByCompanyName( boolean asc ) {
      this.asc = asc;
    }

    public int compare( Object left , Object right ) {
      int result = 0;

      if ( !( left instanceof ClientBean ) ) {
        return result;
      }
      if ( !( right instanceof ClientBean ) ) {
        return result;
      }

      if ( ( left == null ) && ( right == null ) ) {
        return result;
      }
      if ( ( left != null ) && ( right == null ) ) {
        return compval( -1 );
      }
      if ( ( left == null ) && ( right != null ) ) {
        return compval( +1 );
      }

      ClientBean clientBeanLeft = (ClientBean) left;
      ClientBean clientBeanRight = (ClientBean) right;

      String companyNameLeft = clientBeanLeft.getCompanyName();
      String companyNameRight = clientBeanRight.getCompanyName();

      result = compval( companyNameLeft.compareToIgnoreCase( companyNameRight ) );

      return result;
    }

    private int compval( int val ) {
      int result = val;
      if ( ( !asc ) && ( val != 0 ) ) {
        result = val * -1;
      }
      return result;
    }

  } // SortedByCompanyName

}
