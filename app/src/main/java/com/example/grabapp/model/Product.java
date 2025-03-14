package com.example.grabapp.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String id; // ID sản phẩm
    private String name;
    private int price;
    private int imageResId;
    private String description;
    private float rating; // Điểm đánh giá trung bình
    private int soldQuantity; // Số lượng đã bán

    public Product() {
        // Constructor mặc định để Firebase có thể đọc dữ liệu
    }

    public Product(String id, String name, int price, int imageResId, String description, float rating, int soldQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.description = description;
        this.rating = rating;
        this.soldQuantity = soldQuantity;
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

    public int getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
}
