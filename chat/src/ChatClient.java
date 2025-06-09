import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 3000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Połączono z serwerem");

            String userName;
            while (true) {
                System.out.println("Podaj swój nick");
                userName = scanner.nextLine();
                out.println(userName);
                break;
            }

            new Thread(() -> {
                String line;
                try {
                    while ((line = in.readLine()) != null) {
                        if (!line.isEmpty()) {
                            if (!line.startsWith(userName)){
                                System.out.println(line);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Błąd połączenia z serwerem: " + e.getMessage());
                }
            }).start();

            String userInput;
            while (scanner.hasNextLine()) {
                userInput = scanner.nextLine();
                out.println(userInput);

            }
        } catch (IOException e) {
            System.err.println("Błąd połączenia z serwerem: " + e.getMessage());
        }
    }
}
