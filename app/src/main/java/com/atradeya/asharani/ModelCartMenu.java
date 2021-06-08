package com.atradeya.asharani;

public class ModelCartMenu {


    public String usersid;
    public String businessid;
    public String productimage;
    public String productname;
    public String productid;
    public String quantity;
    public String price;
    public String inc_vat;
    public String variationid;
    public String variation_name;
    public int id;
    public String add_product_code;

    public static final String TABLE_NAME = "cart_table";

    public static final String COLUMN_VARIATION_ID="variationid";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "usersid";
    public static final String COLUMN_PRODCUT_IMAGE = "productimage";
    public static final String COLUMN_PRODUCT_NAME = "productname";
    public static final String COLUMN_VARIATION_NAME = "variationname";
    public static final String COLUMN_BUSINESS_ID = "businessid";
    public static final String COLUMN_PRODCUT_ID = "productid";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_INC_VAT = "inc_vat";
    public static final String COLUMN_PRO_CODE="pro_code";



    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +COLUMN_USER_ID + " TEXT,"
                    + COLUMN_PRODCUT_IMAGE + " TEXT,"
                    + COLUMN_PRODUCT_NAME + " TEXT,"
                    + COLUMN_VARIATION_NAME + " TEXT,"
                    + COLUMN_PRODCUT_ID + " TEXT,"
                    + COLUMN_QUANTITY + " TEXT,"
                    + COLUMN_VARIATION_ID + " TEXT,"
                    + COLUMN_INC_VAT + " TEXT,"
                    + COLUMN_PRO_CODE + " TEXT,"
                    + COLUMN_PRICE + " TEXT,"
                    + COLUMN_BUSINESS_ID + " TEXT"
                    + ")";

    public ModelCartMenu() {
    }

    public String getBusinessid() {
        return businessid;
    }

    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }

    public String getAdd_product_code() {
        return add_product_code;
    }

    public void setAdd_product_code(String add_product_code) {
        this.add_product_code = add_product_code;
    }
    public String getVariation_name() {
        return variation_name;
    }

    public void setVariation_name(String variation_name) {
        this.variation_name = variation_name;
    }

    public String getVariationid() {
        return variationid;
    }

    public void setVariationid(String variationid) {
        this.variationid = variationid;
    }

    public String getUsersid() {
        return usersid;
    }

    public void setUsersid(String usersid) {
        this.usersid = usersid;
    }

    public String getInc_vat() {
        return inc_vat;
    }

    public void setInc_vat(String inc_vat) {
        this.inc_vat = inc_vat;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

