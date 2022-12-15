package pl.gf.umlcd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.gf.umlcd.Data;
import pl.gf.umlcd.MainViewController;
import pl.gf.umlcd.OtherClassEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ObjectDetailController implements Initializable {
    @FXML
    public VBox vBox;
    @FXML
    public Button button;
    @FXML
    public FlowPane pane;

    public int pickedId;
    //public Data data = new Data();
    public Data data;

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
