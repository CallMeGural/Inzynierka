package pl.gf.umlcd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.ini4j.Ini;
import org.ini4j.Wini;
import org.w3c.dom.Text;
import pl.gf.umlcd.connections.Association;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Getter
@Setter
public final class Data {

    private static Data instance;
    private ObservableList<ClassEntity> entities;
    private ObservableList<Association> connections;
    private ObservableList<ClassEntity> pickedPair;
    private ObservableList<ConnectedPair> connectedPairs;
    private ObservableList<Association>/*Association*/ pickedConnection;
    private Label label;
    private TextArea textArea;
    private int classCounter;
    private int connectionCounter;

    private Data() {
        entities = FXCollections.observableArrayList();
        connections = FXCollections.observableArrayList();
        pickedPair = FXCollections.observableArrayList();
        connectedPairs = FXCollections.observableArrayList();
        label = new Label();
        textArea = new TextArea();
        pickedConnection = FXCollections.observableArrayList();//new Association();
        classCounter=0;
        connectionCounter=0;
    }
    public static Data getInstance() {
        if(instance == null) instance = new Data();
        return instance;
    }

    public String setFileName() {
        TextInputDialog dialog = new TextInputDialog("file");
        dialog.setTitle("Save File");
        dialog.setHeaderText("Set filename");
        dialog.setContentText("Enter filename:");
        return dialog.showAndWait().orElseThrow();
    }

    private void saveClassEntity(VBox vBox, Ini ini) {
        TextArea temp = new TextArea();
        ini.put("class"+classCounter,"id",vBox.getId());

        label = (Label) vBox.getChildren().get(0); //name
        ini.put("class" + classCounter, "name", label.getText());

        ini.put("class" + classCounter, "layoutX", vBox.getLayoutX());
        ini.put("class" + classCounter, "layoutY", vBox.getLayoutY());

        textArea = (TextArea) vBox.getChildren().get(2); //vars
        temp.setText(textArea.getText());
        temp.setText(temp.getText().replace('\n','`'));
        ini.put("class" + classCounter, "vars", temp.getText());

        textArea = (TextArea) vBox.getChildren().get(4); //,methods
        temp.setText(textArea.getText());
        temp.setText(temp.getText().replace('\n','`'));
        ini.put("class" + classCounter, "meths", temp.getText());

        classCounter++;
    }

    private void saveOtherClassEntity(VBox vBox, Ini ini) {
        TextArea temp = new TextArea();
        ini.put("class"+classCounter,"id",vBox.getId());

        label = (Label) vBox.getChildren().get(0); //type
        //ini.put("other" + otherCounter, "type", label.getText());
        ini.put("class" + classCounter, "type", label.getText());
        label = (Label) vBox.getChildren().get(1); //name
        //ini.put("other" + otherCounter, "name", label.getText());
        ini.put("class" + classCounter, "name", label.getText());

//        ini.put("other" + otherCounter, "layoutX", vBox.getLayoutX());
//        ini.put("other" + otherCounter, "layoutY", vBox.getLayoutY());
        ini.put("class" + classCounter, "layoutX", vBox.getLayoutX());
        ini.put("class" + classCounter, "layoutY", vBox.getLayoutY());

        textArea = (TextArea) vBox.getChildren().get(3); //vars
        temp.setText(textArea.getText());
        temp.setText(temp.getText().replace('\n','`'));
        ini.put("class" + classCounter, "vars", temp.getText());

        textArea = (TextArea) vBox.getChildren().get(5); //methods
        temp.setText(textArea.getText());
        temp.setText(temp.getText().replace('\n','`'));
        ini.put("class" + classCounter, "vars", temp.getText());
        classCounter++;
    }

    private void saveConnection(ConnectedPair pair, Ini ini) {
        ini.put("connection" + connectionCounter, "start", pair.getVBox1().getVBox().getId());
        ini.put("connection" + connectionCounter, "end", pair.getVBox2().getVBox().getId());
        ini.put("connection" + connectionCounter, "type", pair.getConnection().getClass().getSimpleName());
        ini.put("connection" + connectionCounter, "startCardinality", pair.getCardinality1());
        ini.put("connection" + connectionCounter, "endCardinality", pair.getCardinality2());
        connectionCounter++;
    }

    public void save() {
        classCounter=0;
        connectionCounter=0;
        VBox vBox;
        String filename = setFileName() + ".ini";
        Path path = Paths.get(filename);
        try {
            Files.createFile(path);
            File file = new File(String.valueOf(path));
            Wini ini = new Wini(file);

            for (ClassEntity classEntity : entities) {
                vBox = classEntity.getVBox();
                if (vBox.getChildren().size() == 5)  //ClassEntity
                    saveClassEntity(vBox, ini);
                else //OtherClassEntity
                    saveOtherClassEntity(vBox, ini);
                //classCounter++;
            }

            for (ConnectedPair pair : connectedPairs) {
                if(pair.isPresent(pair.getVBox1()) && pair.isPresent(pair.getVBox2()) && pair.isPresent(pair.getConnection()))
                    saveConnection(pair, ini);
                //connectionCounter++;
            }
            ini.put("config", "classCounter", classCounter);
            ini.put("config", "connectionCounter", connectionCounter);
            ini.store();
        } catch (IOException exception) {
            saveFileIO();
        }
    }

