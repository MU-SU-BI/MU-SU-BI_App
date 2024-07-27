package com.example.musubi.model.dto;

public class UserConnectDto {
    private final long userId;
    private final String disabledName;
    private final String disabledPhoneNumber;

    public UserConnectDto(long userId, String disabledName, String disabledPhoneNumber) {
        this.userId = userId;
        this.disabledName = disabledName;
        this.disabledPhoneNumber = disabledPhoneNumber;
    }

    public long getUserId() {
        return userId;
    }

    public String getDisabledName() {
        return disabledName;
    }

    public String getDisabledPhoneNumber() {
        return disabledPhoneNumber;
    }
}
