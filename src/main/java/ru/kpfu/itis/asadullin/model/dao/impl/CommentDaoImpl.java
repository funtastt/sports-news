package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class CommentDaoImpl implements Dao<Comment> {
    private final Connection connection = getConnection();
    @Override
    public Comment getById(int id) {
        String sql = "SELECT * FROM comments WHERE comment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToComment(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Comment resultSetToComment(ResultSet resultSet) throws SQLException{
        return new Comment(
                resultSet.getInt("comment_id"),
                resultSet.getString("text"),
                resultSet.getTimestamp("sending_time"),
                resultSet.getInt("likes"),
                resultSet.getInt("user_id"),
                resultSet.getInt("article_id")
        );
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                comments.add(resultSetToComment(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return comments;
    }

    @Override
    public void insert(Comment comment) {
        String sql = "INSERT INTO comments (text, sending_time, user_id, article_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, comment.getText());
            statement.setTimestamp(2, comment.getSendingTime());
            statement.setInt(3, comment.getAuthorId());
            statement.setInt(4, comment.getArticleId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Comment comment) {
        String sql = "UPDATE comments SET text = ?, sending_time = ?, likes = ? WHERE comment_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, comment.getText());
            statement.setTimestamp(2, comment.getSendingTime());
            statement.setInt(3, comment.getLikes());
            statement.setInt(4, comment.getCommentId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Comment comment) {
        String sql = "DELETE FROM comments WHERE comment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, comment.getCommentId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findCommentId(String text, Timestamp time) {
        String sql = "SELECT * FROM comments WHERE text = ? AND sending_time = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, text);
            statement.setTimestamp(2, time);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("comment_id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
