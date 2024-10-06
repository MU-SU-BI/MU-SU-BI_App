package com.example.musubi.model.dto;

import java.io.Serializable;

public class PostDto implements Serializable {  // Serializable 인터페이스 구현
    private static final long serialVersionUID = 1L;  // 직렬화 ID

    final private long postId;
    final private String title;
    final private String authorName; // 글쓴이
    private String createAt; // 생성날짜
    final private int commentsCount;

    // 생성자와 기타 필드들 (필요에 따라 추가)

    public PostDto(long postId, String title, String author, String creationDate, int commentsCount) {
        this.postId = postId;
        this.title = title;
        this.authorName = author;
        this.createAt = creationDate;
        this.commentsCount = commentsCount;
    }

    public long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getCreateAt() {
        return createAt;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCreateAt( String createAt)
    {
        this.createAt = createAt;
    }
}
