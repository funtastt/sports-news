package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.entity.Master;
import ru.kpfu.itis.asadullin.model.entity.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class ServiceDaoImpl implements Dao<Service> {
    Connection connection = getConnection();

    @Override
    public Service getById(int id) {
        String sql = "SELECT * FROM services WHERE service_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToService(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Service resultSetToService(ResultSet resultSet) throws SQLException {
        return new Service(
                resultSet.getInt("service_id"),
                resultSet.getString("name"),
                resultSet.getInt("duration_minutes")
        );
    }

    @Override
    public List<Service> getAll() {
        List<Service> likes = new ArrayList<>();
        String sql = "SELECT * FROM services";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                likes.add(resultSetToService(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return likes;
    }
}
