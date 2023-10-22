package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil;
import ru.kpfu.itis.asadullin.model.entity.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ArticleDaoImpl implements Dao<Article> {
    Connection connection = DatabaseConnectionUtil.getConnection();
    @Override
    public Article getById(int id) {
        String sql = "SELECT * FROM articles WHERE article_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToArticle(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Article getByTitle(String title) {
        String sql = "SELECT * FROM articles WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToArticle(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Article> getAll() {
        List<Article> articleList = new ArrayList<>();
        String sql = "SELECT * FROM articles";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                articleList.add(resultSetToArticle(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articleList;
    }

    private Article resultSetToArticle(ResultSet resultSet) throws SQLException {
        return new Article(
                resultSet.getInt("article_id"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getString("summary"),
                resultSet.getInt("author_id"),
                resultSet.getTimestamp("publish_time"),
                resultSet.getString("category"),
                resultSet.getString("image_url"),
                resultSet.getInt("views"),
                resultSet.getInt("likes")
        );
    }

    @Override
    public void insert(Article article) {
        String sql = "INSERT INTO articles " +
                "(title, " +
                "content, " +
                "author_id, " +
                "publish_time, " +
                "category, " +
                "image_url, " +
                "views, " +
                "likes, " +
                "summary) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            saveArticleData(article, statement);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Article article) {
        String sql = "UPDATE articles SET title = ?, content = ?, author_id = ?, publish_time = ?, category = ?, image_url = ?, views = ?, likes = ?, summary = ? WHERE article_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            saveArticleData(article, statement);
            statement.setInt(10, article.getArticleId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveArticleData(Article article, PreparedStatement statement) throws SQLException {
        statement.setString(1, article.getTitle().replace("\"", ""));
        statement.setString(2, article.getContent());
        statement.setInt(3, article.getAuthorId());
        statement.setTimestamp(4, article.getPublishTime());
        statement.setString(5, article.getCategory());
        statement.setString(6, article.getImageUrl());
        statement.setInt(7, article.getViews());
        statement.setInt(8, article.getLikes());
        statement.setString(9, article.getSummary());
    }

    @Override
    public void delete(Article article) {
        String sql = "DELETE FROM articles WHERE article_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, article.getArticleId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findArticleId(String content) {
        String sql = "SELECT * FROM articles WHERE content = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, content);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("article_id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
