package ru.kpfu.itis.asadullin.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.asadullin.model.entity.Friend;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    @Query(value = "SELECT * FROM friends WHERE friend_request_id = :id", nativeQuery = true)
    Friend getById(@Param("id") int id);

    @Query(value = "SELECT * FROM friends", nativeQuery = true)
    List<Friend> getAll();

    @Query(value = "SELECT * FROM friends WHERE user_id = :userId", nativeQuery = true)
    List<Friend> getAllForUserId(@Param("userId") int userId);

    @Query(value = "INSERT INTO friends (user_id, friend_id, request_time) " +
            "SELECT :userId, :friendId, CURRENT_TIMESTAMP " +
            "WHERE NOT EXISTS (SELECT 1 FROM friends WHERE user_id = :userId AND friend_id = :friendId)",
            nativeQuery = true)
    void insert(@Param("userId") int userId, @Param("friendId") int friendId);

    @Query(value = "DELETE FROM friends WHERE user_id = :userId AND friend_id = :friendId", nativeQuery = true)
    void delete(@Param("userId") int userId, @Param("friendId") int friendId);

    @Query(value = "SELECT COUNT(*) FROM friends WHERE user_id = :userId AND friend_id = :friendId", nativeQuery = true)
    int countByUserIdAndFriendId(@Param("userId") int userId, @Param("friendId") int friendId);
}
