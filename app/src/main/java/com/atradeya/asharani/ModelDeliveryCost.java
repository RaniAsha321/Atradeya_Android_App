
package com.atradeya.asharani;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDeliveryCost {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Delevery_Cost")
    @Expose
    private List<DeleveryCost> deleveryCost = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<DeleveryCost> getDeleveryCost() {
        return deleveryCost;
    }

    public void setDeleveryCost(List<DeleveryCost> deleveryCost) {
        this.deleveryCost = deleveryCost;
    }

}
