package ru.kpfu.itis.asadullin.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "articles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private int articleId;
    private String title;
    private String content;
    private String summary;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "publish_time")
    private Timestamp publishTime;
    private String category;
    @Column(name = "image_url")
    private String imageUrl;
    private int views;
    private int likes;

    public Article(String title, String content, String summary, User author, Timestamp publishTime, String category, String imageUrl) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.author = author;
        this.publishTime = publishTime;
        this.category = category;
        this.imageUrl = imageUrl;
        this.likes = 0;
        this.views = 0;
    }
}
