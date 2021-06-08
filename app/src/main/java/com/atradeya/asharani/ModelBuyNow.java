
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelBuyNow {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("orderid")
    @Expose
    private Integer orderid;
    @SerializedName("Limitstatus")
    @Expose
    private Integer limitstatus;
    @SerializedName("result")
    @Expose
    private String result;

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

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getLimitstatus() {
        return limitstatus;
    }

    public void setLimitstatus(Integer limitstatus) {
        this.limitstatus = limitstatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
