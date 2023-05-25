package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class CartModel {
    @SerializedName("id")
    int id;
    @SerializedName("userId")
    int user_id;
    @SerializedName("Date")
    Date date;

    public static class Product {
        @SerializedName("productId")
        int productId;

        @SerializedName("quantity")
        int quantity;

        public Product(int productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    @SerializedName("products")
    List<ProductModel> products;

    public CartModel(int id, int user_id, Date date, List<ProductModel> products) {
        this.id = id;
        this.user_id = user_id;
        this.date = date;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}
