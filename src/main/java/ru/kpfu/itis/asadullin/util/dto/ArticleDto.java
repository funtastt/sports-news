package ru.kpfu.itis.asadullin.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@Data
@AllArgsConstructor
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

}
