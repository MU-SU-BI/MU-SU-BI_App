package com.example.musubi.model.dto;

public class WritePostDto {
    private String title;
    private String content;
    private long userId;

    public WritePostDto(String title, String content, long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getUserId() {
        return userId;
    }
}
