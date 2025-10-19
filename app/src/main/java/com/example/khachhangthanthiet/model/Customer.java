package com.example.khachhangthanthiet.model;
import java.util.Date;

public class Customer {
    private String phoneNumber;
    private int points;
    private String createdDate;
    private String lastUpdatedDate;

    public Customer() {
    }

    public Customer(String phoneNumber, int points, String createdDate, String lastUpdatedDate) {
        this.phoneNumber = phoneNumber;
        this.points = points;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
