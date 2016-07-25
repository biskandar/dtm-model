package com.beepcast.model.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public class UsersBean {

  public static final int SORT_BY_ID_ASC = 0;
  public static final int SORT_BY_ID_DESC = 1;
  public static final int SORT_BY_USERID_ASC = 2;
  public static final int SORT_BY_USERID_DESC = 3;

  private HashMap userMap;

  public UsersBean() {
    userMap = new HashMap();
  }

  public void reset() {
    userMap.clear();
  }

  public long totalRecords() {
    return userMap.size();
  }

  public boolean addUserBean( UserBean userBean ) {
    boolean result = false;
    if ( !verifyUserBean( userBean ) ) {
      return result;
    }
    userMap.put( keyUserBean( userBean ) , userBean );
    result = true;
    return result;
  }

  public UserBean getUserBean( String userID ) {
    UserBean userBean = null;
    if ( !StringUtils.isBlank( userID ) ) {
      userBean = (UserBean) userMap.get( userID );
    }
    return userBean;
  }

  public ArrayList listUserBean( int sortBy ) {
    ArrayList arrayList = new ArrayList( userMap.values() );
    switch ( sortBy ) {
    case SORT_BY_ID_ASC :
      Collections.sort( arrayList , new SortedById( true ) );
      break;
    case SORT_BY_ID_DESC :
      Collections.sort( arrayList , new SortedById( false ) );
      break;
    case SORT_BY_USERID_ASC :
      Collections.sort( arrayList , new SortedByUserId( true ) );
      break;
    case SORT_BY_USERID_DESC :
      Collections.sort( arrayList , new SortedByUserId( false ) );
      break;
    }
    return arrayList;
  }

  private boolean verifyUserBean( UserBean userBean ) {
    boolean verify = false;
    if ( userBean == null ) {
      return verify;
    }
    String userID = userBean.getUserID();
    if ( StringUtils.isBlank( userID ) ) {
      return verify;
    }
    verify = true;
    return verify;
  }

  private String keyUserBean( UserBean userBean ) {
    String key = null;
    if ( userBean != null ) {
      key = userBean.getUserID();
    }
    return key;
  }

  class SortedById implements Comparator {

    private boolean asc;

    public SortedById( boolean asc ) {
      this.asc = asc;
    }

    public int compare( Object left , Object right ) {
      int result = 0;

      if ( !( left instanceof UserBean ) ) {
        return result;
      }
      if ( !( right instanceof UserBean ) ) {
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

      UserBean userBeanLeft = (UserBean) left;
      UserBean userBeanRight = (UserBean) right;

      int idLeft = userBeanLeft.getId();
      int idRight = userBeanRight.getId();

      if ( idLeft != idRight ) {
        if ( idLeft < idRight ) {
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

  } // SortedById

  class SortedByUserId implements Comparator {

    private boolean asc;

    public SortedByUserId( boolean asc ) {
      this.asc = asc;
    }

    public int compare( Object left , Object right ) {
      int result = 0;

      if ( !( left instanceof UserBean ) ) {
        return result;
      }
      if ( !( right instanceof UserBean ) ) {
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

      UserBean userBeanLeft = (UserBean) left;
      UserBean userBeanRight = (UserBean) right;

      String userIdLeft = userBeanLeft.getUserID();
      String userIdRight = userBeanRight.getUserID();

      result = compval( userIdLeft.compareToIgnoreCase( userIdRight ) );

      return result;
    }

    private int compval( int val ) {
      int result = val;
      if ( ( !asc ) && ( val != 0 ) ) {
        result = val * -1;
      }
      return result;
    }

  } // SortedByUserId

}
