package org.example.kolokwium2_2022;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public Controller(Stage stage){
        try {
            this.root = FXMLLoader.load(getClass().getResource("view.fxml"));
            this.scene = new Scene(this.root);
            this.stage.setScene(this.scene);
            this.stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
