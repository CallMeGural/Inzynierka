package pl.gf.umlcd.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.paint.Color;
import pl.gf.umlcd.Data;
import pl.gf.umlcd.MainViewController;

public class Composition extends Aggregation {

    public Composition(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2, Data data, MainViewController controller){
        super(x1,y1,x2,y2,data,controller);
        diamond.setFill(Color.BLACK);
    }

    public Composition() {

    }
}
