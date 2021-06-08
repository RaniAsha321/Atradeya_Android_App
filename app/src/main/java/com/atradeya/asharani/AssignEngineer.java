
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignEngineer {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("Oprativedata")
    @Expose
    private List<Oprativedatum> oprativedata = null;

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

    public List<Oprativedatum> getOprativedata() {
        return oprativedata;
    }

    public void setOprativedata(List<Oprativedatum> oprativedata) {
        this.oprativedata = oprativedata;
    }

}
