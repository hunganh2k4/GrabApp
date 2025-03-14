package com.example.grabapp.model;

public class OrderItem {
    private String productName;
    private int quantity;
    private int productImage;

    public OrderItem(String productName, int quantity, int productImage) {
        this.productName = productName;
        this.quantity = quantity;
        this.productImage = productImage;
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

