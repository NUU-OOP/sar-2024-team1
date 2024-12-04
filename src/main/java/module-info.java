module un{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires java.management;
    requires java.desktop;
    opens org.example;
    exports org.example;
}