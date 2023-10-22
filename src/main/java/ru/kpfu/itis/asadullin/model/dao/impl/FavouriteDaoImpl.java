package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.entity.Favourite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class FavouriteDaoImpl implements Dao<Favourite> {
    Connection connection = getConnection();

    @Override
    public Favourite getById(int id) {
        String sql = "SELECT * FROM favourites WHERE favourite_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToFavourite(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Favourite resultSetToFavourite(ResultSet resultSet) throws SQLException {
        return new Favourite(
                resultSet.getInt("favourite_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("article_id")
        );
    }

    @Override
    public List<Favourite> getAll() {
        List<Favourite> favourites = new ArrayList<>();
        String sql = "SELECT * FROM favourites";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                favourites.add(resultSetToFavourite(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return favourites;
    }

    @Override
    public void insert(Favourite favourite) {
        if (!isFavoured(favourite)) {
            String sql = "INSERT INTO favourites (user_id, article_id) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, favourite.getUserId());
                statement.setInt(2, favourite.getArticleId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isFavoured(Favourite favourite) {
        String sql = "SELECT * FROM favourites WHERE user_id = ? and article_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, favourite.getUserId());
            statement.setInt(2, favourite.getArticleId());

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void update(Favourite favourite) {
        insert(favourite);
    }

    @Override
    public void delete(Favourite favourite) {
        String sql = "DELETE FROM favourites WHERE user_id = ? and article_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, favourite.getUserId());
            statement.setInt(2, favourite.getArticleId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
