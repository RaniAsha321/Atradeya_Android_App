package com.atradeya.asharani;

import java.util.List;

public class Model_Home_Categories  {

    String product_text;
    String product_img;
    String cat_id;
    List<Object> myfilterlist;

    Integer status;

    public Model_Home_Categories() {

    }

    public List<Object> getMyfilterlist() {
        return myfilterlist;
    }

    public void setMyfilterlist(List<Object> myfilterlist) {
        this.myfilterlist = myfilterlist;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProduct_text() {
        return product_text;
    }

    public void setProduct_text(String product_text) {
        this.product_text = product_text;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

}
