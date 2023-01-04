package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongSelfAssociationException extends Exception{
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("Self association appears only with simple class");
        alert.showAndWait();
    }
}
