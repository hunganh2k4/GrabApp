package com.example.grabapp.model;

public class HotSpot {
    private String name;
    private int discount;
    private int imageResId;

    public HotSpot(String name, int discount, int imageResId) {
        this.name = name;
        this.discount = discount;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public int getDiscount() { return discount; }
    public int getImageResId() { return imageResId; }
}

