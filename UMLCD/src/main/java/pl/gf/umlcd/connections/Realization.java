package pl.gf.umlcd.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gf.umlcd.Data;
import pl.gf.umlcd.MainViewController;

@Getter
@Setter
@NoArgsConstructor
public class Realization extends Inheritance {
    public Realization(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2, Data data, MainViewController controller) {
        super(x1, y1, x2, y2,data,controller);
        triangle.setFill(Color.CYAN);
        mainLine.getStrokeDashArray().setAll(10.0, 5.0);
    }
}