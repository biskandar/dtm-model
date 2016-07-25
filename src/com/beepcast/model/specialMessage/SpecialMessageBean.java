package com.beepcast.model.specialMessage;

import org.apache.commons.lang.StringEscapeUtils;

public class SpecialMessageBean {

  private int id;
  private String type;
  private String name;
  private String content;
  private String description;

  public SpecialMessageBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType( String type ) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent( String content ) {
    this.content = content;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "SpecialMessageBean ( " + "id = " + this.id + TAB + "type = "
        + StringEscapeUtils.escapeJava( this.type ) + TAB + "name = "
        + StringEscapeUtils.escapeJava( this.name ) + TAB + "content = "
        + StringEscapeUtils.escapeJava( this.content ) + TAB + "description = "
        + StringEscapeUtils.escapeJava( this.description ) + TAB + " )";
    return retValue;
  }

}
