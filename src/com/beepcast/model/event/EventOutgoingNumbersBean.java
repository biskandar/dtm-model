package com.beepcast.model.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventOutgoingNumbersBean {

  private List listOutgoingNumbers;

  public EventOutgoingNumbersBean() {
    listOutgoingNumbers = new ArrayList();
  }

  public List listOutgoingNumbers() {
    return listOutgoingNumbers;
  }

  public Iterator iterOutgoingNumbers() {
    return listOutgoingNumbers.iterator();
  }

}
