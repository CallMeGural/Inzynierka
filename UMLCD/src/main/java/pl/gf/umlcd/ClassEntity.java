package pl.gf.umlcd;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassEntity {

    protected VBox vBox;
    protected Label name;
    protected Label variablesLab;
    protected Label methodsLab;
    protected TextArea vars;
    protected TextArea meths;
    protected Data data;
    protected DraggableMaker draggableMaker;
    protected static int counter = 0;
    protected final double WIDTH = 150.;
    protected final double HEIGHT = 150.;

    public ClassEntity() {
        this.vBox = new VBox();
        this.name = new Label("Class Name");
        this.variablesLab = new Label("Variables");
        this.methodsLab = new Label("Methods");
        this.vars = new TextArea();
        this.meths = new TextArea();
        data = Data.getInstance();
        draggableMaker = DraggableMaker.getInstance();

        vBox.setId("class"+counter);
        vBox.setPrefSize(WIDTH,HEIGHT);
        vBox.setStyle("-fx-border-style: solid");
        System.out.println(vBox.getId());

        System.out.println("Tworze obiekt ClassEntity");
    }

    public ClassEntity(String id) {
        this.vBox = new VBox();
        this.name = new Label("Class Name");
        this.variablesLab = new Label("Variables");
        this.methodsLab = new Label("Methods");
        this.vars = new TextArea();
        this.meths = new TextArea();
        data = Data.getInstance();
        draggableMaker = DraggableMaker.getInstance();

        vBox.setId(id);
        vBox.setPrefSize(WIDTH,HEIGHT);
        vBox.setStyle("-fx-border-style: solid");
        System.out.println(vBox.getId());

        System.out.println("Tworze obiekt ClassEntity");
    }

    //przypisuje do VBoxa odpowiednie labelki i textfieldy
    public void setNodesToVBox(){
        counter++;
        vBox.getChildren().addAll(name,variablesLab,vars,methodsLab,meths);
        System.out.println("VBox "+vBox.getId()+" prepared");
        vBox.setAlignment(Pos.CENTER);
    }

    //event jednokrotnego kliknięcia w klasę - aktualizuje kolor ramki i zapisuje klasę do listy zaznaczonych klas
    public void singleClickEvent(/*Data data*/) {
        data.getPickedPair().add(this);
        vBox.setStyle("-fx-border-color: blue");
        if(data.getPickedPair().size()>2) {
            for(ClassEntity entity : data.getPickedPair()) {
                entity.getVBox().setStyle("-fx-border-color: black");
            }
            data.getPickedPair().clear();

        }
        System.out.println(data.getPickedPair());
    }

    //event podwojnego klikniecia - otwiera okno z wlasnosciami klasy
    public void doubleClickEvent(MainViewController controller/*, Data data*/) {
        vBox.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)){
                if(e.getClickCount() == 2) controller.showClassEntity(e);
                else singleClickEvent(/*data*/);
            }
        });
    }

    //inicjalizacja klasy na ekranie - wyswietlenie na ekranie, nadanie mozliwosci poruszania, wpisanie do listy klas
    public void initializeEntity(/*DraggableMaker maker, */MainViewController controller/*, Data data*/, Pane pane) {
        this.setNodesToVBox();
        System.out.println(this.getVBox().getChildren());
        draggableMaker.makeDraggable(this.getVBox());
        this.doubleClickEvent(controller/*, data*/);
        pane.getChildren().add(this.getVBox());
        data.getEntities().add(this);
        for(ClassEntity entity : data.getEntities()) {
            System.out.println(entity.getVBox().getId());
        }
    }

    //wyswietlenie wlasciwosci klasy
    public void pickEntity(/*Data data, */ObjectViewController controller, String vBoxId) {
        //odnalezienie odpowiedniej klasy w liscie
        for(int i = 0; i<data.getEntities().size(); i++) {
            if (data.getEntities().get(i).getVBox().getId().equals(vBoxId)) {
                controller.setPickedId(i);
                //controller.pickedId = i;
                break;
            }
        }
    }

    public void setChildren(/*Data data, */ObjectViewController controller) {
        //przepisanie labelkow i textfieldow do nowego okna
//        Label label = (Label) data.entityList.get(controller.pickedId).getVbox().getChildren().get(0);
//        data.entityList.get(controller.pickedId).getVbox().getChildren().remove(0);
        Label label = (Label) data.getEntities().get(controller.getPickedId()).getVBox().getChildren().get(0);
        data.getEntities().get(controller.getPickedId()).getVBox().getChildren().remove(0);

        controller.getVBox().getChildren().add(new TextField(label.getText()));
        //controller.vBox.getChildren().addAll(data.entityList.get(controller.pickedId).getVbox().getChildren());
        controller.getVBox().getChildren().addAll(data.getEntities().get(controller.getPickedId()).getVBox().getChildren());

    }

}

