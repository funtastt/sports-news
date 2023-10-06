package ru.kpfu.itis.asadullin.model.entity;

import java.sql.Timestamp;

public class Comment {
    private int commentId;

    private String text;

    private Timestamp sendingTime;

    private int likes;

    private int userId;

    private int articleId;

    public Comment(int commentId, String text, Timestamp sendingTime, int likes, int userId, int articleId) {
        this.commentId = commentId;
        this.text = text;
        this.sendingTime = sendingTime;
        this.likes = likes;
        this.userId = userId;
        this.articleId = articleId;
    }

    public Comment(String text, Timestamp sendingTime, int userId, int articleId) {
        this.text = text;
        this.sendingTime = sendingTime;
        this.userId = userId;
        this.articleId = articleId;
    }

    public int getCommentId() {
        return commentId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
