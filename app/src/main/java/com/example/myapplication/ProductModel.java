package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductModel implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;
    @SerializedName("category")
    private String category;
    @SerializedName("image")
    private String image;

    @SerializedName("price")
    private Double price;

//    String title, description, category, img;

//    Double price;


    public ProductModel() {
    }

    public ProductModel(int id, String title, String description, String category, String image, Double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
