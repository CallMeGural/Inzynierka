module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires lombok;
    requires webp.imageio;
    requires SwingUtils;
    requires java.desktop;
    requires ini4j;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.connections;
    opens com.example.demo.connections to javafx.fxml;
}