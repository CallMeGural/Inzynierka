package pl.gf.umlcd.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import pl.gf.umlcd.MainViewController;

public class SelfAssociation extends Association{

    public SelfAssociation(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1,ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2, MainViewController controller) {
        super(x1,y1,x2,y2,controller);
    }

    @Override
    protected void update() {
        double[] start = scale(x1.get(), y1.get(), x1.get(), y1.get());

        double x1 = start[0];
        double y1 = start[1];

        mainLine.getPoints().setAll(
                x1,y1,
                x1+50.,y1,
                x1+50.,y1-150.,
                x1-50.,y1-150.,
                x1-50., y1-75.);
    }
}
