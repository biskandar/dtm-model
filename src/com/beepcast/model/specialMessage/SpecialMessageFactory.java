package com.beepcast.model.specialMessage;

public class SpecialMessageFactory {

  public static SpecialMessageBean createSpecialMessageBean( String type ,
      String name , String content , String description ) {
    SpecialMessageBean bean = new SpecialMessageBean();
    bean.setType( type );
    bean.setName( name );
    bean.setContent( content );
    bean.setDescription( description );
    return bean;
  }

}
