package pl.gf.umlcd;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassEntity {

    protected VBox vbox;
    protected Label name;
    protected final Label variablesLab;
    protected final Label methodsLab;
    protected TextArea vars;
    protected TextArea meths;
    protected static int counter = 0;
    protected final double WIDTH = 150.;
    protected final double HEIGHT = 200.;


    public ClassEntity() {
        this.vbox = new VBox();
        this.name = new Label("Class Name");
        this.variablesLab = new Label("Variables");
        this.methodsLab = new Label("Methods");
        this.vars = new TextArea();
        this.meths = new TextArea();

        vbox.setId("vbox"+counter);
        vbox.setPrefSize(WIDTH,HEIGHT);
        vbox.setStyle("-fx-border-style: solid");
        System.out.println(vbox.getId());
        counter++;
    }

    //przypisuje do VBoxa odpowiednie labelki i textfieldy
    protected void setNodesToVBox(){
        vbox.getChildren().addAll(name,variablesLab,vars,methodsLab,meths);
        System.out.println("VBox "+vbox.getId()+" prepared");
        vbox.setAlignment(Pos.CENTER);
    }

    //event jednokrotnego kliknięcia w klasę - aktualizuje kolor ramki i zapisuje klasę do listy zaznaczonych klas
    public void singleClickEvent(Data data) {
        data.pickedPair.add(this);
        vbox.setStyle("-fx-border-color: blue");
        if(data.pickedPair.size()>2) {
            for(ClassEntity entity : data.pickedPair) {
                entity.getVbox().setStyle("-fx-border-color: black");
            }
            data.pickedPair.clear();

        }
        System.out.println(data.pickedPair);
    }

    //event podwojnego klikniecia - otwiera okno z wlasnosciami klasy
    public void doubleClickEvent(MainViewController controller, Data data) {
        vbox.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)){
                if(e.getClickCount() == 2) controller.showClassEntity(e);
                else singleClickEvent(data);
            }
        });
    }

    //inicjalizacja klasy na ekranie - wyswietlenie na ekranie, nadanie mozliwosci poruszania, wpisanie do listy klas
    public void initializeEntity(DraggableMaker maker, MainViewController controller, Data data, Pane pane) {
        this.setNodesToVBox();
        System.out.println(this.getVbox().getChildren());
        maker.makeDraggable(this.getVbox());
        this.doubleClickEvent(controller, data);
        pane.getChildren().add(this.getVbox());
        data.entityList.add(this);
        for(ClassEntity entity : data.entityList) {
            System.out.println(entity.getVbox().getId());
        }
    }

    //wyswietlenie wlasciwosci klasy
    public void showEntity(Data data, MainViewController mainController, ObjectDetailController objController, VBox vb, String vBoxId) {
        //odnalezienie odpowiedniej klasy w liscie
        for(int i = 0; i<data.entityList.size(); i++) {
            if(data.entityList.get(i).getVbox().getId().equals(vBoxId)) {
                objController.pickedId=i;
                break;
            }
        }
        objController.data=data;

        //przepisanie labelkow i textfieldow do nowego okna
        Label label = (Label) data.entityList.get(objController.pickedId).getVbox().getChildren().get(0);
        data.entityList.get(objController.pickedId).getVbox().getChildren().remove(0);

        objController.vBox.getChildren().add(new TextField(label.getText()));
        objController.vBox.getChildren().addAll(data.entityList.get(objController.pickedId).getVbox().getChildren());
    }

}

