package ru.kpfu.itis.asadullin.controller.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {
    private static Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/sports";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static Connection getConnection()  {
        if (connection == null) {

            try {
                Class.forName("org.postgresql.Driver");

                connection = DriverManager.getConnection(
                       URL,
                       USER,
                       PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return connection;
    }
}
