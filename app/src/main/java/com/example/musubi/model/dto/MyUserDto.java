package com.example.musubi.model.dto;

public class MyUserDto {
    private final double longitude;
    private final double latitude;

    public MyUserDto(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
}
