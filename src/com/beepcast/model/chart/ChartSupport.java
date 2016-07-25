package com.beepcast.model.chart;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.beepcast.util.Util;

/*******************************************************************************
 * Chart support class.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class ChartSupport {

  /*---------------------------
      colors
    ---------------------------*/
  public static final int colors[] = { 0xff0000 , // red
      0x00ff00 , // green
      0x0000ff , // blue
      0xffff00 , // yellow
      0xff00ff , // magenta
      0x00ffff , // cyan
      0x990000 , // dark red
      0x009900 , // dark green
      0x000099 , // dark blue
      0x999900 , // dark yellow
      0x990099 , // dark magenta
      0x009999 // dark cyan
  };
  public static final String sColors[] = { "ff0000" , // red
      "00ff00" , // green
      "0000ff" , // blue
      "ffff00" , // yellow
      "ff00ff" , // magenta
      "00ffff" , // cyan
      "990000" , // dark red
      "009900" , // dark green
      "000099" , // dark blue
      "999900" , // dark yellow
      "990099" , // dark magenta
      "009999" // dark cyan
  };

  /*****************************************************************************
   * Sort dataset and xLabels to support kavachart.
   * 
   * @param sb_dataset
   * @param sb_xLabels
   ****************************************************************************/
  public void sortDataset( StringBuffer sb_dataset , StringBuffer sb_xLabels ) {
    sortDataset( sb_dataset , sb_xLabels , false );
  }

  /*****************************************************************************
   * Sort dataset and xLabels to support kavachart.
   * 
   * @param sb_dataset
   * @param sb_xLabels
   * @param desc
   *          If true, sorts dataset in descending order.
   ****************************************************************************/
  public void sortDataset( StringBuffer sb_dataset , StringBuffer sb_xLabels ,
      boolean desc ) {

    /*---------------------------
        make arrays
      ---------------------------*/
    String dataset[] = StringUtils.split( sb_dataset.toString() , "," );
    String xLabels[] = StringUtils.split( sb_xLabels.toString() , "," );
    int numPoints = dataset.length;

    /*---------------------------
        sort dataset
      ---------------------------*/
    long data[] = new long[numPoints];
    for ( int i = 0 ; i < numPoints ; i++ )
      data[i] = Long.parseLong( dataset[i] );
    Arrays.sort( data );
    String datasetList = "";
    for ( int i = 0 ; i < numPoints ; i++ )
      datasetList += "" + data[i] + ",";
    datasetList = datasetList.substring( 0 , datasetList.length() - 1 );
    if ( desc )
      datasetList = Util.reverseList( datasetList );
    String sortedDataset[] = StringUtils.split( datasetList , "," );

    /*---------------------------
        sort xLabels
      ---------------------------*/
    String xLabelList = "";
    for ( int j = 0 ; j < numPoints ; j++ ) {
      for ( int k = 0 ; k < numPoints ; k++ ) {
        if ( dataset[k].equals( sortedDataset[j] ) ) {
          xLabelList += xLabels[k] + ",";
          dataset[k] = "";
          break;
        }
      }
    }
    xLabelList = xLabelList.substring( 0 , xLabelList.length() - 1 );

    /*---------------------------
        reload string buffers
      ---------------------------*/
    try {
      sb_dataset.replace( 0 , datasetList.length() , datasetList );
      sb_xLabels.replace( 0 , xLabelList.length() , xLabelList );
    } catch ( Exception e ) {
    }

  } // sortDataset()

} // eof
