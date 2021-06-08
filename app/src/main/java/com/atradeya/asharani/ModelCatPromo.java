
package com.atradeya.asharani;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCatPromo {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("catalogdata")
    @Expose
    private List<Catalogdatum> catalogdata = null;

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

    public List<Catalogdatum> getCatalogdata() {
        return catalogdata;
    }

    public void setCatalogdata(List<Catalogdatum> catalogdata) {
        this.catalogdata = catalogdata;
    }

}
