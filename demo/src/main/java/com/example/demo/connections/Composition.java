package com.example.demo.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.paint.Color;

public class Composition extends Aggregation{

    public Composition(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2){
        super(x1,y1,x2,y2);
        diamond.setFill(Color.BLACK);
    }

}
