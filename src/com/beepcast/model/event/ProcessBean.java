package com.beepcast.model.event;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class ProcessBean implements Cloneable {

  private String step;
  private String type;
  private String paramLabel;
  private String names[];
  private String response;
  private String rfa;
  private String nextStep;
  private String nextType;

  public ProcessBean() {
  }

  public void setStep( String step ) {
    this.step = step;
  }

  public String getStep() {
    return step;
  }

  public void setType( String type ) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setParamLabel( String paramLabel ) {
    this.paramLabel = paramLabel;
  }

  public String getParamLabel() {
    return paramLabel;
  }

  public void setNames( String names[] ) {
    this.names = names;
  }

  public String[] getNames() {
    return names;
  }

  public void setResponse( String response ) {
    this.response = response;
  }

  public String getResponse() {
    return response;
  }

  public void setRfa( String rfa ) {
    this.rfa = rfa;
  }

  public String getRfa() {
    return rfa;
  }

  public void setNextStep( String nextStep ) {
    this.nextStep = nextStep;
  }

  public String getNextStep() {
    return nextStep;
  }

  public void setNextType( String nextType ) {
    this.nextType = nextType;
  }

  public String getNextType() {
    return nextType;
  }

  public Object clone() {
    ProcessBean cloned = null;
    try {
      cloned = (ProcessBean) super.clone();
    } catch ( CloneNotSupportedException e ) {
    }
    return cloned;
  }

  // ////////////////////////////////////////////////////////////////////////////
  //
  // Helper
  //
  // ////////////////////////////////////////////////////////////////////////////

  public String toString() {
    final String TAB = " ";
    String retValue = "";

    String strNames = "[" + StringUtils.join( this.names , "," ) + "]";
    String strResponse = "[" + StringEscapeUtils.escapeJava( this.response )
        + "]";
    String strRfa = "[" + StringEscapeUtils.escapeJava( this.rfa ) + "]";

    retValue = "ProcessBean ( " + "step = " + this.step + TAB + "type = "
        + this.type + TAB + "paramLabel = " + this.paramLabel + TAB
        + "names = " + strNames + TAB + "response = " + strResponse + TAB
        + "rfa = " + strRfa + TAB + "nextStep = " + this.nextStep + TAB
        + "nextType = " + this.nextType + TAB + " )";

    return retValue;
  }

}
