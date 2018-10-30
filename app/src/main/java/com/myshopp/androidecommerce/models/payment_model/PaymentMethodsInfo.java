
package com.myshopp.androidecommerce.models.payment_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PaymentMethodsInfo {
    
    @SerializedName("environment")
    @Expose
    private String environment;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("public_key")
    @Expose
    private String publicKey;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("payment_currency")
    @Expose
    private String paymentCurrency;
    
    
    public String getEnvironment() {
        return environment;
    }
    
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
    
    public String getPaymentCurrency() {
        return paymentCurrency;
    }
    
    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }
}

