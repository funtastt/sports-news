package ru.kpfu.itis.asadullin.model.entity;

import java.sql.Timestamp;

public class Article {
    private int articleId;
    private String title;
    private String content;
    private String summary;
    private int authorId;
    private Timestamp publishTime;
    private String category;
    private String imageUrl;
    private int views;
    private int likes;

    public Article(int articleId, String title, String content, String summary, int authorId, Timestamp publishTime, String category, String imageUrl, int views, int likes) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.authorId = authorId;
        this.publishTime = publishTime;
        this.category = category;
        this.imageUrl = imageUrl;
        this.views = views;
        this.likes = likes;
    }

    public Article(String title, String content, String summary, int authorId, Timestamp publishTime, String category, String imageUrl) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.authorId = authorId;
        this.publishTime = publishTime;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public int getArticleId() {
        return articleId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
