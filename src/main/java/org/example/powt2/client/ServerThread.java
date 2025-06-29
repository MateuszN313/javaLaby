package org.example.powt2.client;

import javafx.scene.paint.Color;
import org.example.powt2.Dot;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private PrintWriter writer;
    private double x;
    private double y;
    private double r;
    private Color color;

    public ServerThread(String host,int port,double x, double y, double r, Color color) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
    }

    @Override
    public void run() {
        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            send();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send() {
        this.writer.println(Dot.toMessage(this.x, this.y, this.r, this.color));
    }
}
