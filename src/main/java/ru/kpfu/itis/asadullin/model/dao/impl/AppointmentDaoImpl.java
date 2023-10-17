package ru.kpfu.itis.asadullin.model.dao.impl;

import ru.kpfu.itis.asadullin.model.dao.Dao;
import ru.kpfu.itis.asadullin.model.entity.Appointment;
import ru.kpfu.itis.asadullin.model.entity.Master;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.kpfu.itis.asadullin.controller.util.DatabaseConnectionUtil.getConnection;

public class AppointmentDaoImpl implements Dao<Appointment> {
    Connection connection = getConnection();

    @Override
    public Appointment getById(int id) {
        String sql = "SELECT * FROM appointments WHERE master_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetToAppointment(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Appointment resultSetToAppointment(ResultSet resultSet) throws SQLException {
        return new Appointment(
                resultSet.getInt("appointment_id"),
                resultSet.getInt("master_id"),
                resultSet.getInt("service_id"),
                resultSet.getTimestamp("time"),
                resultSet.getString("client_phone")
        );
    }

    @Override
    public List<Appointment> getAll() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE master_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                appointments.add(resultSetToAppointment(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    public void insert(Appointment appointment) {
        String sql = "INSERT INTO appointments " +
                "(master_id, " +
                "time, " +
                "client_phone, " +
                "service_id)" +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, appointment.getMasterId());
            statement.setTimestamp(2, appointment.getTime());
            statement.setString(3, appointment.getClientPhone());
            statement.setInt(4, appointment.getServiceId());


            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAppointmentAvailable(Appointment appointment) {

        String sql = "SELECT " +
                "    appointments.time as time, " +
                "    services.duration_minutes as duration " +
                "FROM " +
                "    appointments LEFT JOIN services ON " +
                "appointments.service_id = services.service_id WHERE master_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, appointment.getMasterId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Timestamp start = resultSet.getTimestamp("time");
                int durationMinutes = resultSet.getInt("duration");

                Timestamp resultSetEnd = new Timestamp(start.getTime() + TimeUnit.MINUTES.toMillis(durationMinutes));

                Timestamp appointmentEnd = new Timestamp(appointment.getTime().getTime() + TimeUnit.MINUTES.toMillis(durationMinutes));

                if (appointment.getTime().compareTo(start) >= 0 && resultSetEnd.compareTo(appointment.getTime()) >= 0) {
                    return false;
                }

                if (start.compareTo(appointment.getTime()) >= 0 && appointmentEnd.compareTo(start) >= 0) {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
