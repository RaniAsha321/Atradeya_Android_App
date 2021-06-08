package com.atradeya.asharani;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleOrderDatum {

    @SerializedName("variation_option_name")
    @Expose
    private String variationOptionName;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("linenumber")
    @Expose
    private String linenumber;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("ex_vat")
    @Expose
    private String exVat;
    @SerializedName("inc_vat")
    @Expose
    private String incVat;


    public String getVariationOptionName() {
        return variationOptionName;
    }

    public void setVariationOptionName(String variationOptionName) {
        this.variationOptionName = variationOptionName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLinenumber() {
        return linenumber;
    }

    public void setLinenumber(String linenumber) {
        this.linenumber = linenumber;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getExVat() {
        return exVat;
    }

    public void setExVat(String exVat) {
        this.exVat = exVat;
    }

    public String getIncVat() {
        return incVat;
    }

    public void setIncVat(String incVat) {
        this.incVat = incVat;
    }

}

