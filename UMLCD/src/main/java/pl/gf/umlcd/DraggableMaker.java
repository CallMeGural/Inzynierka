package pl.gf.umlcd;

import javafx.scene.Node;

public class DraggableMaker {
    private double mouseX;
    private double mouseY;

    public void makeDraggable(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            mouseX=mouseEvent.getX();
            mouseY=mouseEvent.getY();
        });
        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX()-mouseX-90); //90 - left bar width
            node.setLayoutY(mouseEvent.getSceneY()-mouseY-25); //25 - top bar height
        });
    }
}
