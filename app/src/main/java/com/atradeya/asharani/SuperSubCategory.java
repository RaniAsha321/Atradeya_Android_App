
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuperSubCategory {



    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("super_sub_cat-id")
    @Expose
    private String superSubCatId;
    @SerializedName("super_sub_cat-name")
    @Expose
    private String superSubCatName;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuperSubCatId() {
        return superSubCatId;
    }

    public void setSuperSubCatId(String superSubCatId) {
        this.superSubCatId = superSubCatId;
    }

    public String getSuperSubCatName() {
        return superSubCatName;
    }

    public void setSuperSubCatName(String superSubCatName) {
        this.superSubCatName = superSubCatName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
