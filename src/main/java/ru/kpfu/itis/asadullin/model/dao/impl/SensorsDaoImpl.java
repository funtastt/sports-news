package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.entity.Like;
import ru.kpfu.itis.asadullin.model.entity.Sensor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class SensorsDaoImpl {
    Connection connection = getConnection();

    public List<Sensor> getAll() {
        List<Sensor> sensors = new ArrayList<>();
        String sql = "SELECT * FROM sensor_data";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sensors.add(resultSetToSensor(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sensors;
    }

    private Sensor resultSetToSensor(ResultSet resultSet) throws SQLException {
            return new Sensor(
                    resultSet.getLong("sensor_id"),
                    resultSet.getLong("measure"),
                    resultSet.getInt("value"),
                    resultSet.getTimestamp("time")
            );
    }
}
