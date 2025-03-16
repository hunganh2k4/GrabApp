package com.example.grabapp.model;

public class OrderItem {

    private String id;
    private String productName;
    private int quantity;
    private int productImage;

    public OrderItem(String id, int productImage, int quantity, String productName) {
        this.id = id;
        this.productImage = productImage;
        this.quantity = quantity;
        this.productName = productName;
    }

    public OrderItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProductImage() {
        return productImage;
    }
}

