package pl.gf.umlcd.exceptions;

import javafx.scene.control.Alert;

public class WrongAssociationException extends Exception{
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Association connection warning");
        alert.setHeaderText("Cannot create connection");
        alert.setContentText("Association cannot appear between class or interface and enumeration.\n" +
                "You can connect class and enum with directed association");
        alert.showAndWait();
    }
}
