module org.example.kolokwium2_2022 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.kolokwium2_2022 to javafx.fxml;
    exports org.example.kolokwium2_2022;
}