module org.example.kolokwium2_2022.client {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.example.kolokwium2_2022.client;
    opens org.example.kolokwium2_2022.client to javafx.fxml;
}