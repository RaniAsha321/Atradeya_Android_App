
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelDescription {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("publishkey")
    @Expose
    private String publishkey;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getPublishkey() {
        return publishkey;
    }

    public void setPublishkey(String publishkey) {
        this.publishkey = publishkey;
    }

}
