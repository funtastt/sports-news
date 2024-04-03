package ru.kpfu.itis.asadullin.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.asadullin.model.entity.Like;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    @Query(value = "SELECT * FROM likes WHERE like_id = :id", nativeQuery = true)
    Like getById(@Param("id") int id);

    @Query(value = "SELECT * FROM likes", nativeQuery = true)
    List<Like> getAll();

    @Query(value = "INSERT INTO likes (user_id, target_id, is_article) " +
            "SELECT :userId, :targetId, :isArticle " +
            "WHERE NOT EXISTS (SELECT 1 FROM likes WHERE user_id = :userId AND target_id = :targetId AND is_article = :isArticle)",
            nativeQuery = true)
    void insert(@Param("userId") int userId, @Param("targetId") int targetId, @Param("isArticle") boolean isArticle);

    @Query(value = "DELETE FROM likes WHERE user_id = :userId AND target_id = :targetId AND is_article = :isArticle", nativeQuery = true)
    void delete(@Param("userId") int userId, @Param("targetId") int targetId, @Param("isArticle") boolean isArticle);

    @Query(value = "SELECT COUNT(*) FROM likes WHERE target_id = :targetId AND is_article = :isArticle", nativeQuery = true)
    int countLikes(@Param("targetId") int targetId, @Param("isArticle") boolean isArticle);

    @Query(value = "SELECT * FROM likes WHERE user_id = :userId AND target_id = :targetId AND is_article = :isArticle", nativeQuery = true)
    Like findByUserIdAndTargetIdAndIsArticle(@Param("userId") int userId, @Param("targetId") int targetId, @Param("isArticle") boolean isArticle);
}
