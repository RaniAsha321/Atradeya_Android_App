package com.atradeya.asharani;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSupplier {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("SupplierData")
    @Expose
    private List<SupplierDatum> supplierData = null;

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

    public List<SupplierDatum> getSupplierData() {
        return supplierData;
    }

    public void setSupplierData(List<SupplierDatum> supplierData) {
        this.supplierData = supplierData;
    }

}

