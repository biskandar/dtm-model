package com.beepcast.model.clientFile;

public class ClientFileFactory {

  public static ClientFileBean createClientFileBean( int clientId ,
      String caption , String fileName , String fileType , String webLink ,
      int sizeBytes , int length , int width ) {
    ClientFileBean bean = new ClientFileBean();
    bean.setClientId( clientId );
    bean.setCaption( caption );
    bean.setFileName( fileName );
    bean.setFileType( fileType );
    bean.setWebLink( webLink );
    bean.setSizeBytes( sizeBytes );
    bean.setLength( length );
    bean.setWidth( width );
    bean.setActive( true );
    return bean;
  }

}
