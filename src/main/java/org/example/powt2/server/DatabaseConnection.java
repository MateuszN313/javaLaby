package org.example.powt2.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static void connect(String filePath) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    static public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initTable() {
        try {
            String createSQLTable = "CREATE TABLE IF NOT EXISTS dot(" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "x INTEGER NOT NULL," +
                    "y INTEGER NOT NULL," +
                    "radius INTEGER NOT NULL," +
                    "color TEXT NOT NULL" +
                    ");";
            PreparedStatement statement = connection.prepareStatement(createSQLTable);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
