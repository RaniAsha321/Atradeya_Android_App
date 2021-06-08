
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSearching {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("SearchData")
    @Expose
    private List<SearchDatum> searchData = null;
    @SerializedName("publishkey")
    @Expose
    private String publishkey;

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

    public List<SearchDatum> getSearchData() {
        return searchData;
    }

    public void setSearchData(List<SearchDatum> searchData) {
        this.searchData = searchData;
    }

    public String getPublishkey() {
        return publishkey;
    }

    public void setPublishkey(String publishkey) {
        this.publishkey = publishkey;
    }

}
