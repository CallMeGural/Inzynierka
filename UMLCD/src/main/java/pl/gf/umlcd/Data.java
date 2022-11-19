package pl.gf.umlcd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Getter
@Setter
public final class Data {

    private static Data instance;
    public ObservableList<ClassEntity> entityList = FXCollections.observableArrayList();
    public ObservableList<ClassEntity> pickedPair = FXCollections.observableArrayList();
    public ObservableList<ConnectedPair> connectedPairs = FXCollections.observableArrayList();
    public Group pickedConnection;

    //public Data() {}

    private Data() {}

    public static Data getInstance() {
        if(instance == null) instance = new Data();
        return instance;
    }

    private Label label;
    private TextArea textArea;
    private int classCounter = 0;
    private int connectionCounter = 0;

    public String setFileName() {
        TextInputDialog dialog = new TextInputDialog("file");
        dialog.setTitle("Save File");
        dialog.setHeaderText("Set filename");
        dialog.setContentText("Enter filename:");
        return dialog.showAndWait().orElseThrow();
    }

    public void saveClassEntity(VBox vBox, Ini ini) {
        ini.put("class"+classCounter,"id",vBox.getId());

        label = (Label) vBox.getChildren().get(0); //name
        ini.put("class" + classCounter, "name", label.getText());

        ini.put("class" + classCounter, "layoutX", vBox.getLayoutX());
        ini.put("class" + classCounter, "layoutY", vBox.getLayoutY());

        textArea = (TextArea) vBox.getChildren().get(2); //vars
        ini.put("class" + classCounter, "vars", textArea.getText());

        textArea = (TextArea) vBox.getChildren().get(4); //vars
        ini.put("class" + classCounter, "vars", textArea.getText());
        classCounter++;
    }

    public void saveOtherClassEntity(VBox vBox, Ini ini) {
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
        //ini.put("other" + otherCounter, "vars", textArea.getText());
        ini.put("class" + classCounter, "vars", textArea.getText());

        textArea = (TextArea) vBox.getChildren().get(5); //methods
        //ini.put("other" + otherCounter, "meths", textArea.getText());
        ini.put("class" + classCounter, "meths", textArea.getText());
        classCounter++;
    }

    public void saveConnection(ConnectedPair pair, Ini ini) {
        ini.put("connection" + connectionCounter, "start", pair.getVBox1().getId());
        ini.put("connection" + connectionCounter, "end", pair.getVBox2().getId());
        ini.put("connection" + connectionCounter, "type", pair.getConnection().getClass().getSimpleName());
        ini.put("connection" + connectionCounter, "startCardinality", pair.getCardinality1());
        ini.put("connection" + connectionCounter, "endCardinality", pair.getCardinality2());
        connectionCounter++;
    }

    public void save(MainViewController controller) {
        classCounter=0;
        connectionCounter=0;
        VBox vBox;
        String filename = setFileName() + ".ini";
        Path path = Paths.get("src/main/resources/"+filename);
        try {
            Files.createFile(path);
            File file = new File(String.valueOf(path));
            Wini ini = new Wini(file);

            for (int i=0;i<controller.data.entityList.size();i++) {
                vBox = controller.data.entityList.get(i).getVbox();
                if (vBox.getChildren().size() == 5)  //ClassEntity
                    saveClassEntity(vBox, ini);
                else //OtherClassEntity
                    saveOtherClassEntity(vBox, ini);
                //classCounter++;
            }
            for (ConnectedPair pair : controller.data.connectedPairs) {
                saveConnection(pair, ini);
                //connectionCounter++;
            }
            ini.put("config", "classCounter", classCounter);
            ini.put("config", "connectionCounter", connectionCounter);
            ini.store();
        } catch (IOException exception) {
            saveFileIOException();
        }
    }

    private void loadClassEntity(Ini.Section section, ClassEntity entity, MainViewController controller) {
        //Ini.Section section = ini.get("class"+i);
        entity.getMeths().setText(section.get("meths"));
        entity.getVars().setText(section.get("vars"));

        entity.getName().setText(section.get("name"));

        entity.getVbox().setLayoutX(Double.parseDouble(section.get("layoutX")));
        entity.getVbox().setLayoutY(Double.parseDouble(section.get("layoutY")));
        controller.createNewClassEntity(entity);
    }

    private void loadOtherClassEntity(Ini.Section section, OtherClassEntity otherEntity, MainViewController controller) {
        otherEntity.getMeths().setText(section.get("meths"));
        otherEntity.getVars().setText(section.get("vars"));

        otherEntity.getName().setText(section.get("name"));
        otherEntity.getTitle().setText(section.get("type"));

        otherEntity.getVbox().setLayoutX(Double.parseDouble(section.get("layoutX")));
        otherEntity.getVbox().setLayoutY(Double.parseDouble(section.get("layoutY")));
        switch (section.get("type")) {
            case "<<enum>>" -> controller.createNewEnumEntity(otherEntity);
            //case "<<primitive>>" -> controller.createNewPrimitiveEntity(otherEntity);
            case "<<interface>>" -> controller.createNewInterfaceEntity(otherEntity);
        }
    }

    private void loadConnection(int i, Ini ini, MainViewController controller) {
        String path = "";
        Ini.Section section = ini.get("connection"+i);
        switch (section.get("type")) {
            case "Aggregation" ->
                    controller.drawAggregation(section.get("start"),section.get("end"),
                            section.get("startCardinality"), section.get("endCardinality"));
            case "Association" ->
                    controller.drawAssociation(section.get("start"),section.get("end"),
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
            default -> differentConnectionClassException();
        }
    }

    public void load(MainViewController controller, Data data) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Ini file (*.ini)","*.ini");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(new Stage());

        if(file!=null) {
            data.pickedPair.clear();
            data.entityList.clear();
            data.connectedPairs.clear();
            controller.pane.getChildren().clear();
            OtherClassEntity.counter=0;

            Ini ini = new Ini();
            Ini.Section section;
            try {
                ini.load(new FileReader(file));
                section = ini.get("config");
                classCounter = Integer.parseInt(section.get("classCounter"));
                connectionCounter = Integer.parseInt(section.get("connectionCounter"));
            } catch (IOException exception) {
                fileNotFoundException();
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
        else fileNotPickedException();
    }

    private void saveFileIOException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File error");
        alert.setHeaderText("Could not save the file");
        alert.setContentText("File was not created or data has not been stored");
        alert.showAndWait();
    }
    private void fileNotFoundException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File error");
        alert.setHeaderText("Could not read the file");
        alert.setContentText("File was not found, could not read the data");
        alert.showAndWait();
    }
    private void differentConnectionClassException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File error");
        alert.setHeaderText("Wrong Connection Class name");
        alert.setContentText("Connection class name could not be read properly");
    }
    private void fileNotPickedException() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("File error");
        alert.setHeaderText("No file to be opened");
        alert.setContentText("Cannot load file. File was not chosen or does not exist");
    }
}

