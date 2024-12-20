package ru.kpfu.itis.asadullin.controller.util.dto;

import java.sql.Timestamp;

public class ArticleDto {
    private String title;
    private int articleId;
    private String content;
    private String summary;
    private String author;

    private int authorId;
    private String authorProfilePicture;
    private Timestamp publishTime;
    private String category;
    private String imageUrl;
    private int views;
    private int likes;

    public ArticleDto(String title, String content, int articleId, String summary, int authorId, String author, String authorProfilePicture, Timestamp publishTime, String category, String imageUrl, int views, int likes) {
        this.title = title;
        this.content = content;
        this.articleId = articleId;
        this.summary = summary;
        this.authorId = authorId;
        this.author = author;
        this.authorProfilePicture = authorProfilePicture;
        this.publishTime = publishTime;
        this.category = category;
        this.imageUrl = imageUrl;
        this.views = views;
        this.likes = likes;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public String getAuthorProfilePicture() {
        return authorProfilePicture;
    }

    public void setAuthorProfilePicture(String authorProfilePicture) {
        this.authorProfilePicture = authorProfilePicture;
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
