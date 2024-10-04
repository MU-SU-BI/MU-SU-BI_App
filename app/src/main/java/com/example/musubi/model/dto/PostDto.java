package com.example.musubi.model.dto;

public class PostDto {
    private String title;
    private String content;
    private int commentCount;
    private String imageUrl;

    public PostDto(String title, String content, int commentCount, String imageUrl) {
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean hasImage() {
        return imageUrl != null && !imageUrl.isEmpty();
    }
}
