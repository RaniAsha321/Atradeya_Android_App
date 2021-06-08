package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelMyOrder {

    @SerializedName("total_ex")
    @Expose
    private String totalEx;
    @SerializedName("total_inc")
    @Expose
    private String totalInc;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("SingleOrderData")
    @Expose
    private List<SingleOrderDatum> singleOrderData = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getTotalEx() {
        return totalEx;
    }

    public void setTotalEx(String totalEx) {
        this.totalEx = totalEx;
    }

    public String getTotalInc() {
        return totalInc;
    }

    public void setTotalInc(String totalInc) {
        this.totalInc = totalInc;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<SingleOrderDatum> getSingleOrderData() {
        return singleOrderData;
    }

    public void setSingleOrderData(List<SingleOrderDatum> singleOrderData) {
        this.singleOrderData = singleOrderData;
    }

}

