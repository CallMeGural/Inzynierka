package pl.gf.umlcd.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gf.umlcd.ConnectionViewController;
import pl.gf.umlcd.Data;
import pl.gf.umlcd.MainViewController;

@Getter
@Setter
@NoArgsConstructor
public class Aggregation extends Dependency {

    protected final double AGGREGATION_ANGLE = Math.toRadians(30);
    protected Polygon diamond = new Polygon();


    public Aggregation(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2, Data data, MainViewController controller){
        this.setId("conn"+counter);
        this.x1.bind(x1);
        this.y1.bind(y1);
        this.x2.bind(x2);
        this.y2.bind(y2);

        getChildren().addAll(mainLine, diamond);

        diamond.setFill(Color.WHITE);
        diamond.setStroke(Color.BLACK);

        for(SimpleDoubleProperty s : new SimpleDoubleProperty[]{this.x1,this.y1,this.x2,this.y2}){
            s.addListener( (l,o,n) -> update() );
        }
        update();

        doubleClickEvent(controller,data);
        counter++;
        System.out.println(this.getId());
    }

    @Override
    protected void updateHead(double x1, double y1, double theta) {
        double xh1 = x1 - Math.cos(theta + AGGREGATION_ANGLE) * ARROWHEAD_LENGTH;
        double yh1 = y1 - Math.sin(theta + AGGREGATION_ANGLE) * ARROWHEAD_LENGTH;
        diamond.getPoints().setAll(xh1,yh1,x1,y1);

        double xh2 = x1 - Math.cos(theta - AGGREGATION_ANGLE) * ARROWHEAD_LENGTH;
        double yh2 = y1 - Math.sin(theta - AGGREGATION_ANGLE) * ARROWHEAD_LENGTH;
        diamond.getPoints().addAll(xh2,yh2);

        double xh3 = xh2 - Math.cos(theta + AGGREGATION_ANGLE) * ARROWHEAD_LENGTH;
        double yh3 = yh2 - Math.sin(theta + AGGREGATION_ANGLE) * ARROWHEAD_LENGTH;
        diamond.getPoints().addAll(xh3,yh3);
    }

}
