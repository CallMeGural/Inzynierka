package pl.gf.umlcd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionViewController implements Initializable {

    @FXML
    public Label startNameLabel, endNameLabel, typeLabel;
    @FXML
    public ComboBox<String> startCombo, endCombo;
    @FXML
    Button saveButton;
    @FXML
    AnchorPane pane;

    String[] cardinalities;
    public Data data;
    public int connectionId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = Data.getInstance();
        cardinalities = new String[]{"not specified", "1", "0..1", "*", "1..*", "{ordered}"};
        startCombo.getItems().addAll(cardinalities);
        endCombo.getItems().addAll(cardinalities);
    }

    public void saveAndExit(){
        if(startCombo.getValue() == null)
            data.connectedPairs.get(connectionId).setCardinality1(startCombo.getPromptText());
        else
            data.connectedPairs.get(connectionId).setCardinality1(startCombo.getValue());

        if (endCombo.getValue() == null)
            data.connectedPairs.get(connectionId).setCardinality2(endCombo.getPromptText());
        else
            data.connectedPairs.get(connectionId).setCardinality2(endCombo.getValue());

        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}