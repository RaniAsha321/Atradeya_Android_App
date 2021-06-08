
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectDatum {

    @SerializedName("project_name")
    @Expose
    private String projectName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("Delivery_Address")
    @Expose
    private String deliveryAddress;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("email_address")
    @Expose
    private String emailAddress;
    @SerializedName("job_status")
    @Expose
    private String jobStatus;
    @SerializedName("AllotedEngineers")
    @Expose
    private String allotedEngineers;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getAllotedEngineers() {
        return allotedEngineers;
    }

    public void setAllotedEngineers(String allotedEngineers) {
        this.allotedEngineers = allotedEngineers;
    }

}
