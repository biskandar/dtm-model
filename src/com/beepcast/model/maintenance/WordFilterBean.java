package com.beepcast.model.maintenance;

import java.io.IOException;
import java.io.Serializable;

/*******************************************************************************
 * Word Filter Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class WordFilterBean implements Serializable {

  private String words[];

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public WordFilterBean() {
  }

  /*****************************************************************************
   * Set words.
   ****************************************************************************/
  public void setWords( String words[] ) {
    this.words = words;
  }

  /**
   * Get words.
   */
  public String[] getWords() {
    return words;
  }

  /*****************************************************************************
   * Select words to be filtered.
   * <p>
   * 
   * @return WordFilterBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public WordFilterBean select() throws IOException {
    return new WordFilterDAO().select();
  }

} // eof
