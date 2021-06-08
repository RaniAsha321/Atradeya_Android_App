
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelProductsSubCategory {


    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Sub_Category")
    @Expose
    private List<SubCategory> subCategory = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

}
