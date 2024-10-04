package com.example.musubi.model.dto;
public class SafeAreaRequestDto {
    private long userId;
    private double longitude;
    private double latitude;
    private double radius;

    public SafeAreaRequestDto(long userId, double longitude, double latitude, double radius) {
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }

    public long getUserId() {
        return userId;
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
