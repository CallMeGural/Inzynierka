package pl.gf.umlcd;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;
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
import pl.gf.umlcd.exceptions.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

@Getter
@Setter
public class MainViewController implements Initializable {

    @FXML
    private BorderPane bPane;
    @FXML
    private Pane pane;

    private DraggableMaker draggableMaker;
    private Data data;
    private WrongConnection wrongConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draggableMaker = DraggableMaker.getInstance();//new DraggableMaker();
        wrongConnection = new WrongConnection();
        //data = new Data();
        data = Data.getInstance();
        bPane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.DELETE) {
                for(ClassEntity entity : data.getPickedPair()) {
                    pane.getChildren().remove(entity.getVBox());
                    data.getEntities().remove(entity);
                }
                if(data.getPickedConnection().size()>0)
                    pane.getChildren().remove(
                            data.getPickedConnection().get(0));
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
        data.save();
    }
    public void loadFile(ActionEvent e) {
        data.load(this);
    }


    /*
            ENTITY - CREATE SHOW
     */
    public void createNewClassEntity(ActionEvent e) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.initializeEntity(/*draggableMaker,*/this/*,data*/,pane);

    }
    public void createNewClassEntity(ClassEntity classEntity) {
        classEntity.initializeEntity(/*draggableMaker,*/this/*,data*/,pane);
    }

    public void showClassEntity(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("class-view.fxml"));
        try {
            Parent root = loader.load();
            ObjectViewController controller = loader.getController();
            VBox temp = (VBox) e.getSource();
            String vBoxId = temp.getId();

            ClassEntity classEntity;// = new ClassEntity();
            for(ClassEntity entity : data.getEntities()) {
                if(entity.getVBox().getId().equals(vBoxId)) {
                    classEntity = entity;
                    classEntity.pickEntity(/*data,*/controller,vBoxId);
                    classEntity.setChildren(/*data,*/ controller);
                    break;
                }
            }

            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException exception) {
            loadingOtherControllerError();
        }
    }

    public void createNewEnumEntity(ActionEvent e) {
        OtherClassEntity entity = new OtherClassEntity(new Label(),OtherClassType.ENUM);
        entity.initializeEntity(/*draggableMaker,*/this/*, data*/, pane);
    }
    public void createNewEnumEntity(OtherClassEntity entity) {
        entity.initializeEntity(/*draggableMaker,*/this/*,data*/,pane);
    }

    public void createNewInterfaceEntity(ActionEvent e) {
        OtherClassEntity entity = new OtherClassEntity(new Label(),OtherClassType.INTERFACE);
        entity.initializeEntity(/*draggableMaker,*/this/*, data*/, pane);
    }
    public void createNewInterfaceEntity(OtherClassEntity otherClassEntity) {
        otherClassEntity.initializeEntity(/*draggableMaker,*/this/*,data*/,pane);
    }

    /*public void createNewPrimitiveEntity(ActionEvent e) {
        OtherClassEntity entity = new OtherClassEntity(new Label(),OtherClassType.PRIMITIVE);
        entity.initializeEntity(draggableMaker,this, data, pane);
    }
    public void createNewPrimitiveEntity(OtherClassEntity otherClassEntity) {
        otherClassEntity.initializeEntity(draggableMaker,this,data,pane);
    }*/

    public void showOtherEntity(MouseEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("class-view.fxml"));
        try {
            Parent root = loader.load();
            ObjectViewController controller = loader.getController();

            VBox temp = (VBox) e.getSource();
            String vBoxId = temp.getId();

            OtherClassEntity otherClassEntity;// = new OtherClassEntity();
            for(ClassEntity entity : data.getEntities()) {
                if(entity.getVBox().getId().equals(vBoxId)) {
                    otherClassEntity = (OtherClassEntity) entity;
                    otherClassEntity.pickEntity(/*data,*/controller,vBoxId);
                    otherClassEntity.setChildren(/*data,*/controller);
                    break;
                }
            }
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException exception) {
            loadingOtherControllerError();
        }
    }

    /*
            DRAW CONNECTIONS
     */

    public void drawAssociation(ActionEvent e) {
        try {
            Center startCenter = new Center(data.getPickedPair().get(0).getVBox());
            Center endCenter = new Center(data.getPickedPair().get(1).getVBox());

            wrongConnection.theSameNodesConnected(data.getPickedPair().get(0),data.getPickedPair().get(1));
            wrongConnection.associationBetweenOtherTypes(data.getPickedPair().get(0),data.getPickedPair().get(1));
            //wrongConnection.associationBetweenEnumAndOther(data.getPickedPair().get(0),data.getPickedPair().get(1));

            Association association = new Association(
                    startCenter.getCenterX(),
                    startCenter.getCenterY(),
                    endCenter.getCenterX(),
                    endCenter.getCenterY()/*,data*/, this);
            pane.getChildren().add(association);
            data.getConnectedPairs().add(new ConnectedPair(
                    data.getPickedPair().get(0).getVBox(),
                    data.getPickedPair().get(1).getVBox(),
                    association));
        } catch (IndexOutOfBoundsException exception1) {
            connectionError();
        } catch (WrongAssociationException exception2) {
            exception2.showMessage();
//        } catch (WrongEnumConnectionException exception3) {
//            exception3.showMessage();
        } catch (TheSameNodesConnectionException exception4) {
            exception4.showMessage();
        }

    }
    public void drawAssociation(String vbox1, String vbox2, String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Association association = new Association(
                startCenter.getCenterX(),
                startCenter.getCenterY(),
                endCenter.getCenterX(),
                endCenter.getCenterY()/*,data*/, this);

        pane.getChildren().add(association);
        data.getConnectedPairs().add(new ConnectedPair(vb1, vb2, association,startCardinality,endCardinality));
    }

    public void drawSelfAssociation(ActionEvent e) {
        try {
            Center startCenter = new Center(data.getPickedPair().get(0).getVBox());

            wrongConnection.associationBetweenOtherTypes(data.getPickedPair().get(0),data.getPickedPair().get(0));

            SelfAssociation association = new SelfAssociation(
                    startCenter.getCenterX(),
                    startCenter.getCenterY(),
                    startCenter.getCenterX(),
                    startCenter.getCenterY()/*,data*/, this);
            pane.getChildren().add(association);
            data.getConnectedPairs().add(new ConnectedPair(
                    data.getPickedPair().get(0).getVBox(),
                    data.getPickedPair().get(0).getVBox(),
                    association));
        } catch (IndexOutOfBoundsException exception1) {
            connectionError();
        } catch (WrongAssociationException exception2) {
            exception2.showMessage();
        }
    }
    public void drawSelfAssociation(String vbox1, String vbox2, String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        SelfAssociation association = new SelfAssociation(
                startCenter.getCenterX(),
                startCenter.getCenterY(),
                endCenter.getCenterX(),
                endCenter.getCenterY(), this);

        pane.getChildren().add(association);
        data.getConnectedPairs().add(new ConnectedPair(vb1, vb2, association,startCardinality,endCardinality));

    }

    public void drawDirectedAssociation(ActionEvent e) {
        try {
            Center startCenter = new Center(data.getPickedPair().get(0).getVBox());
            Center endCenter = new Center(data.getPickedPair().get(1).getVBox());

            wrongConnection.theSameNodesConnected(data.getPickedPair().get(0),data.getPickedPair().get(1));
            //wrongConnection.associationBetweenOtherTypes(data.getPickedPair().get(0),data.getPickedPair().get(1));
            //wrongConnection.associationBetweenEnumAndOther(data.getPickedPair().get(0),data.getPickedPair().get(1));
            wrongConnection.wrongDirectedAssociationFlow(data.getPickedPair().get(0),data.getPickedPair().get(1));

            DirectedAssociation association = new DirectedAssociation(
                    startCenter.getCenterX(),
                    startCenter.getCenterY(),
                    endCenter.getCenterX(),
                    endCenter.getCenterY()/*,data*/, this);
            pane.getChildren().add(association);
            data.getConnectedPairs().add(new ConnectedPair(
                    data.getPickedPair().get(0).getVBox(),
                    data.getPickedPair().get(1).getVBox(),
                    association));
        } catch (IndexOutOfBoundsException exception1) {
            connectionError();
        /*} catch (WrongAssociationException exception2) {
            exception2.showMessage();
        } catch (WrongEnumConnectionException exception3) {
            exception3.showMessage();*/
        } catch (WrongDirectedAssociationException exception4) {
            exception4.showMessage();
        } catch (TheSameNodesConnectionException exception5) {
            exception5.showMessage();
        }

    }
    public void drawDirectedAssociation(String vbox1, String vbox2, String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        DirectedAssociation association = new DirectedAssociation(
                startCenter.getCenterX(),
                startCenter.getCenterY(),
                endCenter.getCenterX(),
                endCenter.getCenterY()/*,data*/, this);

        pane.getChildren().add(association);
        data.getConnectedPairs().add(new ConnectedPair(vb1, vb2, association,startCardinality,endCardinality));
    }

    public void drawDependency(ActionEvent e) {
        try {
            Center startCenter = new Center(data.getPickedPair().get(0).getVBox());
            Center endCenter = new Center(data.getPickedPair().get(1).getVBox());

            wrongConnection.theSameNodesConnected(data.getPickedPair().get(0),data.getPickedPair().get(1));
            wrongConnection.dependencyBetweenNonClasses(data.getPickedPair().get(0),data.getPickedPair().get(1));

            Dependency dependency = new Dependency(
                    startCenter.getCenterX(),
                    startCenter.getCenterY(),
                    endCenter.getCenterX(),
                    endCenter.getCenterY()/*,data*/, this);

            pane.getChildren().add(dependency);
            data.getConnectedPairs().add(new ConnectedPair(
                    data.getPickedPair().get(0).getVBox(),
                    data.getPickedPair().get(1).getVBox(),
                    dependency));
        } catch (IndexOutOfBoundsException exception1) {
            connectionError();
        } catch (WrongDependencyException exception2) {
            exception2.showMessage();
        } catch (TheSameNodesConnectionException exception3) {
            exception3.showMessage();
        }

    }
    public void drawDependency(String vbox1, String vbox2, String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Dependency dependency = new Dependency(
                startCenter.getCenterX(),
                startCenter.getCenterY(),
                endCenter.getCenterX(),
                endCenter.getCenterY()/*,data*/, this);

        pane.getChildren().add(dependency);
        data.getConnectedPairs().add(new ConnectedPair(vb1, vb2, dependency,startCardinality,endCardinality));
    }

    public void drawInheritance() {
        try {
            Center startCenter = new Center(data.getPickedPair().get(0).getVBox());
            Center endCenter = new Center(data.getPickedPair().get(1).getVBox());
            wrongConnection.theSameNodesConnected(data.getPickedPair().get(0),data.getPickedPair().get(1));
            wrongConnection.inheritanceBetweenOtherTypes(data.getPickedPair().get(0),data.getPickedPair().get(1));
            Inheritance inheritance = new Inheritance(
                    startCenter.getCenterX(),
                    startCenter.getCenterY(),
                    endCenter.getCenterX(),
                    endCenter.getCenterY()/*,data*/, this);
            pane.getChildren().add(inheritance);
            data.getConnectedPairs().add(new ConnectedPair(
                    data.getPickedPair().get(0).getVBox(),
                    data.getPickedPair().get(1).getVBox(),
                    inheritance));
        } catch (IndexOutOfBoundsException exception1) {
            connectionError();
        } catch (WrongInheritanceException exception2) {
            exception2.showMessage();
        } catch (TheSameNodesConnectionException exception3) {
            exception3.showMessage();
        }

    }
    public void drawInheritance(String vbox1, String vbox2,String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Inheritance inheritance = new Inheritance(
                startCenter.getCenterX(),
                startCenter.getCenterY(),
                endCenter.getCenterX(),
                endCenter.getCenterY()/*,data*/, this);

        pane.getChildren().add(inheritance);
        data.getConnectedPairs().add(new ConnectedPair(vb1, vb2, inheritance,startCardinality,endCardinality));
    }

    public void drawRealization() {
        try {
            Center startCenter = new Center(data.getPickedPair().get(0).getVBox());
            Center endCenter = new Center(data.getPickedPair().get(1).getVBox());
            wrongConnection.theSameNodesConnected(data.getPickedPair().get(0),data.getPickedPair().get(1));
            wrongConnection.wrongRealization(data.getPickedPair().get(0),data.getPickedPair().get(1));

            Realization realization = new Realization(
                    startCenter.getCenterX(),
                    startCenter.getCenterY(),
                    endCenter.getCenterX(),
                    endCenter.getCenterY(),this);

            pane.getChildren().add(realization);
            data.getConnectedPairs().add(new ConnectedPair(
                    data.getPickedPair().get(0).getVBox(),
                    data.getPickedPair().get(1).getVBox(),
                    realization));
        } catch (IndexOutOfBoundsException exception1) {
            connectionError();
        } catch (WrongRealizationException exception2) {
            exception2.showMessage();
        } catch (TheSameNodesConnectionException exception3) {
            exception3.showMessage();
        }

    }
    public void drawRealization(String vbox1, String vbox2,String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Realization realization = new Realization(
                startCenter.getCenterX(),
                startCenter.getCenterY(),
                endCenter.getCenterX(),
                endCenter.getCenterY()/*,data*/, this);

        pane.getChildren().add(realization);
        data.getConnectedPairs().add(new ConnectedPair(vb1, vb2, realization,startCardinality,endCardinality));
    }

    public void drawAggregation() {
        try {
            Center startCenter = new Center(data.getPickedPair().get(0).getVBox());
            Center endCenter = new Center(data.getPickedPair().get(1).getVBox());

            wrongConnection.theSameNodesConnected(data.getPickedPair().get(0),data.getPickedPair().get(1));
            wrongConnection.wrongAggregationOrComposition(data.getPickedPair().get(0),data.getPickedPair().get(1));

            Aggregation aggregation = new Aggregation(
                    startCenter.getCenterX(),
                    startCenter.getCenterY(),
                    endCenter.getCenterX(),
                    endCenter.getCenterY()/*,data*/, this);

            pane.getChildren().add(aggregation);
            data.getConnectedPairs().add(new ConnectedPair(
                    data.getPickedPair().get(0).getVBox(),
                    data.getPickedPair().get(1).getVBox(),
                    aggregation));
        }
        catch (IndexOutOfBoundsException exception1) {
            connectionError();
        } catch (WrongDirectedAssociationException exception3) {
            exception3.showMessage();
        } catch (TheSameNodesConnectionException exception5) {
            exception5.showMessage();
        }

    }
    public void drawAggregation(String vbox1, String vbox2,String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Aggregation aggregation = new Aggregation(
                startCenter.getCenterX(),
                startCenter.getCenterY(),
                endCenter.getCenterX(),
                endCenter.getCenterY()/*,data*/, this);

        pane.getChildren().add(aggregation);
        data.getConnectedPairs().add(new ConnectedPair(vb1, vb2, aggregation,startCardinality,endCardinality));
    }

    public void drawComposition() {
        try {
            Center startCenter = new Center(data.getPickedPair().get(0).getVBox());
            Center endCenter = new Center(data.getPickedPair().get(1).getVBox());

            wrongConnection.theSameNodesConnected(data.getPickedPair().get(0),data.getPickedPair().get(1));
            wrongConnection.wrongAggregationOrComposition(data.getPickedPair().get(0),data.getPickedPair().get(1));

            Composition composition = new Composition(
                    startCenter.getCenterX(),
                    startCenter.getCenterY(),
                    endCenter.getCenterX(),
                    endCenter.getCenterY()/*,data*/, this);

            pane.getChildren().add(composition);
            data.getConnectedPairs().add(new ConnectedPair(
                    data.getPickedPair().get(0).getVBox(),
                    data.getPickedPair().get(1).getVBox(),
                    composition));
        } catch (IndexOutOfBoundsException exception1) {
            connectionError();
        } catch (WrongDirectedAssociationException exception3) {
            exception3.showMessage();
        } catch (TheSameNodesConnectionException exception5) {
            exception5.showMessage();
        }

    }
    public void drawComposition(String vbox1, String vbox2,String startCardinality, String endCardinality) {
        VBox vb1 = getVBox(vbox1);
        VBox vb2 = getVBox(vbox2);

        Center startCenter = new Center(vb1);
        Center endCenter = new Center(vb2);
        Composition composition = new Composition(
                startCenter.getCenterX(),
                startCenter.getCenterY(),
                endCenter.getCenterX(),
                endCenter.getCenterY()/*,data*/, this);

        pane.getChildren().add(composition);
        data.getConnectedPairs().add(new ConnectedPair(vb1, vb2, composition,startCardinality,endCardinality));
    }

    private VBox getVBox(String vbox) {
        VBox vb = new VBox();
        for (int i = 0; i < data.getEntities().size(); i++) {
            if (data.getEntities().get(i).getVBox().getId().equals(vbox)) {
                vb = data.getEntities().get(i).getVBox();
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
                    association.showConnection(/*data,*/connectionId,controller);
                }
                case "Aggregation" -> {
                    Aggregation aggregation = (Aggregation) e.getSource();
                    connectionId = aggregation.getId();
                    aggregation.showConnection(/*data,*/connectionId,controller);
                }
                case "Composition" -> {
                    Composition composition = (Composition) e.getSource();
                    connectionId = composition.getId();
                    composition.showConnection(/*data,*/connectionId,controller);
                }
                case "Dependency" -> {
                    Dependency dependency = (Dependency) e.getSource();
                    connectionId = dependency.getId();
                    dependency.showConnection(/*data,*/connectionId,controller);
                }
                case "Inheritance" -> {
                    Inheritance inheritance = (Inheritance) e.getSource();
                    connectionId = inheritance.getId();
                    inheritance.showConnection(/*data,*/connectionId,controller);
                }
                case "Realization" -> {
                    Realization realization = (Realization) e.getSource();
                    connectionId = realization.getId();
                    realization.showConnection(/*data,*/connectionId,controller);
                }
            }
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException exception) {
            loadingOtherControllerError();
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
    private void loadingOtherControllerError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Controller error");
        alert.setHeaderText("Could not read other controller");
        alert.setContentText("Trying to read the FXML file that does not exist");
        alert.showAndWait();
    }

}
