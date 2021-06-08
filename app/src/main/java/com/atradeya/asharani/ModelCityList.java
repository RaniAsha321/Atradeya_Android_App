
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelCityList {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("CityList")
    @Expose
    private List<CityList> cityList = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<CityList> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityList> cityList) {
        this.cityList = cityList;
    }

}
