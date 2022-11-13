package pl.gf.umlcd.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gf.umlcd.Data;

@Getter
@Setter
@NoArgsConstructor
public class Association extends Group {

    protected Polyline mainLine = new Polyline();
    protected SimpleDoubleProperty x1 = new SimpleDoubleProperty();
    protected SimpleDoubleProperty y1 = new SimpleDoubleProperty();
    protected SimpleDoubleProperty x2 = new SimpleDoubleProperty();
    protected SimpleDoubleProperty y2 = new SimpleDoubleProperty();

    protected final double ARROW_SCALER = 20;

    public Association(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2, Data data){
        this.x1.bind(x1);
        this.y1.bind(y1);
        this.x2.bind(x2);
        this.y2.bind(y2);

        getChildren().add(mainLine);

        for(SimpleDoubleProperty s : new SimpleDoubleProperty[]{this.x1,this.y1,this.x2,this.y2}){
            s.addListener( (l,o,n) -> update() );
        }
        update();
        pickConnection(data);
    }


    protected void update() {
        double[] start = scale(x1.get(), y1.get(), x2.get(), y2.get());
        double[] end = scale(x2.get(), y2.get(), x1.get(), y1.get());

        double x1 = start[0];
        double y1 = start[1];
        double x2 = end[0];
        double y2 = end[1];

        mainLine.getPoints().setAll(x1,y1,x2,y2);

    }

    protected double[] scale(double x1, double y1, double x2, double y2){
        double theta = Math.atan2(y2-y1, x2-x1);
        return new double[]{
                x1 + Math.cos(theta) * ARROW_SCALER,
                y1 + Math.sin(theta) * ARROW_SCALER
        };
    }

    public void pickConnection(Data data) {
        this.setOnMouseClicked(e -> {
            data.pickedConnection = this;
            if(!(this.getMainLine().getStroke() == Color.BLUE))  {
                this.getMainLine().setStroke(Color.BLUE);
            }
            else {
                this.getMainLine().setStroke(Color.BLACK);
            }

        });
    }


}
