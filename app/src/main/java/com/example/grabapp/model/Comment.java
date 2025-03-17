package com.example.grabapp.model;

public class Comment {
    private String id;         // Thêm thuộc tính id
    private String productId;  // ID của sản phẩm được bình luận
    private String userName;
    private String content;
    private float rating;      // Đánh giá sao

    public Comment() {
        // Constructor mặc định cần thiết cho Firestore
    }

    public Comment(String id, String productId, String userName, String content, float rating) {
        this.id = id;
        this.productId = productId;
        this.userName = userName;
        this.content = content;
        this.rating = rating;
    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}