package com.myshopp.androidecommerce.models.order_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.myshopp.androidecommerce.models.coupons_model.CouponsInfo;


public class OrderDetails implements Parcelable {

    @SerializedName("orders_id")
    @Expose
    private int ordersId;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("customers_id")
    @Expose
    private int customersId;
    @SerializedName("customers_name")
    @Expose
    private String customersName;
    @SerializedName("customers_company")
    @Expose
    private String customersCompany;
    @SerializedName("customers_street_address")
    @Expose
    private String customersStreetAddress;
    @SerializedName("customers_suburb")
    @Expose
    private String customersSuburb;
    @SerializedName("customers_city")
    @Expose
    private String customersCity;
    @SerializedName("customers_postcode")
    @Expose
    private String customersPostcode;
    @SerializedName("customers_state")
    @Expose
    private String customersState;
    @SerializedName("customers_country")
    @Expose
    private String customersCountry;
    @SerializedName("customers_telephone")
    @Expose
    private String customersTelephone;
    @SerializedName("customers_email_address")
    @Expose
    private String customersEmailAddress;
    @SerializedName("customers_address_format_id")
    @Expose
    private int customersAddressFormatId;
    @SerializedName("delivery_name")
    @Expose
    private String deliveryName;
    @SerializedName("delivery_company")
    @Expose
    private String deliveryCompany;
    @SerializedName("delivery_street_address")
    @Expose
    private String deliveryStreetAddress;
    @SerializedName("delivery_suburb")
    @Expose
    private String deliverySuburb;
    @SerializedName("delivery_city")
    @Expose
    private String deliveryCity;
    @SerializedName("delivery_postcode")
    @Expose
    private String deliveryPostcode;
    @SerializedName("delivery_state")
    @Expose
    private String deliveryState;
    @SerializedName("delivery_country")
    @Expose
    private String deliveryCountry;
    @SerializedName("delivery_address_format_id")
    @Expose
    private int deliveryAddressFormatId;
    @SerializedName("billing_name")
    @Expose
    private String billingName;
    @SerializedName("billing_company")
    @Expose
    private String billingCompany;
    @SerializedName("billing_street_address")
    @Expose
    private String billingStreetAddress;
    @SerializedName("billing_suburb")
    @Expose
    private String billingSuburb;
    @SerializedName("billing_city")
    @Expose
    private String billingCity;
    @SerializedName("billing_postcode")
    @Expose
    private String billingPostcode;
    @SerializedName("billing_state")
    @Expose
    private String billingState;
    @SerializedName("billing_country")
    @Expose
    private String billingCountry;
    @SerializedName("billing_address_format_id")
    @Expose
    private int billingAddressFormatId;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("cc_type")
    @Expose
    private String ccType;
    @SerializedName("cc_owner")
    @Expose
    private String ccOwner;
    @SerializedName("cc_number")
    @Expose
    private String ccNumber;
    @SerializedName("cc_expires")
    @Expose
    private String ccExpires;
    @SerializedName("last_modified")
    @Expose
    private String lastModified;
    @SerializedName("date_purchased")
    @Expose
    private String datePurchased;
    @SerializedName("orders_date_finished")
    @Expose
    private String ordersDateFinished;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_value")
    @Expose
    private String currencyValue;
    @SerializedName("order_price")
    @Expose
    private String orderPrice;
    @SerializedName("shipping_cost")
    @Expose
    private String shippingCost;
    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;
    @SerializedName("shipping_duration")
    @Expose
    private String shippingDuration;
    @SerializedName("order_information")
    @Expose
    private String orderInformation;
    @SerializedName("is_seen")
    @Expose
    private String isSeen;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("coupon_amount")
    @Expose
    private String couponAmount;
    @SerializedName("exclude_product_ids")
    @Expose
    private String excludeProductIds;
    @SerializedName("product_categories")
    @Expose
    private String productCategories;
    @SerializedName("excluded_product_categories")
    @Expose
    private String excludedProductCategories;
    @SerializedName("free_shipping")
    @Expose
    private String freeShipping;
    @SerializedName("product_ids")
    @Expose
    private String productIds;
    @SerializedName("orders_status_id")
    @Expose
    private String ordersStatusId;
    @SerializedName("orders_status")
    @Expose
    private String ordersStatus;
    @SerializedName("customer_comments")
    @Expose
    private String customerComments;
    @SerializedName("admin_comments")
    @Expose
    private String adminComments;
    @SerializedName("coupons")
    @Expose
    private List<CouponsInfo> coupons = new ArrayList<CouponsInfo>();
    @SerializedName("data")
    @Expose
    private List<OrderProducts> products = new ArrayList<OrderProducts>();



