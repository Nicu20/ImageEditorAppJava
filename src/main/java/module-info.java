module org.example.imageeditorapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.imageeditorapp to javafx.fxml;
    exports org.example.imageeditorapp;
}