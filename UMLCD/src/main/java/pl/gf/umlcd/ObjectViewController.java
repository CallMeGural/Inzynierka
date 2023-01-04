package pl.gf.umlcd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class ObjectViewController implements Initializable {
    @FXML
    private VBox vBox;
    @FXML
    private FlowPane pane;
    private int pickedId;
    private Data data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = Data.getInstance();
    }

    public void saveAndExit(ActionEvent e) {
            if (data.getEntities().get(pickedId).getClass() != OtherClassEntity.class) {
                //Class
                TextField textField = (TextField) vBox.getChildren().get(0);
                data.getEntities().get(pickedId).getVBox().getChildren().addAll(
                        new Label(textField.getText()),
                        vBox.getChildren().get(1),
                        vBox.getChildren().get(2),
                        vBox.getChildren().get(3),
                        vBox.getChildren().get(4));
            } else {
                //Other
                TextField textField = (TextField) vBox.getChildren().get(1);
                data.getEntities().get(pickedId).getVBox().getChildren().addAll(
                        vBox.getChildren().get(0),
                        new Label(textField.getText()),
                        vBox.getChildren().get(2),
                        vBox.getChildren().get(3),
                        vBox.getChildren().get(4),
                        vBox.getChildren().get(5));
            }
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
    }
}
