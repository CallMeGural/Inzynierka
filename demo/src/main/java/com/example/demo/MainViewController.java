package com.example.demo;

import com.example.demo.connections.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class MainViewController {

    @FXML
    public BorderPane bPane;
    @FXML
    public Pane pane;
    @FXML
    public MenuItem loadButton, saveButton, screenShotButton;


    DraggableMaker draggableMaker = new DraggableMaker();
    Data data = new Data();
    SaveData saveData = new SaveData();

    public void makeScreenShot(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        WritableImage image = stage.getScene().snapshot(null);
        String filename = saveData.setFileName();
        File file = new File(filename+".png");

        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }

    public void saveFile(ActionEvent e) throws IOException {
        saveData.save(this);
    }

    public void loadFile(ActionEvent e) throws IOException {
        saveData.load(this,data);
    }


    public void createNewClassEntity(ActionEvent event) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.initializeEntity(draggableMaker,this,data,pane);

    }
    public void createNewClassEntity(ClassEntity classEntity) {
        classEntity.initializeEntity(draggableMaker,this,data,pane);
    }

    public void showClassEntity(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("detail-view.fxml"));
        Parent root = loader.load();
        ObjectDetailController controller = loader.getController();

        VBox temp = (VBox) e.getSource();
        String vBoxId = temp.getId();

        ClassEntity classEntity = new ClassEntity();
        classEntity.showEntity(data,this,controller,temp,vBoxId);
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void createNewEnumEntity(ActionEvent event) {
        OtherClassEntity entity = new OtherClassEntity(new Label(),OtherClassType.ENUM);
        entity.initializeEntity(draggableMaker,this, data, pane);
    }

    public void createNewEnumEntity(OtherClassEntity entity) {
        entity.initializeEntity(draggableMaker,this,data,pane);
    }

    public void showOtherEntity(MouseEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("detail-view.fxml"));
        Parent root = loader.load();
        ObjectDetailController controller = loader.getController();

        VBox temp = (VBox) e.getSource();
        String vBoxId = temp.getId();

        OtherClassEntity otherClassEntity = new OtherClassEntity();
        otherClassEntity.showEntity(data,this,controller,temp,vBoxId);
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void createNewInterfaceEntity(ActionEvent event) {
        OtherClassEntity entity = new OtherClassEntity(new Label(),OtherClassType.INTERFACE);
        entity.initializeEntity(draggableMaker,this, data, pane);
    }

    public void createNewInterfaceEntity(OtherClassEntity otherClassEntity) {
        otherClassEntity.initializeEntity(draggableMaker,this,data,pane);
    }
    public void createNewPrimitiveEntity(ActionEvent event) {
        OtherClassEntity entity = new OtherClassEntity(new Label(),OtherClassType.PRIMITIVE);
        entity.initializeEntity(draggableMaker,this, data, pane);
    }
    public void createNewPrimitiveEntity(OtherClassEntity otherClassEntity) {
        otherClassEntity.initializeEntity(draggableMaker,this,data,pane);
    }

    public void drawAssociation(ActionEvent e) {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Association association = new Association(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty()
        );
        pane.getChildren().add(association);
        data.connectionList.add(association);
    }

    public void drawDependency(ActionEvent e) {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Dependency dependency = new Dependency(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty());

        pane.getChildren().add(dependency);
        data.connectionList.add(dependency);
    }

    public void drawInheritance() {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Inheritance inheritance = new Inheritance(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty());

        pane.getChildren().add(inheritance);
        data.connectionList.add(inheritance);
    }

    public void drawRealization() {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Realization realization = new Realization(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty());

        pane.getChildren().add(realization);
        data.connectionList.add(realization);
    }

    public void drawAggregation() {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Aggregation aggregation = new Aggregation(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty());

        pane.getChildren().add(aggregation);
        data.connectionList.add(aggregation);
    }

    public void drawComposition() {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Composition composition = new Composition(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty());

        pane.getChildren().add(composition);
        data.connectionList.add(composition);
    }
}