    /**
     * 
     * @return
     *     The ordersId
     */
    public int getOrdersId() {
        return ordersId;
    }

    /**
     * 
     * @param ordersId
     *     The orders_id
     */
    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    /**
     * 
     * @return
     *     The customersId
     */
    public int getCustomersId() {
        return customersId;
    }

    /**
     * 
     * @param customersId
     *     The customers_id
     */
    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    /**
     * 
     * @return
     *     The customersName
     */
    public String getCustomersName() {
        return customersName;
    }

    /**
     * 
     * @param customersName
     *     The customers_name
     */
    public void setCustomersName(String customersName) {
        this.customersName = customersName;
    }

    /**
     * 
     * @return
     *     The customersCompany
     */
    public String getCustomersCompany() {
        return customersCompany;
    }

    /**
     * 
     * @param customersCompany
     *     The customers_company
     */
    public void setCustomersCompany(String customersCompany) {
        this.customersCompany = customersCompany;
    }

    /**
     * 
     * @return
     *     The customersStreetAddress
     */
    public String getCustomersStreetAddress() {
        return customersStreetAddress;
    }

    /**
     * 
     * @param customersStreetAddress
     *     The customers_street_address
     */
    public void setCustomersStreetAddress(String customersStreetAddress) {
        this.customersStreetAddress = customersStreetAddress;
    }

    /**
     * 
     * @return
     *     The customersSuburb
     */
    public String getCustomersSuburb() {
        return customersSuburb;
    }

    /**
     * 
     * @param customersSuburb
     *     The customers_suburb
     */
    public void setCustomersSuburb(String customersSuburb) {
        this.customersSuburb = customersSuburb;
    }

    /**
     * 
     * @return
     *     The customersCity
     */
    public String getCustomersCity() {
        return customersCity;
    }

    /**
     * 
     * @param customersCity
     *     The customers_city
     */
    public void setCustomersCity(String customersCity) {
        this.customersCity = customersCity;
    }

    /**
     * 
     * @return
     *     The customersPostcode
     */
    public String getCustomersPostcode() {
        return customersPostcode;
    }

    /**
     * 
     * @param customersPostcode
     *     The customers_postcode
     */
    public void setCustomersPostcode(String customersPostcode) {
        this.customersPostcode = customersPostcode;
    }

    /**
     * 
     * @return
     *     The customersState
     */
    public String getCustomersState() {
        return customersState;
    }

    /**
     * 
     * @param customersState
     *     The customers_state
     */
    public void setCustomersState(String customersState) {
        this.customersState = customersState;
    }

    /**
     * 
     * @return
     *     The customersCountry
     */
    public String getCustomersCountry() {
        return customersCountry;
    }

    /**
     * 
     * @param customersCountry
     *     The customers_country
     */
    public void setCustomersCountry(String customersCountry) {
        this.customersCountry = customersCountry;
    }

    /**
     * 
     * @return
     *     The customersTelephone
     */
    public String getCustomersTelephone() {
        return customersTelephone;
    }

    /**
     * 
     * @param customersTelephone
     *     The customers_telephone
     */
    public void setCustomersTelephone(String customersTelephone) {
        this.customersTelephone = customersTelephone;
    }

    /**
     * 
     * @return
     *     The customersEmailAddress
     */
    public String getCustomersEmailAddress() {
        return customersEmailAddress;
    }

    /**
     * 
     * @param customersEmailAddress
     *     The customers_email_address
     */
    public void setCustomersEmailAddress(String customersEmailAddress) {
        this.customersEmailAddress = customersEmailAddress;
    }

    /**
     * 
     * @return
     *     The customersAddressFormatId
     */
    public int getCustomersAddressFormatId() {
        return customersAddressFormatId;
    }

