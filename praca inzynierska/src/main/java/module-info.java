module pl.gornickifilip.inz {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires lombok;
    requires SwingUtils;
    requires java.desktop;
    requires ini4j;


    opens pl.gornickifilip.inz to javafx.fxml;
    exports pl.gornickifilip.inz;
    opens pl.gornickifilip.inz.connections to javafx.fxml;
    exports pl.gornickifilip.inz.connections;

}