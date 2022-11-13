package pl.gf.umlcd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        try {
            Parent root = loader.load();
            MainViewController controller = loader.getController();

            if (data.entityList.get(pickedId).getClass() != OtherClassEntity.class) {
                //Class
                TextField textField = (TextField) vBox.getChildren().get(0);
                data.entityList.get(pickedId).getVbox().getChildren().addAll(
                        new Label(textField.getText()),
                        vBox.getChildren().get(1),
                        vBox.getChildren().get(2),
                        vBox.getChildren().get(3),
                        vBox.getChildren().get(4));
            } else {
                //Other
                TextField textField = (TextField) vBox.getChildren().get(1);
                data.entityList.get(pickedId).getVbox().getChildren().addAll(
                        vBox.getChildren().get(0),
                        new Label(textField.getText()),
                        vBox.getChildren().get(2),
                        vBox.getChildren().get(3),
                        vBox.getChildren().get(4),
                        vBox.getChildren().get(5));
            }
            controller.data = data;
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        } catch (IOException exception) {
            loadingOtherControllerException();
        }
    }
    private void loadingOtherControllerException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Controller error");
        alert.setHeaderText("Could not read other controller");
        alert.setContentText("Trying to read the FXML file that does not exist");
        alert.showAndWait();
    }
}
