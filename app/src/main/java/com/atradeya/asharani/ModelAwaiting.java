
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAwaiting {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("AwatingOrderData")
    @Expose
    private List<AwatingOrderDatum> awatingOrderData = null;

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

    public List<AwatingOrderDatum> getAwatingOrderData() {
        return awatingOrderData;
    }

    public void setAwatingOrderData(List<AwatingOrderDatum> awatingOrderData) {
        this.awatingOrderData = awatingOrderData;
    }

}
