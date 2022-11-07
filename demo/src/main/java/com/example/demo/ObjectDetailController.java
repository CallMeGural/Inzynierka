package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ObjectDetailController {
    @FXML
    public VBox vBox;
    @FXML
    public Button button;
    @FXML
    public FlowPane pane;

    private Stage stage;

    public int pickedId;
    public Data data = new Data();

    public void saveAndExit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();
        MainViewController controller = loader.getController();

        if(data.entityList.get(pickedId).getClass()!=OtherClassEntity.class) {
            //Class
            TextField textField = (TextField) vBox.getChildren().get(0);
            data.entityList.get(pickedId).getVbox().getChildren().addAll(
                    //vBox.getChildren()
                    new Label(textField.getText()),
                    vBox.getChildren().get(1),
                    vBox.getChildren().get(2),
                    vBox.getChildren().get(3),
                    vBox.getChildren().get(4));
        }
        else {
            //Other
            TextField textField = (TextField) vBox.getChildren().get(1);
            data.entityList.get(pickedId).getVbox().getChildren().addAll(
                    vBox.getChildren().get(0),
                    //vBox.getChildren()
                    new Label(textField.getText()),
                    vBox.getChildren().get(2),
                    vBox.getChildren().get(3),
                    vBox.getChildren().get(4),
                    vBox.getChildren().get(5));
        }


        controller.data=data;
        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

}
