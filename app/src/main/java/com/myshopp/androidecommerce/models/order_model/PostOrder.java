package com.myshopp.androidecommerce.models.order_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.myshopp.androidecommerce.models.coupons_model.CouponsInfo;


public class PostOrder {


    @SerializedName("customers_id")
    @Expose
    private int customersId;
    @SerializedName("customers_name")
    @Expose
    private String customersName;
    @SerializedName("customers_telephone")
    @Expose
    private String customersTelephone;
    @SerializedName("customers_email_address")
    @Expose
    private String customersEmailAddress;

    @SerializedName("delivery_firstname")
    @Expose
    private String deliveryFirstname;
    @SerializedName("delivery_lastname")
    @Expose
    private String deliveryLastname;
    @SerializedName("delivery_street_address")
    @Expose
    private String deliveryStreetAddress;
    @SerializedName("delivery_suburb")
    @Expose
    private String deliverySuburb;
    @SerializedName("delivery_postcode")
    @Expose
    private String deliveryPostcode;
    @SerializedName("delivery_city")
    @Expose
    private String deliveryCity;
    @SerializedName("delivery_zone")
    @Expose
    private String deliveryZone;
    @SerializedName("delivery_state")
    @Expose
    private String deliveryState;
    @SerializedName("delivery_country")
    @Expose
    private String deliveryCountry;
    @SerializedName("delivery_country_id")
    @Expose
    private String deliveryCountryId;
    @SerializedName("delivery_zone_id")
    @Expose
    private String deliveryZoneId;

    @SerializedName("billing_firstname")
    @Expose
    private String billingFirstname;
    @SerializedName("billing_lastname")
    @Expose
    private String billingLastname;
    @SerializedName("billing_street_address")
    @Expose
    private String billingStreetAddress;
    @SerializedName("billing_suburb")
    @Expose
    private String billingSuburb;
    @SerializedName("billing_postcode")
    @Expose
    private String billingPostcode;
    @SerializedName("billing_city")
    @Expose
    private String billingCity;
    @SerializedName("billing_zone")
    @Expose
    private String billingZone;
    @SerializedName("billing_state")
    @Expose
    private String billingState;
    @SerializedName("billing_country")
    @Expose
    private String billingCountry;
    @SerializedName("billing_country_id")
    @Expose
    private String billingCountryId;
    @SerializedName("billing_zone_id")
    @Expose
    private String billingZoneId;
    
    @SerializedName("tax_zone_id")
    @Expose
    private int taxZoneId;
    @SerializedName("total_tax")
    @Expose
    private double totalTax;
    @SerializedName("shipping_cost")
    @Expose
    private double shippingCost;
    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;


    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("is_coupon_applied")
    @Expose
    private int isCouponApplied;
    @SerializedName("coupon_amount")
    @Expose
    private double couponAmount;
    @SerializedName("coupons")
    @Expose
    private List<CouponsInfo> coupons;


    @SerializedName("nonce")
    @Expose
    private String nonce;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("productsTotal")
    @Expose
    private double productsTotal;
    @SerializedName("totalPrice")
    @Expose
    private double totalPrice;

    @SerializedName("products")
    @Expose
    private List<PostProducts> products = new ArrayList<>();
    
    
    
