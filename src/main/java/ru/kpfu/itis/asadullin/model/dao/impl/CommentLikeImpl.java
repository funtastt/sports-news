package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.entity.CommentLike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class CommentLikeImpl implements Dao<CommentLike> {
    Connection connection = getConnection();

    @Override
    public CommentLike getById(int id) {
        String sql = "SELECT * FROM comment_likes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToCommentLike(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private CommentLike resultSetToCommentLike(ResultSet resultSet) throws SQLException {
        return new CommentLike(
                resultSet.getInt("user_id"),
                resultSet.getInt("comment_id")
        );
    }

    @Override
    public List<CommentLike> getAll() {
        List<CommentLike> likes = new ArrayList<>();
        String sql = "SELECT * FROM comment_likes";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                likes.add(resultSetToCommentLike(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }

    @Override
    public void insert(CommentLike commentLike) {
        if (!isCommentLiked(commentLike)) {
            String sql = "INSERT INTO comment_likes (user_id, comment_id) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, commentLike.getUserId());
                statement.setInt(2, commentLike.getCommentId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isCommentLiked(CommentLike commentLike) {
        String sql = "SELECT * FROM comment_likes WHERE user_id = ? and comment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, commentLike.getUserId());
            statement.setInt(2, commentLike.getCommentId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void update(CommentLike commentLike) {
        insert(commentLike);
    }

    @Override
    public void delete(CommentLike commentLike) {
        String sql = "DELETE FROM comment_likes WHERE user_id = ? and comment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, commentLike.getUserId());
            statement.setInt(2, commentLike.getCommentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLikesCount(int commentId) {
        String sql = "SELECT COUNT(*) FROM comment_likes WHERE comment_id = ?";
        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, commentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return count;
    }
}