    /**
     * 
     * @param customersAddressFormatId
     *     The customers_address_format_id
     */
    public void setCustomersAddressFormatId(int customersAddressFormatId) {
        this.customersAddressFormatId = customersAddressFormatId;
    }

    /**
     * 
     * @return
     *     The deliveryName
     */
    public String getDeliveryName() {
        return deliveryName;
    }

    /**
     * 
     * @param deliveryName
     *     The delivery_name
     */
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    /**
     * 
     * @return
     *     The deliveryCompany
     */
    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    /**
     * 
     * @param deliveryCompany
     *     The delivery_company
     */
    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    /**
     * 
     * @return
     *     The deliveryStreetAddress
     */
    public String getDeliveryStreetAddress() {
        return deliveryStreetAddress;
    }

    /**
     * 
     * @param deliveryStreetAddress
     *     The delivery_street_address
     */
    public void setDeliveryStreetAddress(String deliveryStreetAddress) {
        this.deliveryStreetAddress = deliveryStreetAddress;
    }

    /**
     * 
     * @return
     *     The deliverySuburb
     */
    public String getDeliverySuburb() {
        return deliverySuburb;
    }

    /**
     * 
     * @param deliverySuburb
     *     The delivery_suburb
     */
    public void setDeliverySuburb(String deliverySuburb) {
        this.deliverySuburb = deliverySuburb;
    }

    /**
     * 
     * @return
     *     The deliveryCity
     */
    public String getDeliveryCity() {
        return deliveryCity;
    }

    /**
     * 
     * @param deliveryCity
     *     The delivery_city
     */
    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    /**
     * 
     * @return
     *     The deliveryPostcode
     */
    public String getDeliveryPostcode() {
        return deliveryPostcode;
    }

    /**
     * 
     * @param deliveryPostcode
     *     The delivery_postcode
     */
    public void setDeliveryPostcode(String deliveryPostcode) {
        this.deliveryPostcode = deliveryPostcode;
    }

    /**
     * 
     * @return
     *     The deliveryState
     */
    public String getDeliveryState() {
        return deliveryState;
    }

    /**
     * 
     * @param deliveryState
     *     The delivery_state
     */
    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    /**
     * 
     * @return
     *     The deliveryCountry
     */
    public String getDeliveryCountry() {
        return deliveryCountry;
    }

    /**
     * 
     * @param deliveryCountry
     *     The delivery_country
     */
    public void setDeliveryCountry(String deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    /**
     * 
     * @return
     *     The deliveryAddressFormatId
     */
    public int getDeliveryAddressFormatId() {
        return deliveryAddressFormatId;
    }

    /**
     * 
     * @param deliveryAddressFormatId
     *     The delivery_address_format_id
     */
    public void setDeliveryAddressFormatId(int deliveryAddressFormatId) {
        this.deliveryAddressFormatId = deliveryAddressFormatId;
    }

    /**
     * 
     * @return
     *     The billingName
     */
    public String getBillingName() {
        return billingName;
    }

    /**
     * 
     * @param billingName
     *     The billing_name
     */
    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    /**
     * 
     * @return
     *     The billingCompany
     */
    public String getBillingCompany() {
        return billingCompany;
    }

    /**
     * 
     * @param billingCompany
     *     The billing_company
     */
    public void setBillingCompany(String billingCompany) {
        this.billingCompany = billingCompany;
    }

    /**
     * 
     * @return
     *     The billingStreetAddress
     */
    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }

    /**
     * 
     * @param billingStreetAddress
     *     The billing_street_address
     */
    public void setBillingStreetAddress(String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress;
    }

    /**
     * 
     * @return
     *     The billingSuburb
     */
    public String getBillingSuburb() {
        return billingSuburb;
    }

    /**
     * 
     * @param billingSuburb
     *     The billing_suburb
     */
    public void setBillingSuburb(String billingSuburb) {
        this.billingSuburb = billingSuburb;
    }

    /**
     * 
     * @return
     *     The billingCity
     */
    public String getBillingCity() {
        return billingCity;
    }

    /**
     * 
     * @param billingCity
     *     The billing_city
     */
    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    /**
     * 
     * @return
     *     The billingPostcode
     */
    public String getBillingPostcode() {
        return billingPostcode;
    }

