package com.atradeya.asharani;

public class Model_Card_Details_Saved {


    int id;
    String Card;
    String exp_month;
    String exp_year;
    String cvv;
    String card_type;
    String last4digit;

    public static final String TABLE_NAME = "saved_card_table1";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CARD = "card";
    public static final String COLUMN_EXP_MONTH = "exp_month";
    public static final String COLUMN_EXP_YEAR = "exp_year";
    public static final String COLUMN_CVV = "cvv";
    public static final String COLUMN_CARD_TYPE = "card_type";
    public static final String COLUMN_LAST4DIGIT = "last4digit";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +COLUMN_CARD + " TEXT,"
                    + COLUMN_EXP_MONTH + " TEXT,"
                    + COLUMN_EXP_YEAR + " TEXT,"
                    + COLUMN_CVV + " TEXT,"
                    + COLUMN_CARD_TYPE + " TEXT,"
                    + COLUMN_LAST4DIGIT + " TEXT"
                    + ")";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getLast4digit() {
        return last4digit;
    }

    public void setLast4digit(String last4digit) {
        this.last4digit = last4digit;
    }

    public String getCard() {
        return Card;
    }

    public void setCard(String card) {
        Card = card;
    }

    public String getExp_month() {
        return exp_month;
    }

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

}
