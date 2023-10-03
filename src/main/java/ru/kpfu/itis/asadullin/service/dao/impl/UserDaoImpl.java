package ru.kpfu.itis.asadullin.service.dao.impl;

import ru.kpfu.itis.asadullin.service.dao.Dao;
import ru.kpfu.itis.asadullin.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.database.DatabaseConnectionUtil.getConnection;

public class UserDaoImpl implements Dao<User> {
    private final Connection connection = getConnection();
    @Override
    public User get(int id) {
        List<User> users = getAll();

        for (User user : users) {
            if (user.getUserId() == id) {
                return user;
            }
        }

        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (Statement statement = getConnection().createStatement()) {
            String sql = "SELECT * from users";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date dateOfBirth = resultSet.getDate("date_of_birth");
                String country = resultSet.getString("country");
                String city = resultSet.getString("city");
                Date registrationDate = resultSet.getDate("registration_date");
                String profilePicture = resultSet.getString("profile_picture");
                String bio = resultSet.getString("bio");
                boolean isVerified = resultSet.getBoolean("is_verified");
                boolean isMale = resultSet.getBoolean("is_male");

                User user = new User(userId, username, email, password, firstName, lastName, dateOfBirth, country, city, registrationDate, profilePicture, bio, isVerified, isMale);

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void insert(User user) {
        try {
            String sql = "insert into users(" +
                    "username, " +
                    "email, " +
                    "password, " +
                    "first_name, " +
                    "last_name, " +
                    "date_of_birth, " +
                    "country, " +
                    "city, " +
                    "registration_date, " +
                    "profile_picture, " +
                    "bio, " +
                    "is_verified, " +
                    "is_male" +
                    ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            saveUserData(user, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try {
            String sql = "UPDATE users " +
                    "SET username = ?, " +
                    "email = ?, " +
                    "password = ?, " +
                    "first_name = ?, " +
                    "last_name = ?, " +
                    "date_of_birth = ?, " +
                    "country = ?, " +
                    "city = ?, " +
                    "registration_date = ?, " +
                    "profile_picture = ?, " +
                    "bio = ?, " +
                    "is_verified = ?, " +
                    "is_male = ?, " +
                    "user_id = ? " +
                    "WHERE user_id = ?;";
            saveUserData(user, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUserData(User user, String sql) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setDate(6, user.getDateOfBirth());
            statement.setString(7, user.getCountry());
            statement.setString(8, user.getCity());
            statement.setDate(9, user.getRegistrationDate());
            statement.setString(10, user.getProfilePicture());
            statement.setString(11, user.getBio());
            statement.setBoolean(12, user.isVerified());
            statement.setBoolean(13, user.isMale());
            if (user.getUserId() != 0) {
                statement.setInt(14, user.getUserId());
                statement.setInt(15, user.getUserId());
            }

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
