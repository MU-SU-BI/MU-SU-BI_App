package com.example.musubi.model.dto;

public class PostDetailDto {
    private long postId;
    private String title;
    private String content;
    private String authorName;
    private String createAt;

    public PostDetailDto(long postId, String title, String content, String authorName, String createAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.createAt = createAt;
    }

    public long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getCreateAt() {
        return createAt;
    }
}
