package com.example.grabapp.model;

public class Comment {
    private String productName;
    private String userName;
    private String content;

    public Comment(String productName, String userName, String content) {
        this.productName = productName;
        this.userName = userName;
        this.content = content;
    }

    public String getProductName() {
        return productName;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }
}