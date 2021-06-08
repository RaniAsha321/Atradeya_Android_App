
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddress {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("enginnerid")
    @Expose
    private String enginnerid;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("address_one")
    @Expose
    private String addressOne;
    @SerializedName("address_two")
    @Expose
    private String addressTwo;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("postcode")
    @Expose
    private String postcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEnginnerid() {
        return enginnerid;
    }

    public void setEnginnerid(String enginnerid) {
        this.enginnerid = enginnerid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
