package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.entity.Friend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class FriendDaoImpl implements Dao<Friend> {
    private final Connection connection = getConnection();

    @Override
    public Friend getById(int id) {
        String sql = "SELECT * FROM friends WHERE friend_request_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToFriend(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Friend> getAll() {
        List<Friend> likes = new ArrayList<>();
        String sql = "SELECT * FROM friends";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                likes.add(resultSetToFriend(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }

    @Override
    public void insert(Friend friend) {
        if (!isFriendAdded(friend)) {
            String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, friend.getUserId());
                statement.setInt(2, friend.getFriendId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isFriendAdded(Friend friend) {
        String sql = "SELECT * FROM friends WHERE user_id = ? and friend_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, friend.getUserId());
            statement.setInt(2, friend.getFriendId());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void update(Friend friend) {
        insert(friend);
    }

    @Override
    public void delete(Friend friend) {
        String sql = "DELETE FROM friends WHERE user_id = ? and friend_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, friend.getUserId());
            statement.setInt(2, friend.getFriendId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Friend resultSetToFriend(ResultSet resultSet) throws SQLException {
        return new Friend(
                resultSet.getInt("user_id"),
                resultSet.getInt("friend_id")
        );
    }
}
