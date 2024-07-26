package com.example.musubi.model.dto;

public class CallDto {
    private final long userId;
    private final String callMessage;

    public CallDto(long userId, String callMessage) {
        this.userId = userId;
        this.callMessage = callMessage;
    }

    public long getUserId() {
        return userId;
    }

    public String getCallMessage() {
        return callMessage;
    }
}
