package ru.kpfu.itis.asadullin.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.asadullin.model.entity.Comment;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT * FROM comments WHERE comment_id = :id", nativeQuery = true)
    Comment getById(@Param("id") int id);

    @Query(value = "SELECT * FROM comments", nativeQuery = true)
    List<Comment> getAll();

    @Modifying
    @Query(value = "INSERT INTO comments (text, sending_time, user_id, article_id) VALUES (:text, :sendingTime, :userId, :articleId)", nativeQuery = true)
    void insert(@Param("text") String text, @Param("sendingTime") Timestamp sendingTime, @Param("userId") int userId, @Param("articleId") int articleId);

    @Modifying
    @Query(value = "UPDATE comments SET text = :text, sending_time = :sendingTime, likes = :likes WHERE comment_id = :id", nativeQuery = true)
    void update(@Param("id") int id, @Param("text") String text, @Param("sendingTime") Timestamp sendingTime, @Param("likes") int likes);

    @Modifying
    @Query(value = "DELETE FROM comments WHERE comment_id = :id", nativeQuery = true)
    void delete(@Param("id") int id);

    @Query(value = "SELECT comment_id FROM comments WHERE text = :text AND sending_time = :sendingTime", nativeQuery = true)
    int findCommentId(@Param("text") String text, @Param("sendingTime") Timestamp sendingTime);
}