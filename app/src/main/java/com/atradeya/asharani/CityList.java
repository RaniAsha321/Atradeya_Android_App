
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityList {

    @SerializedName("town")
    @Expose
    private String town;

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

}
