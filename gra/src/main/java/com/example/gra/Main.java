package com.example.gra;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GameCanvas gameCanvas = new GameCanvas(512, 512);
        gameCanvas.draw();
        StackPane pane = new StackPane(gameCanvas);
        Scene scene = new Scene(pane);

        stage.setTitle("super gra");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
