package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.entity.Master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class MasterDaoImpl implements Dao<Master>{
    Connection connection =  getConnection();

    @Override
    public Master getById(int id) {
        String sql = "SELECT * FROM masters WHERE master_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToMaster(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Master resultSetToMaster(ResultSet resultSet) throws SQLException {
        return new Master(
                resultSet.getInt("master_id"),
                resultSet.getString("name")
        );
    }

    @Override
    public List<Master> getAll() {
        List<Master> likes = new ArrayList<>();
        String sql = "SELECT * FROM masters";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                likes.add(resultSetToMaster(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }
}
