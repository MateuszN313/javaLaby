package org.example.powt2.server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientThread extends Thread{
    private Socket socket;
    private PrintWriter writer;
    private Server server;

    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);

            List<String> dots = server.getDotsFromDb();
            for (String dot : dots) {
                send(dot);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                server.saveDotFromMessage(line);
                server.broadcast(line);
            }
            System.out.println("closed");
            server.removeClient(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message){
        writer.println(message);
    }
}
