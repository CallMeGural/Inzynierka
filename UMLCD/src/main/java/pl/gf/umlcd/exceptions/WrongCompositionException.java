package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongCompositionException extends Exception{
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aggregation connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("Composition can appear only between the same type entities.");
        alert.showAndWait();
    }
}
