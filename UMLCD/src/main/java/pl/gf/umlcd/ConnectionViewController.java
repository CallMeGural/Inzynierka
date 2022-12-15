package pl.gf.umlcd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class ConnectionViewController implements Initializable {

    @FXML
    private Label startNameLabel, endNameLabel, typeLabel;
    @FXML
    private ComboBox<String> startCombo, endCombo;
    @FXML
    private Button saveButton;
    @FXML
    private AnchorPane pane;

    private String[] cardinalities;
    private Data data;
    private int connectionId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = Data.getInstance();
        cardinalities = new String[]{"not specified", "1", "0..1", "*", "1..*", "{ordered}"};
        startCombo.getItems().addAll(cardinalities);
        endCombo.getItems().addAll(cardinalities);
    }

    public void saveAndExit(){
        if(startCombo.getValue() == null)
            data.getConnectedPairs().get(connectionId).setCardinality1(startCombo.getPromptText());
        else
            data.getConnectedPairs().get(connectionId).setCardinality1(startCombo.getValue());

        if (endCombo.getValue() == null)
            data.getConnectedPairs().get(connectionId).setCardinality2(endCombo.getPromptText());
        else
            data.getConnectedPairs().get(connectionId).setCardinality2(endCombo.getValue());

        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}