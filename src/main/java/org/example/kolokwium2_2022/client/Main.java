package org.example.kolokwium2_2022.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 480);
        stage.setTitle("losowe słówka co 5 sekund");
        stage.setScene(scene);
        stage.show();

        try {
            Socket socket = new Socket("localhost", 5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Controller controller = fxmlLoader.getController();

            new Thread(() -> {
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        final String word = line;
                        if(!word.isEmpty()) {
                            Platform.runLater(() -> controller.addWord(word));
                        }
                        if(!stage.isShowing()) {
                            socket.close();
                            break;
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
