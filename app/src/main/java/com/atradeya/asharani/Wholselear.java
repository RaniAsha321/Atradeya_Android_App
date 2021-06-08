
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wholselear {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("bussiness_logo")
    @Expose
    private String bussinessLogo;
    @SerializedName("stripe_publishkey")
    @Expose
    private String stripePublishkey;
    @SerializedName("business")
    @Expose
    private String business;
    @SerializedName("business_id")
    @Expose
    private String businessId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBussinessLogo() {
        return bussinessLogo;
    }

    public void setBussinessLogo(String bussinessLogo) {
        this.bussinessLogo = bussinessLogo;
    }

    public String getStripePublishkey() {
        return stripePublishkey;
    }

    public void setStripePublishkey(String stripePublishkey) {
        this.stripePublishkey = stripePublishkey;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

}
