
package com.atradeya.asharani;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("cat-id")
    @Expose
    private String catId;
    @SerializedName("cat-name")
    @Expose
    private String catName;
    @SerializedName("cat-image")
    @Expose
    private String catImage;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("fillter")
    @Expose
    private List<Object> fillter = null;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Object> getFillter() {
        return fillter;
    }

    public void setFillter(List<Object> fillter) {
        this.fillter = fillter;
    }

}
