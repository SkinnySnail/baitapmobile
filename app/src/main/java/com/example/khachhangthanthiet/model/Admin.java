package com.example.khachhangthanthiet.model;

public class Admin extends Customer{
    private String password;

    // Constructor mặc định
    public Admin() {
        super(); // gọi constructor mặc định của Customer
    }

    // Constructor đầy đủ
    public Admin(String phoneNumber, int points, String createdDate, String lastUpdatedDate, String password) {
        super(phoneNumber, points, createdDate, lastUpdatedDate); // gọi constructor của lớp cha
        this.password = password;
    }

    // Getter & Setter cho password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
