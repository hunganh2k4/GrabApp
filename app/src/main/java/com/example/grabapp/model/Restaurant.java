package com.example.grabapp.model;

import java.io.Serializable;
import java.util.List;

public class Restaurant implements Serializable {
    private String id;  // Thêm ID
    private String name;
    private int imageResId;
    private float rating;
    private int soldQuantity;
    private List<Product> productList;

    public Restaurant() {
        // Constructor mặc định để Firebase có thể đọc dữ liệu
    }

    public Restaurant(String id, String name, int imageResId, float rating, int soldQuantity, List<Product> productList) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.rating = rating;
        this.soldQuantity = soldQuantity;
        this.productList = productList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public float getRating() {
        return rating;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
