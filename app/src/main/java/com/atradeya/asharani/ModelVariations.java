
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelVariations {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("hasornot")
    @Expose
    private Integer hasornot;
    @SerializedName("variation")
    @Expose
    private List<Variation> variation = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getHasornot() {
        return hasornot;
    }

    public void setHasornot(Integer hasornot) {
        this.hasornot = hasornot;
    }

    public List<Variation> getVariation() {
        return variation;
    }

    public void setVariation(List<Variation> variation) {
        this.variation = variation;
    }
}
