package ru.kpfu.itis.asadullin.model.dto;

import ru.kpfu.itis.asadullin.model.entity.Comment;

import java.sql.Timestamp;

public class CommentDto {
    private String text;

    private Timestamp sendingTime;

    private int likes;
    private int commentId;
    private String username;
    private int userId;
    private String profilePictureUrl;
    private int articleId;

    private boolean isLiked;

    public CommentDto(String text, Timestamp sendingTime, int likes, int commentId, int userId, String username, String profilePictureUrl, int articleId, boolean isLiked) {
        this.text = text;
        this.sendingTime = sendingTime;
        this.likes = likes;
        this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
        this.articleId = articleId;
        this.isLiked = isLiked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Timestamp sendingTime) {
        this.sendingTime = sendingTime;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
