package com.example.grabapp.model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String email;
    private String password;
    private String username;
    private String avatar;

    public User() {
        // Constructor mặc định để Firebase có thể đọc dữ liệu
    }

    public User(String id, String email, String password, String username, String avatar) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
