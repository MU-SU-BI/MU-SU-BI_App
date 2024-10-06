package com.example.musubi.model.dto;

public class SafeAreaDto {
    private final long userId;
    private final double longitude;
    private final double latitude;
    private final double radius;

    public SafeAreaDto(long userId, double longitude, double latitude, double radius) {
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
