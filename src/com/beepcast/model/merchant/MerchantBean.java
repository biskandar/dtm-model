package com.beepcast.model.merchant;

import java.io.IOException;
import java.io.Serializable;

/*******************************************************************************
 * Merchant Bean.
 * <p>
 * 
 * @author Alan Megargel
 * @version 1.01
 ******************************************************************************/
public class MerchantBean implements Serializable {

  private long merchantID;
  private String storeName;
  private String address;
  private String phone;

  /*****************************************************************************
   * No-args constructor.
   ****************************************************************************/
  public MerchantBean() {
  }

  /*****************************************************************************
   * Set merchant ID.
   ****************************************************************************/
  public void setMerchantID( long merchantID ) {
    this.merchantID = merchantID;
  }

  /**
   * Get merchant ID.
   */
  public long getMerchantID() {
    return merchantID;
  }

  /*****************************************************************************
   * Set store name.
   ****************************************************************************/
  public void setStoreName( String storeName ) {
    this.storeName = storeName;
  }

  /**
   * Get store name.
   */
  public String getStoreName() {
    return storeName;
  }

  /*****************************************************************************
   * Set address.
   ****************************************************************************/
  public void setAddress( String address ) {
    this.address = address;
  }

  /**
   * Get address.
   */
  public String getAddress() {
    return address;
  }

  /*****************************************************************************
   * Set phone.
   ****************************************************************************/
  public void setPhone( String phone ) {
    this.phone = phone;
  }

  /**
   * Get phone.
   */
  public String getPhone() {
    return phone;
  }

  /*****************************************************************************
   * Insert merchant record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void insert() throws IOException {
    new MerchantDAO().insert( this );
  }

  /*****************************************************************************
   * Select merchant record.
   * <p>
   * 
   * @param merchantID
   * @return MerchantBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public MerchantBean select( long merchantID ) throws IOException {
    return new MerchantDAO().select( merchantID );
  }

  /*****************************************************************************
   * Select merchant record.
   * <p>
   * 
   * @param storeName
   * @return MerchantBean
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public MerchantBean select( String storeName ) throws IOException {
    return new MerchantDAO().select( storeName );
  }

  /*****************************************************************************
   * Update merchant record.
   * <p>
   * 
   * @throws IOException
   *           if database connection problem.
   ****************************************************************************/
  public void update() throws IOException {
    new MerchantDAO().update( this );
  }

} // eof
