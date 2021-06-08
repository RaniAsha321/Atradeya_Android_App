
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelWholesellers {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("wholselear")
    @Expose
    private List<Wholselear> wholselear = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<Wholselear> getWholselear() {
        return wholselear;
    }

    public void setWholselear(List<Wholselear> wholselear) {
        this.wholselear = wholselear;
    }

}
