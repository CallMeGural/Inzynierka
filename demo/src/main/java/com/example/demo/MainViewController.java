package com.example.demo;

import com.example.demo.connections.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    @FXML
    public BorderPane bPane;
    @FXML
    public Pane pane;
    @FXML
    public MenuItem loadButton, saveButton, screenShotButton;


    DraggableMaker draggableMaker;// = new DraggableMaker();
    Data data;// = new Data();
    SaveData saveData;// = new SaveData();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draggableMaker = new DraggableMaker();
        data = new Data();
        saveData = new SaveData();
        bPane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.DELETE) {
                for(ClassEntity entity : data.pickedPair) {
                    pane.getChildren().remove(entity.getVbox());
                    data.entityList.remove(entity);
                }
                pane.getChildren().remove(data.pickedConnection);
            }

        });
    }

    /*
            MENU OPTIONS
     */
    public void makeScreenShot(ActionEvent e) throws IOException {
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


    /*
            ENTITY - CREATE SHOW
     */

    public void createNewClassEntity(ActionEvent e) {
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

    public void createNewEnumEntity(ActionEvent e) {
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

    public void createNewInterfaceEntity(ActionEvent e) {
        OtherClassEntity entity = new OtherClassEntity(new Label(),OtherClassType.INTERFACE);
        entity.initializeEntity(draggableMaker,this, data, pane);
    }

    public void createNewInterfaceEntity(OtherClassEntity otherClassEntity) {
        otherClassEntity.initializeEntity(draggableMaker,this,data,pane);
    }
    public void createNewPrimitiveEntity(ActionEvent e) {
        OtherClassEntity entity = new OtherClassEntity(new Label(),OtherClassType.PRIMITIVE);
        entity.initializeEntity(draggableMaker,this, data, pane);
    }
    public void createNewPrimitiveEntity(OtherClassEntity otherClassEntity) {
        otherClassEntity.initializeEntity(draggableMaker,this,data,pane);
    }


    /*
            DRAW CONNECTIONS
     */
    public void drawAssociation(ActionEvent e) {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Association association = new Association(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);
        pane.getChildren().add(association);
        data.connectedPairs.add(new ConnectedPair(
                data.pickedPair.get(0).getVbox(),
                data.pickedPair.get(1).getVbox(),
                association));
    }
    public void drawAssociation(String vbox1, String vbox2) {
        VBox vb1 = getVBoxes(vbox1,vbox2)[0];
        VBox vb2 = getVBoxes(vbox1,vbox2)[1];

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Association association = new Association(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(association);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, association));
    }

    public void drawDependency(ActionEvent e) {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Dependency dependency = new Dependency(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(dependency);
        //data.connectionList.add(dependency);
        data.connectedPairs.add(new ConnectedPair(
                data.pickedPair.get(0).getVbox(),
                data.pickedPair.get(1).getVbox(),
                dependency));
    }
    public void drawDependency(String vbox1, String vbox2) {
        VBox vb1 = getVBoxes(vbox1,vbox2)[0];
        VBox vb2 = getVBoxes(vbox1,vbox2)[1];

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Dependency dependency = new Dependency(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(dependency);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, dependency));
    }

    public void drawInheritance() {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Inheritance inheritance = new Inheritance(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(inheritance);
        //data.connectionList.add(inheritance);
        data.connectedPairs.add(new ConnectedPair(
                data.pickedPair.get(0).getVbox(),
                data.pickedPair.get(1).getVbox(),
                inheritance));
    }
    public void drawInheritance(String vbox1, String vbox2) {
        VBox vb1 = getVBoxes(vbox1,vbox2)[0];
        VBox vb2 = getVBoxes(vbox1,vbox2)[1];

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Inheritance inheritance = new Inheritance(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(inheritance);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, inheritance));
    }

    public void drawRealization() {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Realization realization = new Realization(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(realization);
        //data.connectionList.add(realization);
        data.connectedPairs.add(new ConnectedPair(
                data.pickedPair.get(0).getVbox(),
                data.pickedPair.get(1).getVbox(),
                realization));
    }
    public void drawRealization(String vbox1, String vbox2) {
        VBox vb1 = getVBoxes(vbox1,vbox2)[0];
        VBox vb2 = getVBoxes(vbox1,vbox2)[1];

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Realization realization = new Realization(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(realization);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, realization));
    }

    public void drawAggregation() {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Aggregation aggregation = new Aggregation(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(aggregation);
        //data.connectionList.add(aggregation);
        data.connectedPairs.add(new ConnectedPair(
                data.pickedPair.get(0).getVbox(),
                data.pickedPair.get(1).getVbox(),
                aggregation));
    }
    public void drawAggregation(String vbox1, String vbox2) {
        VBox vb1 = getVBoxes(vbox1,vbox2)[0];
        VBox vb2 = getVBoxes(vbox1,vbox2)[1];

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Aggregation aggregation = new Aggregation(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(aggregation);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, aggregation));
    }

    public void drawComposition() {
        Center startCenter = new Center(data.pickedPair.get(0).getVbox());
        Center endCenter = new Center(data.pickedPair.get(1).getVbox());
        Composition composition = new Composition(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(composition);
        //data.connectionList.add(composition);
        data.connectedPairs.add(new ConnectedPair(
                data.pickedPair.get(0).getVbox(),
                data.pickedPair.get(1).getVbox(),
                composition));
    }
    public void drawComposition(String vbox1, String vbox2) {
        VBox vb1 = getVBoxes(vbox1,vbox2)[0];
        VBox vb2 = getVBoxes(vbox1,vbox2)[1];

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Composition composition = new Composition(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data);

        pane.getChildren().add(composition);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, composition));
    }

    public VBox[] getVBoxes(String vbox1,String vbox2) {
        VBox vb1 = new VBox();
        VBox vb2 = new VBox();
        for (int i = 0; i < data.entityList.size(); i++) {
            if (data.entityList.get(i).getVbox().getId().equals(vbox1)) {
                vb1 = data.entityList.get(i).getVbox();
            }
            if (data.entityList.get(i).getVbox().getId().equals(vbox2)) {
                vb2 = data.entityList.get(i).getVbox();
            }
        }
        return new VBox[]{vb1,vb2};
    }

}
