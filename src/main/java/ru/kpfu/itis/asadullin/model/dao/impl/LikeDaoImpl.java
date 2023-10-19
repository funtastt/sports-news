package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.entity.ArticleLike;
import ru.kpfu.itis.asadullin.model.entity.Like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class LikeDaoImpl implements Dao<Like> {
    Connection connection = getConnection();

    @Override
    public Like getById(int id) {
        String sql = "SELECT * FROM likes WHERE like_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToLike(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Like resultSetToLike(ResultSet resultSet) throws SQLException {
        return new Like(
                resultSet.getInt("like_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("target_id"),
                resultSet.getBoolean("is_article")
        );
    }

    @Override
    public List<Like> getAll() {
        List<Like> likes = new ArrayList<>();
        String sql = "SELECT * FROM likes";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                likes.add(resultSetToLike(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }

    @Override
    public void insert(Like like) {
        if (!isLiked(like)) {
            String sql = "INSERT INTO likes (user_id, target_id, is_article) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, like.getUserId());
                statement.setInt(2, like.getTargetId());
                statement.setBoolean(3, like.isArticle());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isLiked(Like like) {
        String sql = "SELECT * FROM likes WHERE user_id = ? and target_id = ? and is_article = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, like.getUserId());
            statement.setInt(2, like.getTargetId());
            statement.setBoolean(3, like.isArticle());

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void update(Like like) {
        insert(like);
    }

    @Override
    public void delete(Like like) {
        String sql = "DELETE FROM likes WHERE user_id = ? and target_id = ? and is_article = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, like.getUserId());
            statement.setInt(2, like.getTargetId());
            statement.setBoolean(3, like.isArticle());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLikesCount(int targetId, boolean isArticle) {
        String sql = "SELECT COUNT(*) FROM likes WHERE target_id = ? and is_article = ?";
        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, targetId);
            preparedStatement.setBoolean(2, isArticle);

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
