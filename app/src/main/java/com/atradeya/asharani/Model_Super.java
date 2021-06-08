package com.atradeya.asharani;

public class Model_Super {

    String SuperImage;
    String Supertext;
    String subcat;

    Model_Super(String text, String Image, String subcat){

        this.Supertext=text;
        this.SuperImage=Image;
        this.subcat=subcat;
    }

    public Model_Super() {

    }

    public String getSuperImage() {
        return SuperImage;
    }

    public void setSuperImage(String superImage) {
        SuperImage = superImage;
    }

    public String getSupertext() {
        return Supertext;
    }

    public void setSupertext(String supertext) {
        Supertext = supertext;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }



}
