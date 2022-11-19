package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongEnumConnectionException extends Exception {
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Enum connection warning");
        alert.setHeaderText("Cannot create connection with Enumeration");
        alert.setContentText("Enumeration entity can be connected only with Association and only with Class entity.");
        alert.showAndWait();
    }
}
