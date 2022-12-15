package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongRealizationException extends Exception {
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Realization connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("Realization only appears between interface and class. (Class realizes interface).");
        alert.showAndWait();
    }
}
