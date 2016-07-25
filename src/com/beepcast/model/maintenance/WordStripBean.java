package com.beepcast.model.maintenance;

import java.io.IOException;
import java.io.Serializable;

/*******************************************************************************
 * Word Strip Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class WordStripBean implements Serializable {

  private String words[];

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public WordStripBean() {
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
   * Select words to be stripped.
   * <p>
   * 
   * @return WordStripBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public WordStripBean select() throws IOException {
    return new WordStripDAO().select();
  }

} // eof
