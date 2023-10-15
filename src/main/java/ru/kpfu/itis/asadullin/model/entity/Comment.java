package ru.kpfu.itis.asadullin.model.entity;

import java.sql.Timestamp;

public class Comment {
    private int commentId;

    private String text;

    private Timestamp sendingTime;

    private int likes;

    private int authorId;

    private int articleId;

    public Comment(int commentId, String text, Timestamp sendingTime, int likes, int authorId, int articleId) {
        this.commentId = commentId;
        this.text = text;
        this.sendingTime = sendingTime;
        this.likes = likes;
        this.authorId = authorId;
        this.articleId = articleId;
    }

    public Comment(String text, Timestamp sendingTime, int authorId, int articleId) {
        this.text = text;
        this.sendingTime = sendingTime;
        this.authorId = authorId;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
