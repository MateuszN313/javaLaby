module org.example.kolokwium2_2023 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.kolokwium2_2023 to javafx.fxml;
    exports org.example.kolokwium2_2023;
}