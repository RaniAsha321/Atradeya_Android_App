
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variation {

    @SerializedName("variation_id")
    @Expose
    private String variationId;
    @SerializedName("option_id")
    @Expose
    private String optionId;
    @SerializedName("option_name")
    @Expose
    private String optionName;
    @SerializedName("plaseholder")
    @Expose
    private String plaseholder;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("inc_price")
    @Expose
    private String incPrice;


    public String getIncPrice() {
        return incPrice;
    }

    public void setIncPrice(String incPrice) {
        this.incPrice = incPrice;
    }
    public String getVariationId() {
        return variationId;
    }

    public void setVariationId(String variationId) {
        this.variationId = variationId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getPlaseholder() {
        return plaseholder;
    }

    public void setPlaseholder(String plaseholder) {
        this.plaseholder = plaseholder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
