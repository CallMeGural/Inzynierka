module pl.gf.umlcd {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires lombok;
    requires SwingUtils;
    requires java.desktop;
    requires ini4j;


    opens pl.gf.umlcd to javafx.fxml;
    exports pl.gf.umlcd;
    opens pl.gf.umlcd.connections to javafx.fxml;
    exports pl.gf.umlcd.connections;
}