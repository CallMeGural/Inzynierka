package pl.gf.umlcd;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import pl.gf.umlcd.connections.*;
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
import java.util.NoSuchElementException;
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
        //data = new Data();
        data = Data.getInstance();
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
    public void makeScreenShot(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PNG (*.png)","*.png");
        fileChooser.getExtensionFilters().add(filter);
        Stage stage = (Stage) pane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if(file!=null) {
            try {
                WritableImage image = stage.getScene().snapshot(null);
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (NoSuchElementException exception) {
                screenshotError();
            } catch (IOException exception) {
                screenshotIOError();
            }
        }
        /*try {
            Stage stage = (Stage) pane.getScene().getWindow();
            WritableImage image = stage.getScene().snapshot(null);
            String filename = saveData.setFileName();
            File file = new File(filename+".png");

            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (NoSuchElementException exception) {
            screenshotError();
        } catch (IOException exception) {
            screenshotIOError();
        }*/
    }

    public void saveFile(ActionEvent e) {
        saveData.save(this);
    }
    public void loadFile(ActionEvent e) {
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

    public void showClassEntity(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("class-view.fxml"));
        try {
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
        } catch(IOException exception) {
            loadingOtherControllerException();
        }
    }

    public void createNewEnumEntity(ActionEvent e) {
        OtherClassEntity entity = new OtherClassEntity(new Label(),OtherClassType.ENUM);
        entity.initializeEntity(draggableMaker,this, data, pane);
    }
    public void createNewEnumEntity(OtherClassEntity entity) {
        entity.initializeEntity(draggableMaker,this,data,pane);
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

    public void showOtherEntity(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("class-view.fxml"));
        try {
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
        } catch(IOException exception) {
            loadingOtherControllerException();
        }
    }

    /*
            DRAW CONNECTIONS
     */

    public void drawAssociation(ActionEvent e) {
        try {
            Center startCenter = new Center(data.pickedPair.get(0).getVbox());
            Center endCenter = new Center(data.pickedPair.get(1).getVbox());
            Association association = new Association(
                    startCenter.centerXProperty(),
                    startCenter.centerYProperty(),
                    endCenter.centerXProperty(),
                    endCenter.centerYProperty(),data, this);
            pane.getChildren().add(association);
            data.connectedPairs.add(new ConnectedPair(
                    data.pickedPair.get(0).getVbox(),
                    data.pickedPair.get(1).getVbox(),
                    association));
        } catch (IndexOutOfBoundsException exception) {
            connectionError();
        }

    }
    public void drawAssociation(String vbox1, String vbox2, String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Association association = new Association(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data, this);

        pane.getChildren().add(association);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, association,startCardinality,endCardinality));
    }

    public void drawDependency(ActionEvent e) {
        try {
            Center startCenter = new Center(data.pickedPair.get(0).getVbox());
            Center endCenter = new Center(data.pickedPair.get(1).getVbox());
            Dependency dependency = new Dependency(
                    startCenter.centerXProperty(),
                    startCenter.centerYProperty(),
                    endCenter.centerXProperty(),
                    endCenter.centerYProperty(),data,this);

            pane.getChildren().add(dependency);
            data.connectedPairs.add(new ConnectedPair(
                    data.pickedPair.get(0).getVbox(),
                    data.pickedPair.get(1).getVbox(),
                    dependency));
        } catch (IndexOutOfBoundsException exception) {
            connectionError();
        }

    }
    public void drawDependency(String vbox1, String vbox2, String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Dependency dependency = new Dependency(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data,this);

        pane.getChildren().add(dependency);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, dependency,startCardinality,endCardinality));
    }

    public void drawInheritance() {
        try {
            Center startCenter = new Center(data.pickedPair.get(0).getVbox());
            Center endCenter = new Center(data.pickedPair.get(1).getVbox());
            Inheritance inheritance = new Inheritance(
                    startCenter.centerXProperty(),
                    startCenter.centerYProperty(),
                    endCenter.centerXProperty(),
                    endCenter.centerYProperty(),data,this);

            pane.getChildren().add(inheritance);
            data.connectedPairs.add(new ConnectedPair(
                    data.pickedPair.get(0).getVbox(),
                    data.pickedPair.get(1).getVbox(),
                    inheritance));
        } catch (IndexOutOfBoundsException exception) {
            connectionError();
        }

    }
    public void drawInheritance(String vbox1, String vbox2,String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Inheritance inheritance = new Inheritance(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data,this);

        pane.getChildren().add(inheritance);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, inheritance,startCardinality,endCardinality));
    }

    public void drawRealization() {
        try {
            Center startCenter = new Center(data.pickedPair.get(0).getVbox());
            Center endCenter = new Center(data.pickedPair.get(1).getVbox());
            Realization realization = new Realization(
                    startCenter.centerXProperty(),
                    startCenter.centerYProperty(),
                    endCenter.centerXProperty(),
                    endCenter.centerYProperty(),data,this);

            pane.getChildren().add(realization);
            data.connectedPairs.add(new ConnectedPair(
                    data.pickedPair.get(0).getVbox(),
                    data.pickedPair.get(1).getVbox(),
                    realization));
        } catch (IndexOutOfBoundsException exception) {
            connectionError();
        }

    }
    public void drawRealization(String vbox1, String vbox2,String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Realization realization = new Realization(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data,this);

        pane.getChildren().add(realization);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, realization,startCardinality,endCardinality));
    }

    public void drawAggregation() {
        try {
            Center startCenter = new Center(data.pickedPair.get(0).getVbox());
            Center endCenter = new Center(data.pickedPair.get(1).getVbox());
            Aggregation aggregation = new Aggregation(
                    startCenter.centerXProperty(),
                    startCenter.centerYProperty(),
                    endCenter.centerXProperty(),
                    endCenter.centerYProperty(),data,this);

            pane.getChildren().add(aggregation);
            data.connectedPairs.add(new ConnectedPair(
                    data.pickedPair.get(0).getVbox(),
                    data.pickedPair.get(1).getVbox(),
                    aggregation));
        }
        catch (IndexOutOfBoundsException exception) {
            connectionError();
        }

    }
    public void drawAggregation(String vbox1, String vbox2,String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Aggregation aggregation = new Aggregation(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data,this);

        pane.getChildren().add(aggregation);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, aggregation,startCardinality,endCardinality));
    }

    public void drawComposition() {
        try {
            Center startCenter = new Center(data.pickedPair.get(0).getVbox());
            Center endCenter = new Center(data.pickedPair.get(1).getVbox());
            Composition composition = new Composition(
                    startCenter.centerXProperty(),
                    startCenter.centerYProperty(),
                    endCenter.centerXProperty(),
                    endCenter.centerYProperty(),data,this);

            pane.getChildren().add(composition);
            data.connectedPairs.add(new ConnectedPair(
                    data.pickedPair.get(0).getVbox(),
                    data.pickedPair.get(1).getVbox(),
                    composition));
        } catch (IndexOutOfBoundsException exception) {
            connectionError();
        }

    }
    public void drawComposition(String vbox1, String vbox2,String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Composition composition = new Composition(
                startCenter.centerXProperty(),
                startCenter.centerYProperty(),
                endCenter.centerXProperty(),
                endCenter.centerYProperty(),data, this);

        pane.getChildren().add(composition);
        data.connectedPairs.add(new ConnectedPair(vb1, vb2, composition,startCardinality,endCardinality));
    }

    private VBox getVBox(String vbox) {
        VBox vb = new VBox();
        for (int i = 0; i < data.entityList.size(); i++) {
            if (data.entityList.get(i).getVbox().getId().equals(vbox)) {
                vb = data.entityList.get(i).getVbox();
            }
        }
        return vb;
    }

    public void showConnection(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("connection-view.fxml"));
        try {
            Parent root = loader.load();
            ConnectionViewController controller = loader.getController();
            String connectionId;
            switch (e.getSource().getClass().getSimpleName()) {
                case "Association" -> {
                    Association association = (Association) e.getSource();
                    connectionId = association.getId();
                    association.showConnection(data,connectionId,controller);
                }
                case "Aggregation" -> {
                    Aggregation aggregation = (Aggregation) e.getSource();
                    connectionId = aggregation.getId();
                    aggregation.showConnection(data,connectionId,controller);
                }
                case "Composition" -> {
                    Composition composition = (Composition) e.getSource();
                    connectionId = composition.getId();
                    composition.showConnection(data,connectionId,controller);
                }
                case "Dependency" -> {
                    Dependency dependency = (Dependency) e.getSource();
                    connectionId = dependency.getId();
                    dependency.showConnection(data,connectionId,controller);
                }
                case "Inheritance" -> {
                    Inheritance inheritance = (Inheritance) e.getSource();
                    connectionId = inheritance.getId();
                    inheritance.showConnection(data,connectionId,controller);
                }
                case "Realization" -> {
                    Realization realization = (Realization) e.getSource();
                    connectionId = realization.getId();
                    realization.showConnection(data,connectionId,controller);
                }
            }



            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException exception) {
            loadingOtherControllerException();
        }
    }

    private void connectionError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Classes connection error");
        alert.setHeaderText("Could not connect classes");
        alert.setContentText("You need to choose two classes to be connected");
        alert.showAndWait();
    }
    private void screenshotError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Screenshot error");
        alert.setHeaderText("Could not make screenshot");
        alert.setContentText("File was not created");
        alert.showAndWait();
    }
    private void screenshotIOError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Screenshot error");
        alert.setHeaderText("Could not make screenshot");
        alert.setContentText("File could not be saved");
        alert.showAndWait();
    }
    private void loadingOtherControllerException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Controller error");
        alert.setHeaderText("Could not read other controller");
        alert.setContentText("Trying to read the FXML file that does not exist");
        alert.showAndWait();
    }

}