    public int getCustomersId() {
        return customersId;
    }
    
    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }
    
    public String getCustomersName() {
        return customersName;
    }
    
    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }
    
    public String getCustomersTelephone() {
        return customersTelephone;
    }
    
    public void setCustomersTelephone(String customersTelephone) {
        this.customersTelephone = customersTelephone;
    }
    
    public String getCustomersEmailAddress() {
        return customersEmailAddress;
    }
    
    public void setCustomersEmailAddress(String customersEmailAddress) {
        this.customersEmailAddress = customersEmailAddress;
    }
    
    public String getDeliveryFirstname() {
        return deliveryFirstname;
    }
    
    public void setDeliveryFirstname(String deliveryFirstname) {
        this.deliveryFirstname = deliveryFirstname;
    }
    
    public String getDeliveryLastname() {
        return deliveryLastname;
    }
    
    public void setDeliveryLastname(String deliveryLastname) {
        this.deliveryLastname = deliveryLastname;
    }
    
    public String getDeliveryStreetAddress() {
        return deliveryStreetAddress;
    }
    
    public void setDeliveryStreetAddress(String deliveryStreetAddress) {
        this.deliveryStreetAddress = deliveryStreetAddress;
    }
    
    public String getDeliverySuburb() {
        return deliverySuburb;
    }
    
    public void setDeliverySuburb(String deliverySuburb) {
        this.deliverySuburb = deliverySuburb;
    }
    
    public String getDeliveryPostcode() {
        return deliveryPostcode;
    }
    
    public void setDeliveryPostcode(String deliveryPostcode) {
        this.deliveryPostcode = deliveryPostcode;
    }
    
    public String getDeliveryCity() {
        return deliveryCity;
    }
    
    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }
    
    public String getDeliveryZone() {
        return deliveryZone;
    }
    
    public void setDeliveryZone(String deliveryZone) {
        this.deliveryZone = deliveryZone;
    }
    
    public String getDeliveryState() {
        return deliveryState;
    }
    
    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }
    
    public String getDeliveryCountry() {
        return deliveryCountry;
    }
    
    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }
    
    public String getBillingFirstname() {
        return billingFirstname;
    }
    
    public void setBillingFirstname(String billingFirstname) {
        this.billingFirstname = billingFirstname;
    }
    
    public String getBillingLastname() {
        return billingLastname;
    }
    
    public void setBillingLastname(String billingLastname) {
        this.billingLastname = billingLastname;
    }
    
    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }
    
    public void setBillingStreetAddress(String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress;
    }
    
    public String getBillingSuburb() {
        return billingSuburb;
    }
    
    public void setBillingSuburb(String billingSuburb) {
        this.billingSuburb = billingSuburb;
    }
    
    public String getBillingPostcode() {
        return billingPostcode;
    }
    
    public void setBillingPostcode(String billingPostcode) {
        this.billingPostcode = billingPostcode;
    }
    
    public String getBillingCity() {
        return billingCity;
    }
    
    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }
    
    public String getBillingZone() {
        return billingZone;
    }
    
    public void setBillingZone(String billingZone) {
        this.billingZone = billingZone;
    }
    
    public String getBillingState() {
        return billingState;
    }
    
    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }
    
    public String getBillingCountry() {
        return billingCountry;
    }
    
    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }
    
    public String getDeliveryCountryId() {
        return deliveryCountryId;
    }
    
    public void setDeliveryCountryId(String deliveryCountryId) {
        this.deliveryCountryId = deliveryCountryId;
    }
    
    public String getDeliveryZoneId() {
        return deliveryZoneId;
    }
    
    public void setDeliveryZoneId(String deliveryZoneId) {
        this.deliveryZoneId = deliveryZoneId;
    }
    
    public String getBillingCountryId() {
        return billingCountryId;
    }
    
    public void setBillingCountryId(String billingCountryId) {
        this.billingCountryId = billingCountryId;
    }
    
    public String getBillingZoneId() {
        return billingZoneId;
    }
    
    public void setBillingZoneId(String billingZoneId) {
        this.billingZoneId = billingZoneId;
    }
    
    public int getTaxZoneId() {
        return taxZoneId;
    }
    
    public void setTaxZoneId(int taxZoneId) {
        this.taxZoneId = taxZoneId;
    }
    
    public double getTotalTax() {
        return totalTax;
    }
    
    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }
    
    public double getShippingCost() {
        return shippingCost;
    }
    
    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }
    
    public String getShippingMethod() {
        return shippingMethod;
    }
    
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public int getIsCouponApplied() {
        return isCouponApplied;
    }
    
    public void setIsCouponApplied(int isCouponApplied) {
        this.isCouponApplied = isCouponApplied;
    }
    
    public double getCouponAmount() {
        return couponAmount;
    }
    
    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }
    
    public List<CouponsInfo> getCoupons() {
        return coupons;
    }
    
    public void setCoupons(List<CouponsInfo> coupons) {
        this.coupons = coupons;
    }
    
    public String getNonce() {
        return nonce;
    }
    
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public double getProductsTotal() {
        return productsTotal;
    }
    
    public void setProductsTotal(double productsTotal) {
        this.productsTotal = productsTotal;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public List<PostProducts> getProducts() {
        return products;
    }
    
    public void setProducts(List<PostProducts> products) {
        this.products = products;
    }
    
}
