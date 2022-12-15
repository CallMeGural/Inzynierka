package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class TheSameNodesConnectionException extends Exception{
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("You cannot connect class with itself");
        alert.showAndWait();
    }
}
