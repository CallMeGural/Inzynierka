package com.example.demo.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Group;
import javafx.scene.shape.Line;

public class Association extends Group {
    private Line mainLine = new Line();

    public Association(ReadOnlyDoubleProperty startX, ReadOnlyDoubleProperty startY, ReadOnlyDoubleProperty endX, ReadOnlyDoubleProperty endY) {
        mainLine.startXProperty().bind(startX);
        mainLine.startYProperty().bind(startY);
        mainLine.endXProperty().bind(endX);
        mainLine.endYProperty().bind(endY);

    }


}
