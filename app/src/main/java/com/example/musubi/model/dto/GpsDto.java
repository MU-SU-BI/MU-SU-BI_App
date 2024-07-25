package com.example.musubi.model.dto;

public class GpsDto {
    private final long userId;
    private final String coordinate;

    public GpsDto(String coordinate, long userId)
    {
        this.coordinate = coordinate;
        this.userId = userId;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public long getUserId() {
        return userId;
    }
}
