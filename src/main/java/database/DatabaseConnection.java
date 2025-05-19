package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private java.sql.Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connect(String dbPath){
        try{
            String url = "jdbc:sqlite:" + dbPath;
            this.connection = DriverManager.getConnection(url);
            System.out.println("Database connected");
        } catch (SQLException e) {
            System.err.println("Connect error" + e.getMessage());
        }
    }
    public void disconnect(){
        try{
            if(this.connection != null && !this.connection.isClosed()){
                this.connection.close();
                System.out.println("Closed connection with database");
            }
        } catch (SQLException e) {
            System.err.println("Disconnect error" + e.getMessage());
        }
    }
}
