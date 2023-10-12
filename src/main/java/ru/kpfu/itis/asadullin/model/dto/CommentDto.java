package ru.kpfu.itis.asadullin.model.dto;

import java.sql.Timestamp;

public class CommentDto {
    private String text;

    private Timestamp sendingTime;

    private int likes;

    private String username;
    private String profilePictureUrl;
    private int articleId;

    public CommentDto(String text, Timestamp sendingTime, int likes, String username, String profilePictureUrl, int articleId) {
        this.text = text;
        this.sendingTime = sendingTime;
        this.likes = likes;
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
        this.articleId = articleId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
