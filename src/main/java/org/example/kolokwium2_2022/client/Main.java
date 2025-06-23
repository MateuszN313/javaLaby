package org.example.kolokwium2_2022;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Controller controller = new Controller(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
