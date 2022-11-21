package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongDirectedAssociationException extends Exception {
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Directed Association connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("Wrong information flow. Reverse the connection.");
        alert.showAndWait();
    }
}
