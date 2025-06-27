package org.example.powt2;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.powt2.client.ServerThread;
import org.example.powt2.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Controller {
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Slider radiusSlider;
    @FXML
    private Canvas canvas;

    private String host;
    private int port;
    private Server server;
    private ServerThread serverThread;

    @FXML
    public void onStartServerClicked() {
        this.host = this.addressField.getText();
        this.port = Integer.parseInt(this.portField.getText());

        this.server = new Server(port);
        this.server.start();

        listen();
    }

    @FXML
    public void onConnectClicked() {
        this.host = this.addressField.getText();
        this.port = Integer.parseInt(this.portField.getText());

        listen();
    }

    @FXML
    public void onMouseClicked(MouseEvent event) {
        Color color = this.colorPicker.getValue();
        double radius = this.radiusSlider.getValue();

        double x = event.getX() - radius;
        double y = event.getY() - radius;

        if(this.host != null && this.port != 0){
            this.serverThread = new ServerThread(this.host,this.port,x, y, radius, color);
            this.serverThread.start();
        }
    }

    public void draw(Dot dot) {
        GraphicsContext context = this.canvas.getGraphicsContext2D();
        context.setFill(dot.color());
        context.fillOval(dot.x(), dot.y(), dot.r() * 2,dot.r() * 2);
    }

    public void listen() {
        new Thread(() -> {
            try(Socket socket = new Socket(this.host, this.port)) {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line;
                while ((line = reader.readLine()) != null) {
                    if(!line.isEmpty()){
                        Dot dot = Dot.fromMessage(line);
                        draw(dot);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
