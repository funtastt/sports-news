package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.entity.Appointment;
import ru.kpfu.itis.asadullin.model.entity.Master;
import ru.kpfu.itis.asadullin.model.entity.MasterService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class MasterServiceDaoImpl implements Dao<MasterService> {
    Connection connection =  getConnection();

    @Override
    public MasterService getById(int id) {
        String sql = "SELECT * FROM master_services WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToMasterService(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private MasterService resultSetToMasterService(ResultSet resultSet) throws SQLException {
        return new MasterService(
                resultSet.getInt("id"),
                resultSet.getInt("master_id"),
                resultSet.getInt("service_id")
        );
    }

    @Override
    public List<MasterService> getAll() {
        List<MasterService> services = new ArrayList<>();
        String sql = "SELECT * FROM master_services";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                services.add(resultSetToMasterService(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return services;
    }

    public List<MasterService> getAllForMasterId(int id) {
        List<MasterService> services = new ArrayList<>();
        String sql = "SELECT * FROM master_services WHERE master_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                services.add(resultSetToMasterService(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return services;
    }
}
