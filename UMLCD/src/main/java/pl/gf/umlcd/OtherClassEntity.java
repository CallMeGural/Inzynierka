package pl.gf.umlcd;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.Locale;

@NoArgsConstructor
@Getter
@Setter
public class OtherClassEntity extends ClassEntity {
    private Label title;
    OtherClassType type;


    public OtherClassEntity(Label title) {
        super();
        this.title=title;
    }
    public OtherClassEntity(Label title, OtherClassType type) {
        super();
        this.type=type;
        this.title = title;
        this.title.setText("<<"+type.name().toLowerCase(Locale.ROOT)+">>");
    }

    @Override
    protected void setNodesToVBox() {
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
    public void showEntity(Data data, MainViewController mainController, ObjectDetailController objController, VBox vb, String vBoxId) {
        for(int i = 0; i<data.entityList.size(); i++) {
            if(data.entityList.get(i).getVbox().getId().equals(vBoxId)) {
                objController.pickedId=i;
                break;
            }
        }
        objController.data=data;

        Label label = (Label) data.entityList.get(objController.pickedId).getVbox().getChildren().get(1);
        data.entityList.get(objController.pickedId).getVbox().getChildren().remove(1);
        objController.vBox.getChildren().add(data.entityList.get(objController.pickedId).getVbox().getChildren().get(0));
        objController.vBox.getChildren().add(new TextField(label.getText()));
        objController.vBox.getChildren().addAll(data.entityList.get(objController.pickedId).getVbox().getChildren());
    }

}
