package com.example.demo;

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


public class SaveData {
    private Label label;
    private TextArea textArea;
    private int classCounter = 0;
    private int otherCounter = 0;

    public String setFileName() {
        TextInputDialog dialog = new TextInputDialog("file");
        dialog.setTitle("Save File");
        dialog.setHeaderText("Set filename");
        dialog.setContentText("Enter filename:");
        return dialog.showAndWait().orElseThrow();
    }

    public void saveClassEntity(VBox vBox, Ini ini) {
        label = (Label) vBox.getChildren().get(0); //name
        ini.put("class"+classCounter,"name", label.getText());

        ini.put("class"+classCounter,"layoutX", vBox.getLayoutX());
        ini.put("class"+classCounter,"layoutY", vBox.getLayoutY());

        textArea = (TextArea) vBox.getChildren().get(2); //vars
        ini.put("class"+classCounter,"vars", textArea.getText());

        textArea = (TextArea) vBox.getChildren().get(4); //vars
        ini.put("class"+classCounter,"vars", textArea.getText());
    }

    public void saveOtherClassEntity(VBox vBox,Ini ini) {
        label = (Label) vBox.getChildren().get(0); //type
        ini.put("other"+otherCounter,"type", label.getText());
        label = (Label) vBox.getChildren().get(1); //name
        ini.put("other"+otherCounter,"name", label.getText());

        ini.put("other"+otherCounter,"layoutX", vBox.getLayoutX());
        ini.put("other"+otherCounter,"layoutY", vBox.getLayoutY());

        textArea = (TextArea) vBox.getChildren().get(3); //vars
        ini.put("other"+otherCounter,"vars", textArea.getText());

        textArea = (TextArea) vBox.getChildren().get(5); //methods
        ini.put("other"+otherCounter,"meths", textArea.getText());
    }

    public void save(MainViewController controller) throws IOException {
        VBox vBox;
        String filename = setFileName()+".ini";
        File file = new File(filename);
        if(file.createNewFile()) {
            Wini ini = new Wini(file);
            for(int i=0;i<controller.pane.getChildren().size();i++) {
                vBox = (VBox) controller.pane.getChildren().get(i);

                if (vBox.getChildren().size() == 5) { //ClassEntity

                    saveClassEntity(vBox,ini);
                    classCounter++;
                }
                else { //OtherClassEntity

                    saveOtherClassEntity(vBox,ini);
                    otherCounter++;
                }
            }
            ini.put("config","classCounter", classCounter);
            ini.put("config","otherCounter",otherCounter);
            ini.store();
        }
        else System.out.println("NIE UTWORZONO PLIKU");

    }

    public void loadClassEntity(int i, Ini ini, Ini.Section section, ClassEntity entity, MainViewController controller) {
        section = ini.get("class"+i);
        entity.getMeths().setText(section.get("meths"));
        entity.getVars().setText(section.get("vars"));

        entity.getName().setText(section.get("name"));

        entity.getVbox().setLayoutX(Double.parseDouble(section.get("layoutX")));
        entity.getVbox().setLayoutY(Double.parseDouble(section.get("layoutY")));
        controller.createNewClassEntity(entity);
    }

    public void loadOtherClassEntity(int i, Ini ini, Ini.Section section, OtherClassEntity otherEntity, MainViewController controller) {
        section = ini.get("other"+i);
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

    public void load(MainViewController controller, Data data) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Ini file (*.ini)","*.ini");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(new Stage());

        if(file!=null) {
            data.pickedPair.clear();
            data.entityList.clear();
            controller.pane.getChildren().clear();

            Ini ini = new Ini();
            Ini.Section section;
            ini.load(new FileReader(file));
            section = ini.get("config");
            classCounter = Integer.parseInt(section.get("classCounter"));
            otherCounter = Integer.parseInt(section.get("otherCounter"));

            for(int i=0;i<classCounter;i++) {
                ClassEntity entity = new ClassEntity();
                loadClassEntity(i,ini,section,entity,controller);
            }
            for(int i=0;i<otherCounter;i++) {
                OtherClassEntity otherEntity = new OtherClassEntity(new Label(),OtherClassType.PRIMITIVE);
                loadOtherClassEntity(i,ini,section,otherEntity,controller);
            }
        }
    }

}
