
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("sub_cat-id")
    @Expose
    private String subCatId;
    @SerializedName("sub_cat-name")
    @Expose
    private String subCatName;
    @SerializedName("cat-image")
    @Expose
    private String catImage;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

}
