package com.example.demo.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.paint.Color;

public class Realization extends Inheritance {
    public Realization(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2) {
        super(x1, y1, x2, y2);
        triangle.setFill(Color.CYAN);
        mainLine.getStrokeDashArray().setAll(10.0, 5.0);
    }
}
