package com.beepcast.model.clientFile;

public class ClientFileBean {

  private int id;
  private int clientId;
  private String caption;
  private String fileName;
  private String fileType;
  private String webLink;
  private int sizeBytes;
  private int length;
  private int width;
  private boolean active;

  public ClientFileBean() {
  }

  public int getId() {
    return id;
  }

  public void setId( int id ) {
    this.id = id;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId( int clientId ) {
    this.clientId = clientId;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption( String caption ) {
    this.caption = caption;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName( String fileName ) {
    this.fileName = fileName;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType( String fileType ) {
    this.fileType = fileType;
  }

  public String getWebLink() {
    return webLink;
  }

  public void setWebLink( String webLink ) {
    this.webLink = webLink;
  }

  public int getSizeBytes() {
    return sizeBytes;
  }

  public void setSizeBytes( int sizeBytes ) {
    this.sizeBytes = sizeBytes;
  }

  public int getLength() {
    return length;
  }

  public void setLength( int length ) {
    this.length = length;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth( int width ) {
    this.width = width;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive( boolean active ) {
    this.active = active;
  }

  public String toString() {
    final String TAB = " ";
    String retValue = "";
    retValue = "ClientFileBean ( " + "id = " + this.id + TAB + "clientId = "
        + this.clientId + TAB + "caption = " + this.caption + TAB
        + "fileName = " + this.fileName + TAB + "fileType = " + this.fileType
        + TAB + "webLink = " + this.webLink + TAB + "sizeBytes = "
        + this.sizeBytes + TAB + "length = " + this.length + TAB + "width = "
        + this.width + TAB + "active = " + this.active + TAB + " )";
    return retValue;
  }

}
