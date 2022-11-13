package pl.gf.umlcd;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.ini4j.Ini;
import org.ini4j.Wini;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SaveData {
    private Label label;
    private TextArea textArea;
    private int classCounter = 0;
    private int otherCounter = 0;
    private int connectionCounter = 0;

    public String setFileName() {
        TextInputDialog dialog = new TextInputDialog("file");
        dialog.setTitle("Save File");
        dialog.setHeaderText("Set filename");
        dialog.setContentText("Enter filename:");
        return dialog.showAndWait().orElseThrow();
    }

    public void saveClassEntity(VBox vBox, Ini ini) {
        label = (Label) vBox.getChildren().get(0); //name
        ini.put("class" + classCounter, "name", label.getText());

        ini.put("class" + classCounter, "layoutX", vBox.getLayoutX());
        ini.put("class" + classCounter, "layoutY", vBox.getLayoutY());

        textArea = (TextArea) vBox.getChildren().get(2); //vars
        ini.put("class" + classCounter, "vars", textArea.getText());

        textArea = (TextArea) vBox.getChildren().get(4); //vars
        ini.put("class" + classCounter, "vars", textArea.getText());
    }

    public void saveOtherClassEntity(VBox vBox, Ini ini) {
        label = (Label) vBox.getChildren().get(0); //type
        ini.put("other" + otherCounter, "type", label.getText());
        label = (Label) vBox.getChildren().get(1); //name
        ini.put("other" + otherCounter, "name", label.getText());

        ini.put("other" + otherCounter, "layoutX", vBox.getLayoutX());
        ini.put("other" + otherCounter, "layoutY", vBox.getLayoutY());

        textArea = (TextArea) vBox.getChildren().get(3); //vars
        ini.put("other" + otherCounter, "vars", textArea.getText());

        textArea = (TextArea) vBox.getChildren().get(5); //methods
        ini.put("other" + otherCounter, "meths", textArea.getText());
    }

    public void saveConnection(ConnectedPair pair, Ini ini) {
        ini.put("connection" + connectionCounter, "vbox1", pair.getVBox1().getId());
        ini.put("connection" + connectionCounter, "vbox2", pair.getVBox2().getId());
        ini.put("connection" + connectionCounter, "type", pair.getConnection().getClass().getSimpleName());
    }

    public void save(MainViewController controller) {
        VBox vBox;
        String filename = setFileName() + ".ini";
        Path path = Paths.get("src/main/resources/"+filename);
        try {
            Files.createFile(path);
            File file = new File(String.valueOf(path));
            Wini ini = new Wini(file);

            for (int i=0;i<controller.data.entityList.size();i++) {
                vBox = controller.data.entityList.get(i).getVbox();
                if (vBox.getChildren().size() == 5) { //ClassEntity
                    saveClassEntity(vBox, ini);
                    classCounter++;
                } else { //OtherClassEntity
                    saveOtherClassEntity(vBox, ini);
                    otherCounter++;
                }
            }
            for (ConnectedPair pair : controller.data.connectedPairs) {
                saveConnection(pair, ini);
                connectionCounter++;
            }
            ini.put("config", "classCounter", classCounter);
            ini.put("config", "otherCounter", otherCounter);
            ini.put("config", "connectionCounter", connectionCounter);
            ini.store();
        } catch (IOException exception) {
            saveFileIOException();
        }
    }

    private void loadClassEntity(int i, Ini ini, ClassEntity entity, MainViewController controller) {
        Ini.Section section = ini.get("class"+i);
        entity.getMeths().setText(section.get("meths"));
        entity.getVars().setText(section.get("vars"));

        entity.getName().setText(section.get("name"));

        entity.getVbox().setLayoutX(Double.parseDouble(section.get("layoutX")));
        entity.getVbox().setLayoutY(Double.parseDouble(section.get("layoutY")));
        controller.createNewClassEntity(entity);
    }

    private void loadOtherClassEntity(int i, Ini ini, OtherClassEntity otherEntity, MainViewController controller) {
        Ini.Section section = ini.get("other"+i);
        otherEntity.getMeths().setText(section.get("meths"));
        otherEntity.getVars().setText(section.get("vars"));

        otherEntity.getName().setText(section.get("name"));
        otherEntity.getTitle().setText(section.get("type"));

        otherEntity.getVbox().setLayoutX(Double.parseDouble(section.get("layoutX")));
        otherEntity.getVbox().setLayoutY(Double.parseDouble(section.get("layoutY")));
        switch (section.get("type")) {
            case "<<enum>>" -> controller.createNewEnumEntity(otherEntity);
            case "<<primitive>>" -> controller.createNewPrimitiveEntity(otherEntity);
            case "<<interface>>" -> controller.createNewInterfaceEntity(otherEntity);
        }
    }

    private void loadConnection(int i, Ini ini, MainViewController controller) {
        String path = "";
        Ini.Section section = ini.get("connection"+i);
        switch (section.get("type")) {
            case "Aggregation" ->
                    controller.drawAggregation(section.get("vbox1"),section.get("vbox2"));
            case "Association" ->
                    controller.drawAssociation(section.get("vbox1"),section.get("vbox2"));
            case "Composition" ->
                    controller.drawComposition(section.get("vbox1"),section.get("vbox2"));
            case "Dependency" ->
                    controller.drawDependency(section.get("vbox1"),section.get("vbox2"));
            case "Inheritance" ->
                    controller.drawInheritance(section.get("vbox1"),section.get("vbox2"));
            case "Realization" ->
                    controller.drawRealization(section.get("vbox1"),section.get("vbox2"));
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
                otherCounter = Integer.parseInt(section.get("otherCounter"));
                connectionCounter = Integer.parseInt(section.get("connectionCounter"));
            } catch (IOException exception) {
                fileNotFoundException();
            }
            for(int i=0;i<classCounter;i++) {
                //loadClassEntity(i,ini,entity,controller);
                loadClassEntity(i,ini,new ClassEntity(), controller);
            }
            for(int i=0;i<otherCounter;i++) {
                //loadOtherClassEntity(i,ini,otherEntity,controller);
                loadOtherClassEntity(i,ini, new OtherClassEntity(new Label()), controller);
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

