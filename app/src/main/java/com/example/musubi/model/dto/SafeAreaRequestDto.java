package com.example.musubi.model.dto;

public class SafeAreaRequestDto {
    private final long userId;
    private final SafeAreaDto safeArea;

    public SafeAreaRequestDto(long userId, SafeAreaDto safeArea) {
        this.userId = userId;
        this.safeArea = safeArea;
    }

    public long getUserId() {
        return userId;
    }

    public SafeAreaDto getSafeArea() {
        return safeArea;
    }
}
