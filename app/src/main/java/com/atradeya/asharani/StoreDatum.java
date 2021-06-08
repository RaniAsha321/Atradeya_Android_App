
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bussiness")
    @Expose
    private String bussiness;
    @SerializedName("Store_Name")
    @Expose
    private String storeName;
    @SerializedName("Store_Address_one")
    @Expose
    private String storeAddressOne;
    @SerializedName("Store_Address_two")
    @Expose
    private String storeAddressTwo;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Post_Code")
    @Expose
    private String postCode;
    @SerializedName("Contact_Number")
    @Expose
    private String contactNumber;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("defaultcheck")
    @Expose
    private String defaultcheck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBussiness() {
        return bussiness;
    }

    public void setBussiness(String bussiness) {
        this.bussiness = bussiness;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddressOne() {
        return storeAddressOne;
    }

    public void setStoreAddressOne(String storeAddressOne) {
        this.storeAddressOne = storeAddressOne;
    }

    public String getStoreAddressTwo() {
        return storeAddressTwo;
    }

    public void setStoreAddressTwo(String storeAddressTwo) {
        this.storeAddressTwo = storeAddressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDefaultcheck() {
        return defaultcheck;
    }

    public void setDefaultcheck(String defaultcheck) {
        this.defaultcheck = defaultcheck;
    }

}
