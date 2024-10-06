package com.example.musubi.model.dto;

public class CommentDto {
    private String authorName;
    private String content;
    private String createAt;
    private long commendId;

    public CommentDto(String authorName, String content, String createAt, long commendId) {
        this.authorName = authorName;
        this.content = content;
        this.createAt = createAt;
        this.commendId = commendId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public long getCommendId() {
        return commendId;
    }
}
