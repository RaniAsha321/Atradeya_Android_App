package com.atradeya.asharani;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("business")
    @Expose
    private String business;
    @SerializedName("phn_numer")
    @Expose
    private String phnNumer;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("permission_full")
    @Expose
    private String permissionFull;
    @SerializedName("permission_products")
    @Expose
    private String permissionProducts;
    @SerializedName("permission_orders")
    @Expose
    private String permissionOrders;
    @SerializedName("permission_quotes")
    @Expose
    private String permissionQuotes;
    @SerializedName("permission_suppliers")
    @Expose
    private String permissionSuppliers;
    @SerializedName("permission_engineers")
    @Expose
    private String permissionEngineers;
    @SerializedName("permission_projects")
    @Expose
    private String permissionProjects;
    @SerializedName("permission_catologes")
    @Expose
    private String permissionCatologes;
    @SerializedName("permission_categories")
    @Expose
    private String permissionCategories;
    @SerializedName("permission_notifications")
    @Expose
    private String permissionNotifications;
    @SerializedName("permission_super_Admin")
    @Expose
    private String permissionSuperAdmin;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getPhnNumer() {
        return phnNumer;
    }

    public void setPhnNumer(String phnNumer) {
        this.phnNumer = phnNumer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermissionFull() {
        return permissionFull;
    }

    public void setPermissionFull(String permissionFull) {
        this.permissionFull = permissionFull;
    }

    public String getPermissionProducts() {
        return permissionProducts;
    }

    public void setPermissionProducts(String permissionProducts) {
        this.permissionProducts = permissionProducts;
    }

    public String getPermissionOrders() {
        return permissionOrders;
    }

    public void setPermissionOrders(String permissionOrders) {
        this.permissionOrders = permissionOrders;
    }

    public String getPermissionQuotes() {
        return permissionQuotes;
    }

    public void setPermissionQuotes(String permissionQuotes) {
        this.permissionQuotes = permissionQuotes;
    }

    public String getPermissionSuppliers() {
        return permissionSuppliers;
    }

    public void setPermissionSuppliers(String permissionSuppliers) {
        this.permissionSuppliers = permissionSuppliers;
    }

    public String getPermissionEngineers() {
        return permissionEngineers;
    }

    public void setPermissionEngineers(String permissionEngineers) {
        this.permissionEngineers = permissionEngineers;
    }

    public String getPermissionProjects() {
        return permissionProjects;
    }

    public void setPermissionProjects(String permissionProjects) {
        this.permissionProjects = permissionProjects;
    }

    public String getPermissionCatologes() {
        return permissionCatologes;
    }

    public void setPermissionCatologes(String permissionCatologes) {
        this.permissionCatologes = permissionCatologes;
    }

    public String getPermissionCategories() {
        return permissionCategories;
    }

    public void setPermissionCategories(String permissionCategories) {
        this.permissionCategories = permissionCategories;
    }

    public String getPermissionNotifications() {
        return permissionNotifications;
    }

    public void setPermissionNotifications(String permissionNotifications) {
        this.permissionNotifications = permissionNotifications;
    }

    public String getPermissionSuperAdmin() {
        return permissionSuperAdmin;
    }

    public void setPermissionSuperAdmin(String permissionSuperAdmin) {
        this.permissionSuperAdmin = permissionSuperAdmin;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

}

