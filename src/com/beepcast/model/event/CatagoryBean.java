package com.beepcast.model.event;

import java.io.IOException;
import java.io.Serializable;

public class CatagoryBean implements Serializable {

  private static final long serialVersionUID = 5140281778781380751L;

  private long catagoryID;
  private String catagoryName;

  public CatagoryBean() {
  }

  public void setCatagoryID( long catagoryID ) {
    this.catagoryID = catagoryID;
  }

  public long getCatagoryID() {
    return catagoryID;
  }

  public void setCatagoryName( String catagoryName ) {
    this.catagoryName = catagoryName;
  }

  public String getCatagoryName() {
    return catagoryName;
  }

  public void insert() throws IOException {
    new CatagoryDAO().insert( this );
  }

  public CatagoryBean select( long catagoryID ) throws IOException {
    return new CatagoryDAO().select( catagoryID );
  }

  public CatagoryBean select( String catagoryName ) throws IOException {
    return new CatagoryDAO().select( catagoryName );
  }

}
