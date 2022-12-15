package pl.gf.umlcd;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@NoArgsConstructor
@Getter
@Setter
public class OtherClassEntity extends ClassEntity {
    private Label title;
    private OtherClassType type;

    public OtherClassEntity(Label title) {
        draggableMaker = DraggableMaker.getInstance();
        data = Data.getInstance();
        this.vBox = new VBox();
        this.name = new Label("Class Name");
        this.variablesLab = new Label("Variables");
        this.methodsLab = new Label("Methods");
        this.vars = new TextArea();
        this.meths = new TextArea();

        vBox.setId("other"+counter);
        vBox.setPrefSize(WIDTH,HEIGHT);
        vBox.setStyle("-fx-border-style: solid");
        System.out.println(vBox.getId());
        counter++;
        this.title=title;
        System.out.println("Tworze obiekt OtherClassEntity");
    }

    public OtherClassEntity(Label title, OtherClassType type) {
        draggableMaker = DraggableMaker.getInstance();
        data = Data.getInstance();
        //super();
        this.vBox = new VBox();
        this.name = new Label("Class Name");
        this.variablesLab = new Label("Variables");
        this.methodsLab = new Label("Methods");
        this.vars = new TextArea();
        this.meths = new TextArea();

        vBox.setId("other"+counter);
        vBox.setPrefSize(WIDTH,HEIGHT);
        vBox.setStyle("-fx-border-style: solid");
        System.out.println(vBox.getId());
        counter++;
        this.type=type;
        this.title = title;
        this.title.setText("<<"+type.name().toLowerCase(Locale.ROOT)+">>");
    }

    @Override
    public void setNodesToVBox() {
        vBox.getChildren().addAll(title, name, variablesLab, vars, methodsLab, meths);
        System.out.println("VBox " + vBox.getId() + " prepared");
        vBox.setAlignment(Pos.CENTER);
    }

    @Override
    public void doubleClickEvent (MainViewController controller/*, Data data*/) {
        vBox.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)){
                if(e.getClickCount() == 2) controller.showOtherEntity(e);
                else singleClickEvent(/*data*/);
            }
        });
    }

    @Override
    public void setChildren(/*Data data, */ObjectViewController controller) {
//        Label label = (Label) data.entityList.get(controller.pickedId).getVbox().getChildren().get(1);
//        data.entityList.get(controller.pickedId).getVbox().getChildren().remove(1);
//        controller.vBox.getChildren().add(data.entityList.get(controller.pickedId).getVbox().getChildren().get(0));
//        controller.vBox.getChildren().add(new TextField(label.getText()));
//        controller.vBox.getChildren().addAll(data.entityList.get(controller.pickedId).getVbox().getChildren());
        Label label = (Label) data.getEntities().get(controller.getPickedId()).getVBox().getChildren().get(1);
        data.getEntities().get(controller.getPickedId()).getVBox().getChildren().remove(1);
        controller.getVBox().getChildren().add(data.getEntities().get(controller.getPickedId()).getVBox().getChildren().get(0));
        controller.getVBox().getChildren().add(new TextField(label.getText()));
        controller.getVBox().getChildren().addAll(data.getEntities().get(controller.getPickedId()).getVBox().getChildren());

    }
}
