package com.example.demo.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.shape.Polyline;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Dependency extends Group {

    protected Polyline mainLine = new Polyline();
    protected Polyline head = new Polyline();
    protected SimpleDoubleProperty x1 = new SimpleDoubleProperty();
    protected SimpleDoubleProperty y1 = new SimpleDoubleProperty();
    protected SimpleDoubleProperty x2 = new SimpleDoubleProperty();
    protected SimpleDoubleProperty y2 = new SimpleDoubleProperty();

    protected final double ARROW_SCALER = 20;
    protected final double ARROWHEAD_ANGLE = Math.toRadians(40);
    protected final double ARROWHEAD_LENGTH = 10;

    public Dependency(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2){
        this.x1.bind(x1);
        this.y1.bind(y1);
        this.x2.bind(x2);
        this.y2.bind(y2);

        getChildren().addAll(mainLine, head);

        for(SimpleDoubleProperty s : new SimpleDoubleProperty[]{this.x1,this.y1,this.x2,this.y2}){
            s.addListener( (l,o,n) -> update() );
        }
        mainLine.getStrokeDashArray().setAll(10.0, 5.0);
        update();
    }


    protected void update() {
        double[] start = scale(x1.get(), y1.get(), x2.get(), y2.get());
        double[] end = scale(x2.get(), y2.get(), x1.get(), y1.get());

        double x1 = start[0];
        double y1 = start[1];
        double x2 = end[0];
        double y2 = end[1];

        mainLine.getPoints().setAll(x1,y1,x2,y2);

        double theta = Math.atan2(y2-y1, x2-x1);
        updateHead(x1,y1,theta);


    }

    protected void updateHead(double x1,double y1,double theta) {
        //arrowhead
        double x = x1 + Math.cos(theta + ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        double y = y1 + Math.sin(theta + ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        head.getPoints().setAll(x,y,x1,y1);
        x = x1 + Math.cos(theta - ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        y = y1 + Math.sin(theta - ARROWHEAD_ANGLE) * ARROWHEAD_LENGTH;
        head.getPoints().addAll(x,y);

    }

    private double[] scale(double x1, double y1, double x2, double y2){
        double theta = Math.atan2(y2-y1, x2-x1);
        return new double[]{
                x1 + Math.cos(theta) * ARROW_SCALER,
                y1 + Math.sin(theta) * ARROW_SCALER
        };
    }

}