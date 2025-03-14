package com.example.grabapp.model;

public class Food {
    private String name;
    private int price;
    private int imageResource;

    public Food() {
    }

    public Food(String name, int price, int imageResource) {
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }
}