    /**
     * 
     * @param billingPostcode
     *     The billing_postcode
     */
    public void setBillingPostcode(String billingPostcode) {
        this.billingPostcode = billingPostcode;
    }

    /**
     * 
     * @return
     *     The billingState
     */
    public String getBillingState() {
        return billingState;
    }

    /**
     * 
     * @param billingState
     *     The billing_state
     */
    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    /**
     * 
     * @return
     *     The billingCountry
     */
    public String getBillingCountry() {
        return billingCountry;
    }

    /**
     * 
     * @param billingCountry
     *     The billing_country
     */
    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    /**
     * 
     * @return
     *     The billingAddressFormatId
     */
    public int getBillingAddressFormatId() {
        return billingAddressFormatId;
    }

    /**
     * 
     * @param billingAddressFormatId
     *     The billing_address_format_id
     */
    public void setBillingAddressFormatId(int billingAddressFormatId) {
        this.billingAddressFormatId = billingAddressFormatId;
    }

    /**
     * 
     * @return
     *     The paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * 
     * @param paymentMethod
     *     The payment_method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * 
     * @return
     *     The ccType
     */
    public String getCcType() {
        return ccType;
    }

    /**
     * 
     * @param ccType
     *     The cc_type
     */
    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    /**
     * 
     * @return
     *     The ccOwner
     */
    public String getCcOwner() {
        return ccOwner;
    }

    /**
     * 
     * @param ccOwner
     *     The cc_owner
     */
    public void setCcOwner(String ccOwner) {
        this.ccOwner = ccOwner;
    }

    /**
     * 
     * @return
     *     The ccNumber
     */
    public String getCcNumber() {
        return ccNumber;
    }

    /**
     * 
     * @param ccNumber
     *     The cc_number
     */
    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    /**
     * 
     * @return
     *     The ccExpires
     */
    public String getCcExpires() {
        return ccExpires;
    }

    /**
     * 
     * @param ccExpires
     *     The cc_expires
     */
    public void setCcExpires(String ccExpires) {
        this.ccExpires = ccExpires;
    }

    /**
     * 
     * @return
     *     The lastModified
     */
    public String getLastModified() {
        return lastModified;
    }

    /**
     * 
     * @param lastModified
     *     The last_modified
     */
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * 
     * @return
     *     The datePurchased
     */
    public String getDatePurchased() {
        return datePurchased;
    }

