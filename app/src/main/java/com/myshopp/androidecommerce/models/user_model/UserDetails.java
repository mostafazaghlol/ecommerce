package com.myshopp.androidecommerce.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class UserDetails {

    @SerializedName("customers_id")
    @Expose
    private String customersId;
    @SerializedName("customers_firstname")
    @Expose
    private String customersFirstname;
    @SerializedName("customers_lastname")
    @Expose
    private String customersLastname;
    @SerializedName("customers_dob")
    @Expose
    private String customersDob;
    @SerializedName("customers_gender")
    @Expose
    private String customersGender;
    @SerializedName("customers_picture")
    @Expose
    private String customersPicture;
    @SerializedName("customers_email_address")
    @Expose
    private String customersEmailAddress;
    @SerializedName("customers_password")
    @Expose
    private String customersPassword;
    @SerializedName("customers_telephone")
    @Expose
    private String customersTelephone;
    @SerializedName("customers_fax")
    @Expose
    private String customersFax;
    @SerializedName("customers_newsletter")
    @Expose
    private String customersNewsletter;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("isActive")
    @Expose
    private int isActive;
    @SerializedName("customers_default_address_id")
    @Expose
    private String customersDefaultAddressId;
    @SerializedName("liked_products")
    @Expose
    private List<UserLikedProducts> likedProducts = null;


    public String getCustomersId() {
        return customersId;
    }

    public void setCustomersId(String customersId) {
        this.customersId = customersId;
    }

    public String getCustomersGender() {
        return customersGender;
    }

    public void setCustomersGender(String customersGender) {
        this.customersGender = customersGender;
    }

    public String getCustomersFirstname() {
        return customersFirstname;
    }

    public void setCustomersFirstname(String customersFirstname) {
        this.customersFirstname = customersFirstname;
    }

    public String getCustomersLastname() {
        return customersLastname;
    }

    public void setCustomersLastname(String customersLastname) {
        this.customersLastname = customersLastname;
    }

    public String getCustomersDob() {
        return customersDob;
    }

    public void setCustomersDob(String customersDob) {
        this.customersDob = customersDob;
    }

    public String getCustomersEmailAddress() {
        return customersEmailAddress;
    }

    public void setCustomersEmailAddress(String customersEmailAddress) {
        this.customersEmailAddress = customersEmailAddress;
    }

    public String getCustomersDefaultAddressId() {
        return customersDefaultAddressId;
    }

    public void setCustomersDefaultAddressId(String customersDefaultAddressId) {
        this.customersDefaultAddressId = customersDefaultAddressId;
    }

    public String getCustomersTelephone() {
        return customersTelephone;
    }

    public void setCustomersTelephone(String customersTelephone) {
        this.customersTelephone = customersTelephone;
    }

    public String getCustomersFax() {
        return customersFax;
    }

    public void setCustomersFax(String customersFax) {
        this.customersFax = customersFax;
    }

    public String getCustomersPassword() {
        return customersPassword;
    }

    public void setCustomersPassword(String customersPassword) {
        this.customersPassword = customersPassword;
    }

    public String getCustomersNewsletter() {
        return customersNewsletter;
    }

    public void setCustomersNewsletter(String customersNewsletter) {
        this.customersNewsletter = customersNewsletter;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getCustomersPicture() {
        return customersPicture;
    }

    public void setCustomersPicture(String customersPicture) {
        this.customersPicture = customersPicture;
    }

    public List<UserLikedProducts> getLikedProducts() {
        return likedProducts;
    }

    public void setLikedProducts(List<UserLikedProducts> likedProducts) {
        this.likedProducts = likedProducts;
    }

}
