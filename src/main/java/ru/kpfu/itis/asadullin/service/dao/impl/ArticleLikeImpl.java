package ru.kpfu.itis.asadullin.service.dao.impl;

import ru.kpfu.itis.asadullin.model.entity.ArticleLike;
import ru.kpfu.itis.asadullin.service.dao.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.service.util.DatabaseConnectionUtil.getConnection;

public class ArticleLikeImpl implements Dao<ArticleLike> {
    Connection connection = getConnection();

    @Override
    public ArticleLike getById(int id) {
        String sql = "SELECT * FROM article_likes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToUserLike(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ArticleLike resultSetToUserLike(ResultSet resultSet) throws SQLException {
        return new ArticleLike(
                resultSet.getInt("user_id"),
                resultSet.getInt("article_id")
        );
    }

    @Override
    public List<ArticleLike> getAll() {
        List<ArticleLike> likes = new ArrayList<>();
        String sql = "SELECT * FROM article_likes";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                likes.add(resultSetToUserLike(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }

    @Override
    public void insert(ArticleLike articleLike) {
        if (!isArticleLiked(articleLike)) {
            String sql = "INSERT INTO article_likes (user_id, article_id) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, articleLike.getUserId());
                statement.setInt(2, articleLike.getArticleId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isArticleLiked(ArticleLike articleLike) {
        String sql = "SELECT * FROM article_likes WHERE user_id = ? and article_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, articleLike.getUserId());
            statement.setInt(2, articleLike.getArticleId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void update(ArticleLike articleLike) {
        insert(articleLike);
    }

    @Override
    public void delete(ArticleLike articleLike) {
        String sql = "DELETE FROM article_likes WHERE user_id = ? and article_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, articleLike.getUserId());
            statement.setInt(2, articleLike.getArticleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLikesCount(int articleId) {
        String sql = "SELECT COUNT(*) FROM article_likes WHERE article_id = ?";
        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, articleId);

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
