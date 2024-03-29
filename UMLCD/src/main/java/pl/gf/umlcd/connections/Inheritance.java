package pl.gf.umlcd.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gf.umlcd.Data;
import pl.gf.umlcd.MainViewController;


@Getter
@Setter
@NoArgsConstructor
public class Inheritance extends Dependency {

    protected Polygon triangle = new Polygon();

    public Inheritance(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2/*, Data data*/, MainViewController controller){
        data = Data.getInstance();
        this.setId("conn"+counter);
        this.x1.bind(x1);
        this.y1.bind(y1);
        this.x2.bind(x2);
        this.y2.bind(y2);
        mainLine.setStrokeWidth(2.);
        triangle.setStrokeWidth(2.);
        getChildren().addAll(mainLine, triangle);
        triangle.setFill(Color.WHITE);
        triangle.setStroke(Color.BLACK);
        for(SimpleDoubleProperty s : new SimpleDoubleProperty[]{this.x1,this.y1,this.x2,this.y2}){
            s.addListener( (l,o,n) -> update() );
        }
        update();
        doubleClickEvent(controller/*,data*/);
        counter++;
    }

    @Override
    protected void updateHead(double x1,double y1,double theta) {
        //arrowhead
        double x = x1 + Math.cos(theta + ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        double y = y1 + Math.sin(theta + ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        triangle.getPoints().setAll(x,y,x1,y1);
        x = x1 + Math.cos(theta - ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        y = y1 + Math.sin(theta - ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        triangle.getPoints().addAll(x,y);

    }
}