
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleveryCost {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bussiness")
    @Expose
    private String bussiness;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cost")
    @Expose
    private String cost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBussiness() {
        return bussiness;
    }

    public void setBussiness(String bussiness) {
        this.bussiness = bussiness;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

}
