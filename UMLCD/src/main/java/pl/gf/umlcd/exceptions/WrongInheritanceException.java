package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongInheritanceException extends Exception{
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Inheritance connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("Inheritance can appear only between two classes or two interfaces");
        alert.showAndWait();
    }
}
