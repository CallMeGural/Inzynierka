package pl.gornickifilip.inz.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.paint.Color;
import pl.gornickifilip.inz.Data;

public class Composition extends Aggregation {

    public Composition(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2, Data data){
        super(x1,y1,x2,y2,data);
        diamond.setFill(Color.BLACK);
    }

}
