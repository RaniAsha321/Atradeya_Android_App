
package com.atradeya.asharani;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveCard {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("cardnumber")
    @Expose
    private String cardnumber;
    @SerializedName("exp_date")
    @Expose
    private String expDate;
    @SerializedName("exp_year")
    @Expose
    private String expYear;
    @SerializedName("card_type")
    @Expose
    private String cardType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

}
