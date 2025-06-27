module org.example.powt2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens org.example.powt2 to javafx.fxml;
    exports org.example.powt2;
}