package com.example.demo;

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
            node.setLayoutX(mouseEvent.getSceneX()-mouseX-80); //80 - left bar width
            node.setLayoutY(mouseEvent.getSceneY()-mouseY-60); //60 - top bar height
        });
    }
}
