
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Catalogdatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("cover_image")
    @Expose
    private String coverImage;
    @SerializedName("catalog")
    @Expose
    private String catalog;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

}
