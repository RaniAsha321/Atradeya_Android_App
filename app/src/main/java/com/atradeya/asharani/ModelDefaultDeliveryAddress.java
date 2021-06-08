
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDefaultDeliveryAddress {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("DeleveryAddress")
    @Expose
    private String deleveryAddress;
    @SerializedName("DeleveryCompanyName")
    @Expose
    private String deleveryCompanyName;
    @SerializedName("DeleveryCompanyAddress")
    @Expose
    private String deleveryCompanyAddress;
    @SerializedName("DeleveryCompanyCity")
    @Expose
    private String deleveryCompanyCity;
    @SerializedName("DeleveryCompanyPostcode")
    @Expose
    private String deleveryCompanyPostcode;
    @SerializedName("valid")
    @Expose
    private Boolean valid;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDeleveryAddress() {
        return deleveryAddress;
    }

    public void setDeleveryAddress(String deleveryAddress) {
        this.deleveryAddress = deleveryAddress;
    }

    public String getDeleveryCompanyName() {
        return deleveryCompanyName;
    }

    public void setDeleveryCompanyName(String deleveryCompanyName) {
        this.deleveryCompanyName = deleveryCompanyName;
    }

    public String getDeleveryCompanyAddress() {
        return deleveryCompanyAddress;
    }

    public void setDeleveryCompanyAddress(String deleveryCompanyAddress) {
        this.deleveryCompanyAddress = deleveryCompanyAddress;
    }

    public String getDeleveryCompanyCity() {
        return deleveryCompanyCity;
    }

    public void setDeleveryCompanyCity(String deleveryCompanyCity) {
        this.deleveryCompanyCity = deleveryCompanyCity;
    }

    public String getDeleveryCompanyPostcode() {
        return deleveryCompanyPostcode;
    }

    public void setDeleveryCompanyPostcode(String deleveryCompanyPostcode) {
        this.deleveryCompanyPostcode = deleveryCompanyPostcode;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

}
