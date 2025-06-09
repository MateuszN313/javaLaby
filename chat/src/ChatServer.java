import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT = 3000;
    private static Map<String, PrintWriter> clientWriters = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Server is running on port: " + PORT);
        ExecutorService pool = Executors.newFixedThreadPool(50);

        try(ServerSocket listener = new ServerSocket(PORT)){
            while(true){
                pool.execute(new ClientHandler(listener.accept()));
            }
        }catch(IOException e){
            System.err.println("Server error: " + e.getMessage());
        }finally {
            pool.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String userName;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){
            try{
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                userName = in.readLine();
                while(userName == null || userName.trim().isEmpty()){
                    out.println("nick nie może być pusty");
                    userName = in.readLine();
                }
                System.out.println(userName + " dołączył do czatu");
                broadcastMessage(userName + " dołączył do czatu");
                out.println("cześć " + userName + " dołączyłeś do czatu");

                synchronized (clientWriters) {
                    if(clientWriters.containsKey(userName)){
                        out.println(userName + "Ten login jest zajęty");
                        return;
                    }
                    clientWriters.put(userName, out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equals("/online")){
                        synchronized (clientWriters) {
                            for (String client : clientWriters.keySet()) {
                                clientWriters.get(userName).println(client);
                            }
                        }
                        continue;
                    }
                    if (message.startsWith("/w"))  {
                        String[] tokens = message.split(" ");
                        if (tokens.length < 3) {
                            synchronized (clientWriters) {
                                clientWriters.get(userName).println("nieprawidłowa składnia\n" +
                                        "poprawna składnia to /w recipient message");
                            }
                            continue;
                        }

                        String whisperMessage = message.substring(tokens[1].length() + 4);
                        synchronized (clientWriters) {
                            if(!clientWriters.containsKey(tokens[1])) {
                                clientWriters.get(userName).println("ten użytkownik nie jest online");
                                continue;
                            }
                            clientWriters.get(tokens[1]).println("szept od " + userName + ": " + whisperMessage);
                        }
                        continue;
                    }
                    System.out.println(userName + ": "+ message);
                    broadcastMessage(userName + ": " + message);
                }
            } catch (IOException e) {
                System.err.println("Error handling client " + userName + ": " + e.getMessage());
            } finally {
                if (userName != null) {
                    synchronized (clientWriters) {
                        clientWriters.remove(userName);
                    }
                    System.out.println(userName + " wyszedł z czatu");
                    broadcastMessage(userName + " wyszedł z czatu");
                }

                if (out != null) {
                    synchronized (clientWriters){
                        clientWriters.remove(out);
                    }
                }

                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing socket for " + userName + ": " + e.getMessage());
                }
            }
        }
    }

    private static void broadcastMessage(String message){
        synchronized (clientWriters) {
            for(PrintWriter writer : clientWriters.values()){
                writer.println(message);
            }
        }
    }
}
