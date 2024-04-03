package ru.kpfu.itis.asadullin.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "likes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private int likeId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "target_id")
    private int targetId;
    @Column(name = "is_article")
    boolean isArticle;

    public Like(int userId, int targetId, boolean isArticle) {
        this.userId = userId;
        this.targetId = targetId;
        this.isArticle = isArticle;
    }
}