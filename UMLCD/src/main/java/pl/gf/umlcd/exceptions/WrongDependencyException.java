package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongDependencyException extends Exception{
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Dependency connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("Dependency can appear only between two classes."/*+ "or class and enum (enum depends on class)"*/);
        alert.showAndWait();
    }
}
