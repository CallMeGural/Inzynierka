package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongCompositionOrAggregation extends Exception{
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aggregation or Composition connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("Composition and Aggregation can appear only between two classes or class and interface.");
        alert.showAndWait();
    }
}
