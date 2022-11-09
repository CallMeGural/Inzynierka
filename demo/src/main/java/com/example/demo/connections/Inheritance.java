package com.example.demo.connections;

import com.example.demo.Data;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Inheritance extends Dependency {

    protected Polygon triangle = new Polygon();

    public Inheritance(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2, Data data){
        super();
        this.x1.bind(x1);
        this.y1.bind(y1);
        this.x2.bind(x2);
        this.y2.bind(y2);

        getChildren().addAll(mainLine, triangle);
        triangle.setFill(Color.WHITE);
        triangle.setStroke(Color.BLACK);
        for(SimpleDoubleProperty s : new SimpleDoubleProperty[]{this.x1,this.y1,this.x2,this.y2}){
            s.addListener( (l,o,n) -> update() );
        }
        update();
        pickConnection(data);

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