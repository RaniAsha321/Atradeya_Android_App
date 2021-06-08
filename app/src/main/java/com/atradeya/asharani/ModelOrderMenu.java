
package com.atradeya.asharani;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOrderMenu {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("OrderData")
    @Expose
    private List<OrderDatum> orderData = null;
    @SerializedName("Number_of_awting")
    @Expose
    private Integer numberOfAwting;

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

    public List<OrderDatum> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderDatum> orderData) {
        this.orderData = orderData;
    }

    public Integer getNumberOfAwting() {
        return numberOfAwting;
    }

    public void setNumberOfAwting(Integer numberOfAwting) {
        this.numberOfAwting = numberOfAwting;
    }

}
