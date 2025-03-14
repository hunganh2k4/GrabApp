package com.example.grabapp.model;

import java.util.List;

public class Order {
    private int id;
    private int totalPrice;
    private String date;
    private List<OrderItem> orderItems;

    public Order(int id, int totalPrice, String date, List<OrderItem> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.date = date;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
