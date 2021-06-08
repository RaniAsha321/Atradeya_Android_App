package com.atradeya.asharani;

public class Model_Product_Description {

    String product_id;
    String name;
    String image;
    String price;
    String Description;
    String Reviews;
    String specification;

    Model_Product_Description(String product_id, String name, String image, String price, String Description, String Reviews, String specification){

        this.product_id=product_id;
        this.name=name;
        this.image=image;
        this.price=price;
        this.Description=Description;
        this.Reviews=Reviews;
        this.specification=specification;

    }

    public Model_Product_Description() {

    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getReviews() {
        return Reviews;
    }

    public void setReviews(String reviews) {
        Reviews = reviews;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }



}
