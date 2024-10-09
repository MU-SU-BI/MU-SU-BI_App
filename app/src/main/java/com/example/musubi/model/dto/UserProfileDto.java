package com.example.musubi.model.dto;

public class UserProfileDto {
    private final String profileImageUrl;

    public UserProfileDto(String profile) {
        this.profileImageUrl = profile;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
