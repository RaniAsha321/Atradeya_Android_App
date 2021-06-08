
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoreDetails {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("ordermoredetails")
    @Expose
    private Ordermoredetails ordermoredetails;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Ordermoredetails getOrdermoredetails() {
        return ordermoredetails;
    }

    public void setOrdermoredetails(Ordermoredetails ordermoredetails) {
        this.ordermoredetails = ordermoredetails;
    }

}
