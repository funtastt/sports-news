package ru.kpfu.itis.asadullin.model.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favourite_id")
    private int favouriteId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "article_id")
    private int articleId;

    public Favourite(int userId, int articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }
}
