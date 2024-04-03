package ru.kpfu.itis.asadullin.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.asadullin.model.entity.Article;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Article getById(int id);

    Article getByTitle(String title);

    @Query(value = "SELECT * FROM articles", nativeQuery = true)
    List<Article> getAll();

    @Modifying
    @Query(value = "INSERT INTO articles " +
            "(title, content, author_id, publish_time, category, image_url, views, likes, summary) " +
            "VALUES (:title, :content, :authorId, :publishTime, :category, :imageUrl, :views, :likes, :summary)", nativeQuery = true)
    void insert(@Param("title") String title, @Param("content") String content, @Param("authorId") int authorId,
                @Param("publishTime") Timestamp publishTime, @Param("category") String category,
                @Param("imageUrl") String imageUrl, @Param("views") int views, @Param("likes") int likes,
                @Param("summary") String summary);

    @Modifying
    @Query(value = "UPDATE articles SET title = :title, content = :content, author_id = :authorId, " +
            "publish_time = :publishTime, category = :category, image_url = :imageUrl, views = :views, " +
            "likes = :likes, summary = :summary WHERE article_id = :id", nativeQuery = true)
    void update(@Param("id") int id, @Param("title") String title, @Param("content") String content,
                @Param("authorId") int authorId, @Param("publishTime") Timestamp publishTime,
                @Param("category") String category, @Param("imageUrl") String imageUrl,
                @Param("views") int views, @Param("likes") int likes, @Param("summary") String summary);

    @Modifying
    @Query(value = "DELETE FROM articles WHERE article_id = :id", nativeQuery = true)
    void delete(@Param("id") int id);

    @Query(value = "SELECT article_id FROM articles WHERE content = :content", nativeQuery = true)
    int findArticleId(@Param("content") String content);
}
