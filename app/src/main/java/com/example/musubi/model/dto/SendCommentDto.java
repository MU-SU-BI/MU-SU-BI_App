package com.example.musubi.model.dto;

public class SendCommentDto {
    private long userId;
    private String content;

    public SendCommentDto(long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
