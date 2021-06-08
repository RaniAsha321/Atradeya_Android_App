
package com.atradeya.asharani;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCollectionStore {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Store_data")
    @Expose
    private List<StoreDatum> storeData = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<StoreDatum> getStoreData() {
        return storeData;
    }

    public void setStoreData(List<StoreDatum> storeData) {
        this.storeData = storeData;
    }

}
