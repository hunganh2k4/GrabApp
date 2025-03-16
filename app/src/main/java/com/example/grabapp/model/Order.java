package com.example.grabapp.model;

import java.util.List;

public class Order {
    private String id;
    private String userId; // ID của người dùng đặt hàng
    private double totalPrice;
    private String date;
    private List<OrderItem> items; // Danh sách sản phẩm trong đơn hàng

    public Order() {
        // Firebase yêu cầu constructor mặc định
    }

    public Order(String  id, double totalPrice, String date,String userId, List<OrderItem> items) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.date = date;
        this.items = items;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
