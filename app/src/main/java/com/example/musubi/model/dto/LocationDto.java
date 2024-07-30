package com.example.musubi.model.dto;

public class LocationDto {
    private final long userId;
    private final double latitude;
    private final double longitude;

    public LocationDto(long userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getUserId() {
        return userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
