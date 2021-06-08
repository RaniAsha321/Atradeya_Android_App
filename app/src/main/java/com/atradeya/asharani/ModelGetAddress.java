
package com.atradeya.asharani;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelGetAddress {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("GetAddress")
    @Expose
    private List<GetAddress> address = null;
    @SerializedName("valid")
    @Expose
    private Boolean valid;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetAddress> getAddress() {
        return address;
    }

    public void setAddress(List<GetAddress> address) {
        this.address = address;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

}
