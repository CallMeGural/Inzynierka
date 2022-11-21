package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongAssociationException extends Exception{
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aggregation connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("Association can appear only between class and class or class and enum.");
        alert.showAndWait();
    }
}
