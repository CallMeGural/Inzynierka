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
    OtherClassType type;

    public OtherClassEntity(Label title) {
        this.vbox = new VBox();
        this.name = new Label("Class Name");
        this.variablesLab = new Label("Variables");
        this.methodsLab = new Label("Methods");
        this.vars = new TextArea();
        this.meths = new TextArea();

        vbox.setId("other"+counter);
        vbox.setPrefSize(WIDTH,HEIGHT);
        vbox.setStyle("-fx-border-style: solid");
        System.out.println(vbox.getId());
        counter++;
        this.title=title;
        System.out.println("Tworze obiekt OtherClassEntity");
    }

    public OtherClassEntity(Label title, OtherClassType type) {
        //super();
        this.vbox = new VBox();
        this.name = new Label("Class Name");
        this.variablesLab = new Label("Variables");
        this.methodsLab = new Label("Methods");
        this.vars = new TextArea();
        this.meths = new TextArea();

        vbox.setId("other"+counter);
        vbox.setPrefSize(WIDTH,HEIGHT);
        vbox.setStyle("-fx-border-style: solid");
        System.out.println(vbox.getId());
        counter++;
        this.type=type;
        this.title = title;
        this.title.setText("<<"+type.name().toLowerCase(Locale.ROOT)+">>");
    }

    @Override
    public void setNodesToVBox() {
        vbox.getChildren().addAll(title, name, variablesLab, vars, methodsLab, meths);
        System.out.println("VBox " + vbox.getId() + " prepared");
        vbox.setAlignment(Pos.CENTER);
    }

    @Override
    public void doubleClickEvent (MainViewController controller, Data data) {
        vbox.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)){
                if(e.getClickCount() == 2) controller.showOtherEntity(e);
                else singleClickEvent(data);
            }
        });
    }

    @Override
    public void setChildren(Data data, ObjectDetailController controller) {
        Label label = (Label) data.entityList.get(controller.pickedId).getVbox().getChildren().get(1);
        data.entityList.get(controller.pickedId).getVbox().getChildren().remove(1);
        controller.vBox.getChildren().add(data.entityList.get(controller.pickedId).getVbox().getChildren().get(0));
        controller.vBox.getChildren().add(new TextField(label.getText()));
        controller.vBox.getChildren().addAll(data.entityList.get(controller.pickedId).getVbox().getChildren());

    }
}