    private void loadClassEntity(Ini.Section section, ClassEntity entity, MainViewController controller) {
        //Ini.Section section = ini.get("class"+i);
        entity.getMeths().setText(section.get("meths"));
        if(entity.getMeths().getText()!=null) entity.getMeths().setText(entity.getMeths().getText().replace('`','\n'));

        entity.getVars().setText(section.get("vars"));
        if(entity.getVars().getText()!=null) entity.getVars().setText(entity.getVars().getText().replace('`','\n'));

        entity.getName().setText(section.get("name"));

        entity.getVBox().setLayoutX(Double.parseDouble(section.get("layoutX")));
        entity.getVBox().setLayoutY(Double.parseDouble(section.get("layoutY")));
        controller.createNewClassEntity(entity);
    }

    private void loadOtherClassEntity(Ini.Section section, OtherClassEntity otherEntity, MainViewController controller) {
        otherEntity.getMeths().setText(section.get("meths"));
        if(otherEntity.getMeths().getText()!=null) otherEntity.getMeths().setText(otherEntity.getMeths().getText().replace('`','\n'));

        otherEntity.getVars().setText(section.get("vars"));
        if(otherEntity.getVars().getText()!=null) otherEntity.getVars().setText(otherEntity.getVars().getText().replace('`','\n'));

        otherEntity.getName().setText(section.get("name"));
        otherEntity.getTitle().setText(section.get("type"));

        otherEntity.getVBox().setLayoutX(Double.parseDouble(section.get("layoutX")));
        otherEntity.getVBox().setLayoutY(Double.parseDouble(section.get("layoutY")));
        switch (section.get("type")) {
            case "<<enum>>" -> controller.createNewEnumEntity(otherEntity);
            //case "<<primitive>>" -> controller.createNewPrimitiveEntity(otherEntity);
            case "<<interface>>" -> controller.createNewInterfaceEntity(otherEntity);
        }
    }

    private void loadConnection(int i, Ini ini, MainViewController controller) {
        //String path = "";
        Ini.Section section = ini.get("connection"+i);
        switch (section.get("type")) {
            case "Aggregation" ->
                    controller.drawAggregation(section.get("start"),section.get("end"),
                            section.get("startCardinality"), section.get("endCardinality"));
            case "Association" ->
                    controller.drawAssociation(section.get("start"),section.get("end"),
                            section.get("startCardinality"),section.get("endCardinality"));
            case "DirectedAssociation" ->
                    controller.drawDirectedAssociation(section.get("start"),section.get("end"),
                            section.get("startCardinality"),section.get("endCardinality"));
            case "SelfAssociation" ->
                    controller.drawSelfAssociation(section.get("start"),section.get("end"),
                            section.get("startCardinality"),section.get("endCardinality"));
            case "Composition" ->
                    controller.drawComposition(section.get("start"),section.get("end"),
                            section.get("startCardinality"),section.get("endCardinality"));
            case "Dependency" ->
                    controller.drawDependency(section.get("start"),section.get("end"),
                            section.get("startCardinality"),section.get("endCardinality"));
            case "Inheritance" ->
                    controller.drawInheritance(section.get("start"),section.get("end"),
                            section.get("startCardinality"),section.get("endCardinality"));
            case "Realization" ->
                    controller.drawRealization(section.get("start"),section.get("end"),
                            section.get("startCardinality"),section.get("endCardinality"));
            default -> wrongConnectionType();
        }
    }

    public void load(MainViewController controller) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Ini file (*.ini)","*.ini");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(new Stage());

        if(file!=null) {
            pickedPair.clear();
            entities.clear();
            connectedPairs.clear();
            controller.getPane().getChildren().clear();
            OtherClassEntity.counter=0;

            Ini ini = new Ini();
            Ini.Section section;
            try {
                ini.load(new FileReader(file));
                section = ini.get("config");
                classCounter = Integer.parseInt(section.get("classCounter"));
                connectionCounter = Integer.parseInt(section.get("connectionCounter"));
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                fileNotFound();
            }
            for(int i=0;i<classCounter;i++) {
                section = ini.get("class"+i);
                if(section.get("id").contains("class")) loadClassEntity(section, new ClassEntity(),controller);
                else if(section.get("id").contains("other")) loadOtherClassEntity(section,new OtherClassEntity(new Label()),controller);
            }
            for(int i=0;i<connectionCounter;i++) {
                loadConnection(i,ini,controller);
            }
        }
        else fileNotPicked();
    }

    private void saveFileIO() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File error");
        alert.setHeaderText("Could not save the file");
        alert.setContentText("File was not created or data has not been stored");
        alert.showAndWait();
    }
    private void fileNotFound() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File error");
        alert.setHeaderText("Could not read the file");
        alert.setContentText("File was not found, could not read the data");
        alert.showAndWait();
    }
    private void wrongConnectionType() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File error");
        alert.setHeaderText("Wrong Connection Class name");
        alert.setContentText("Connection class name could not be read properly");
    }
    private void fileNotPicked() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File error");
        alert.setHeaderText("No file to be opened");
        alert.setContentText("Cannot load file. File was not chosen or does not exist");
    }
}

