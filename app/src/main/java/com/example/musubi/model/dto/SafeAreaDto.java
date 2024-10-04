package com.example.musubi.model.dto;

public class SafeAreaDto {
    private long userID;
    private final double longitude;
    private final double latitude;
    private final double radius;

    public SafeAreaDto(double longitude, double latitude, double radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }

    public long getUserId() {
        return userID;
    }

    public void setUserID(long userId)
    {
        this.userID = userId;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getRadius() {
        return radius;
    }
}
