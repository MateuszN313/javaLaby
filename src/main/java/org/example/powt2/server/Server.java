package org.example.powt2.server;

import javafx.scene.paint.Color;
import org.example.powt2.Dot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private ServerSocket serverSocket;
    private List<ClientThread> clients;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            DatabaseConnection.connect("dots.db");
            DatabaseConnection.initTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.clients = new ArrayList<>();
    }

    @Override
    public void run() {
        while(true) {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
                ClientThread clientThread = new ClientThread(clientSocket, this);
                this.clients.add(clientThread);
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeClient(ClientThread client) {
        clients.remove(client);
        System.out.println("removed");
    }

    public void broadcast(String message){
        for(ClientThread client : clients)
            client.send(message);
    }

    public void saveDotFromMessage(String message) {
        Dot dot = Dot.fromMessage(message);
        try {
            String insertSQL = "INSERT INTO dot VALUES (NULL, ?, ?, ?, ?);";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(insertSQL);

            statement.setInt(1,(int) dot.x());
            statement.setInt(2,(int) dot.y());
            statement.setInt(3,(int) dot.r());
            statement.setString(4,dot.color().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getDotsFromDb() {
        try {
            String selectSQL = "SELECT * FROM dot;";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(selectSQL);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            List<String> result = new ArrayList<>();
            while (resultSet.next()) {
                int x = resultSet.getInt(2);
                int y = resultSet.getInt(3);
                int r = resultSet.getInt(4);
                String color = resultSet.getString(5);
                result.add(Dot.toMessage(x ,y ,r , Color.valueOf(color)));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