    /**
     * 
     * @param datePurchased
     *     The date_purchased
     */
    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }

    /**
     * 
     * @return
     *     The ordersDateFinished
     */
    public String getOrdersDateFinished() {
        return ordersDateFinished;
    }

    /**
     * 
     * @param ordersDateFinished
     *     The orders_date_finished
     */
    public void setOrdersDateFinished(String ordersDateFinished) {
        this.ordersDateFinished = ordersDateFinished;
    }

    /**
     * 
     * @return
     *     The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * @param currency
     *     The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * @return
     *     The currencyValue
     */
    public String getCurrencyValue() {
        return currencyValue;
    }

    /**
     * 
     * @param currencyValue
     *     The currency_value
     */
    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    /**
     * 
     * @return
     *     The orderPrice
     */
    public String getOrderPrice() {
        return orderPrice;
    }

    /**
     * 
     * @param orderPrice
     *     The order_price
     */
    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }


    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingDuration() {
        return shippingDuration;
    }

    public void setShippingDuration(String shippingDuration) {
        this.shippingDuration = shippingDuration;
    }

    public String getOrderInformation() {
        return orderInformation;
    }

    public void setOrderInformation(String orderInformation) {
        this.orderInformation = orderInformation;
    }

    public String getOrdersStatusId() {
        return ordersStatusId;
    }

    public void setOrdersStatusId(String ordersStatusId) {
        this.ordersStatusId = ordersStatusId;
    }

    public String getOrdersStatus() {
        return ordersStatus;
    }

    public void setOrdersStatus(String ordersStatus) {
        this.ordersStatus = ordersStatus;
    }
    
    public String getTotalTax() {
        return totalTax;
    }
    
    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }
    
    public String getIsSeen() {
        return isSeen;
    }
    
    public void setIsSeen(String isSeen) {
        this.isSeen = isSeen;
    }
    
    public String getCouponCode() {
        return couponCode;
    }
    
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    
    public String getCouponAmount() {
        return couponAmount;
    }
    
    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }
    
    public String getExcludeProductIds() {
        return excludeProductIds;
    }
    
    public void setExcludeProductIds(String excludeProductIds) {
        this.excludeProductIds = excludeProductIds;
    }
    
    public String getProductCategories() {
        return productCategories;
    }
    
    public void setProductCategories(String productCategories) {
        this.productCategories = productCategories;
    }
    
    public String getExcludedProductCategories() {
        return excludedProductCategories;
    }
    
    public void setExcludedProductCategories(String excludedProductCategories) {
        this.excludedProductCategories = excludedProductCategories;
    }
    
    public String getFreeShipping() {
        return freeShipping;
    }
    
    public void setFreeShipping(String freeShipping) {
        this.freeShipping = freeShipping;
    }
    
    public String getProductIds() {
        return productIds;
    }
    
    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public String getCustomerComments() {
        return customerComments;
    }

    public void setCustomerComments(String customerComments) {
        this.customerComments = customerComments;
    }

    public String getAdminComments() {
        return adminComments;
    }

    public void setAdminComments(String adminComments) {
        this.adminComments = adminComments;
    }

    public List<CouponsInfo> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponsInfo> coupons) {
        this.coupons = coupons;
    }


    /**
     * 
     * @return
     *     The data
     */
    public List<OrderProducts> getProducts() {
        return products;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setProducts(List<OrderProducts> data) {
        this.products = data;
    }
    
    
    
    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    
    
    //********** Writes the values to the Parcel *********//
    
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeValue(ordersId);
        parcel_out.writeValue(totalTax);
        parcel_out.writeValue(customersId);
        parcel_out.writeValue(customersName);
        parcel_out.writeValue(customersCompany);
        parcel_out.writeValue(customersStreetAddress);
        parcel_out.writeValue(customersSuburb);
        parcel_out.writeValue(customersCity);
        parcel_out.writeValue(customersPostcode);
        parcel_out.writeValue(customersState);
        parcel_out.writeValue(customersCountry);
        parcel_out.writeValue(customersTelephone);
        parcel_out.writeValue(customersEmailAddress);
        parcel_out.writeValue(customersAddressFormatId);
        parcel_out.writeValue(deliveryName);
        parcel_out.writeValue(deliveryCompany);
        parcel_out.writeValue(deliveryStreetAddress);
        parcel_out.writeValue(deliverySuburb);
        parcel_out.writeValue(deliveryCity);
        parcel_out.writeValue(deliveryPostcode);
        parcel_out.writeValue(deliveryState);
        parcel_out.writeValue(deliveryCountry);
        parcel_out.writeValue(deliveryAddressFormatId);
        parcel_out.writeValue(billingName);
        parcel_out.writeValue(billingCompany);
        parcel_out.writeValue(billingStreetAddress);
        parcel_out.writeValue(billingSuburb);
        parcel_out.writeValue(billingCity);
        parcel_out.writeValue(billingPostcode);
        parcel_out.writeValue(billingState);
        parcel_out.writeValue(billingCountry);
        parcel_out.writeValue(billingAddressFormatId);
        parcel_out.writeValue(paymentMethod);
        parcel_out.writeValue(ccType);
        parcel_out.writeValue(ccOwner);
        parcel_out.writeValue(ccNumber);
        parcel_out.writeValue(ccExpires);
        parcel_out.writeValue(lastModified);
        parcel_out.writeValue(datePurchased);
        parcel_out.writeValue(ordersDateFinished);
        parcel_out.writeValue(currency);
        parcel_out.writeValue(currencyValue);
        parcel_out.writeValue(orderPrice);
        parcel_out.writeValue(shippingCost);
        parcel_out.writeValue(shippingMethod);
        parcel_out.writeValue(shippingDuration);
        parcel_out.writeValue(orderInformation);
        parcel_out.writeValue(isSeen);
        parcel_out.writeValue(couponCode);
        parcel_out.writeValue(couponAmount);
        parcel_out.writeValue(excludeProductIds);
        parcel_out.writeValue(productCategories);
        parcel_out.writeValue(excludedProductCategories);
        parcel_out.writeValue(freeShipping);
        parcel_out.writeValue(productIds);
        parcel_out.writeValue(ordersStatusId);
        parcel_out.writeValue(ordersStatus);
        parcel_out.writeValue(customerComments);
        parcel_out.writeValue(adminComments);
        parcel_out.writeList(coupons);
        parcel_out.writeList(products);
    }
    
    
    
    //********** Generates Instances of Parcelable class from a Parcel *********//
    
    public static final Creator<OrderDetails> CREATOR = new Creator<OrderDetails>() {
        
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public OrderDetails createFromParcel(Parcel parcel_in) {
            return new OrderDetails(parcel_in);
        }
        
        // Creates a new array of the Parcelable class
        @Override
        public OrderDetails[] newArray(int size) {
            return new OrderDetails[size];
        }
    };
    
    
    
    //********** Retrieves the values from the Parcel *********//
    
    protected OrderDetails(Parcel parcel_in) {
        this.ordersId = parcel_in.readInt();
        this.totalTax = parcel_in.readString();
        this.customersName = parcel_in.readString();
        this.customersCompany = parcel_in.readString();
        this.customersStreetAddress = parcel_in.readString();
        this.customersSuburb = parcel_in.readString();
        this.customersCity = parcel_in.readString();
        this.customersPostcode = parcel_in.readString();
        this.customersState = parcel_in.readString();
        this.customersCountry = parcel_in.readString();
        this.customersTelephone = parcel_in.readString();
        this.customersEmailAddress = parcel_in.readString();
        this.customersAddressFormatId = parcel_in.readInt();
        this.deliveryName = parcel_in.readString();
        this.deliveryCompany = parcel_in.readString();
        this.deliveryStreetAddress = parcel_in.readString();
        this.deliverySuburb = parcel_in.readString();
        this.deliveryCity = parcel_in.readString();
        this.deliveryPostcode = parcel_in.readString();
        this.deliveryState = parcel_in.readString();
        this.deliveryCountry = parcel_in.readString();
        this.deliveryAddressFormatId = parcel_in.readInt();
        this.billingName = parcel_in.readString();
        this.billingCompany = parcel_in.readString();
        this.billingStreetAddress = parcel_in.readString();
        this.billingSuburb = parcel_in.readString();
        this.billingCity = parcel_in.readString();
        this.billingPostcode = parcel_in.readString();
        this.billingState = parcel_in.readString();
        this.billingCountry = parcel_in.readString();
        this.billingAddressFormatId = parcel_in.readInt();
        this.paymentMethod = parcel_in.readString();
        this.ccType = parcel_in.readString();
        this.ccOwner = parcel_in.readString();
        this.ccNumber = parcel_in.readString();
        this.ccExpires = parcel_in.readString();
        this.lastModified = parcel_in.readString();
        this.datePurchased = parcel_in.readString();
        this.ordersDateFinished = parcel_in.readString();
        this.currency = parcel_in.readString();
        this.currencyValue = parcel_in.readString();
        this.orderPrice = parcel_in.readString();
        this.shippingCost = parcel_in.readString();
        this.shippingMethod = parcel_in.readString();
        this.shippingDuration = parcel_in.readString();
        this.orderInformation = parcel_in.readString();
        this.isSeen = parcel_in.readString();
        this.couponCode = parcel_in.readString();
        this.couponAmount = parcel_in.readString();
        this.excludeProductIds = parcel_in.readString();
        this.productCategories = parcel_in.readString();
        this.excludedProductCategories = parcel_in.readString();
        this.freeShipping = parcel_in.readString();
        this.productIds = parcel_in.readString();
        this.ordersStatusId = parcel_in.readString();
        this.ordersStatus = parcel_in.readString();
        this.customerComments = parcel_in.readString();
        this.adminComments = parcel_in.readString();

        this.coupons = new ArrayList<CouponsInfo>();
        parcel_in.readList(coupons, CouponsInfo.class.getClassLoader());

        this.products = new ArrayList<OrderProducts>();
        parcel_in.readList(products, OrderProducts.class.getClassLoader());
    }
    
}
