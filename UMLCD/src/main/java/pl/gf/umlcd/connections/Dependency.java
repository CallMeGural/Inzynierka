package pl.gf.umlcd.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gf.umlcd.Data;
import pl.gf.umlcd.MainViewController;


@Getter
@Setter
@NoArgsConstructor
public class Dependency extends DirectedAssociation {

    public Dependency(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2/*, Data data*/, MainViewController controller){
        super(x1,y1,x2,y2,/*data,*/controller);
        mainLine.getStrokeDashArray().setAll(10.0, 5.0);
    }
}