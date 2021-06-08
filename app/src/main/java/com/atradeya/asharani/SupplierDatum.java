package com.atradeya.asharani;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("suppliers_name")
    @Expose
    private String suppliersName;
    @SerializedName("contact_name")
    @Expose
    private String contactName;
    @SerializedName("suppliers_city")
    @Expose
    private String suppliersCity;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("\temail")
    @Expose
    private String email;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("order_email_address")
    @Expose
    private String orderEmailAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getSuppliersName() {
        return suppliersName;
    }

    public void setSuppliersName(String suppliersName) {
        this.suppliersName = suppliersName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getSuppliersCity() {
        return suppliersCity;
    }

    public void setSuppliersCity(String suppliersCity) {
        this.suppliersCity = suppliersCity;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOrderEmailAddress() {
        return orderEmailAddress;
    }

    public void setOrderEmailAddress(String orderEmailAddress) {
        this.orderEmailAddress = orderEmailAddress;
    }

}
