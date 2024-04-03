package ru.kpfu.itis.asadullin.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    private String text;

    @Column(name = "sending_time")
    private Timestamp sendingTime;

    private int likes;
    @Column(name = "author_id")
    private int authorId;
    @Column(name = "article_id")
    private int articleId;

    public Comment(String text, Timestamp sendingTime, int authorId, int articleId) {
        this.text = text;
        this.sendingTime = sendingTime;
        this.authorId = authorId;
        this.articleId = articleId;
    }
}
