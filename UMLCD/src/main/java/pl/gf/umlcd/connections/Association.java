package pl.gf.umlcd.connections;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gf.umlcd.ConnectionViewController;
import pl.gf.umlcd.Data;
import pl.gf.umlcd.MainViewController;

import java.io.IOException;

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
    protected static int counter=0;


    public Association(ReadOnlyDoubleProperty x1, ReadOnlyDoubleProperty y1, ReadOnlyDoubleProperty x2, ReadOnlyDoubleProperty y2, Data data, MainViewController controller){
        this.setId("conn"+counter);
        this.x1.bind(x1);
        this.y1.bind(y1);
        this.x2.bind(x2);
        this.y2.bind(y2);

        getChildren().add(mainLine);

        for(SimpleDoubleProperty s : new SimpleDoubleProperty[]{this.x1,this.y1,this.x2,this.y2}){
            s.addListener( (l,o,n) -> update() );
        }
        update();
        doubleClickEvent(controller, data);
        counter++;
        System.out.println(this.getId());
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

    public void doubleClickEvent(MainViewController controller,Data data) {
        this.setOnMouseClicked(e -> {
            if(e.getButton().equals(MouseButton.PRIMARY)){
                System.out.println(e.getClickCount());
                if(e.getClickCount() == 1)
                    singleClickEvent(data);
                else
                    controller.showConnection(e);
            }
        });
    }

    public void singleClickEvent(Data data) {
        System.out.println(this.getClass().getSimpleName());
            data.pickedConnection = this;
            if(!(this.getMainLine().getStroke() == Color.BLUE))  {
                this.getMainLine().setStroke(Color.BLUE);
            }
            else {
                this.getMainLine().setStroke(Color.BLACK);
            }
    }


    public void showConnection(Data data, String id, ConnectionViewController controller) {
        System.out.println("connection id " +id);
        //odnalezienie odpowiedniego polaczenia w liscie
        for(int i = 0; i<data.connectedPairs.size(); i++) {
            if(data.connectedPairs.get(i).getConnection().getId().equals(id)) {
                controller.connectionId=i;
                System.out.println(data.connectedPairs.get(i));
                break;
            }
        }
        controller.data=data;
        Label label;
        //Start Node
        //if(data.connectedPairs.get(controller.connectionId).getVBox1().getClass().getSimpleName().equals("ClassEntity")) {
        //if(data.connectedPairs.get(controller.connectionId).getVBox1().getChildren().get(2).getClass().getSimpleName().equals("TextArea")) {
        if(data.connectedPairs.get(controller.connectionId).getVBox1().getChildren().size() == 5)
            label = (Label) data.connectedPairs.get(controller.connectionId).getVBox1().getChildren().get(0);
        else
            label = (Label) data.connectedPairs.get(controller.connectionId).getVBox1().getChildren().get(1);
        controller.startNameLabel.setText(label.getText());
        //End node
        if(data.connectedPairs.get(controller.connectionId).getVBox2().getChildren().size() == 5)
            label = (Label) data.connectedPairs.get(controller.connectionId).getVBox2().getChildren().get(0);
        else
            label = (Label) data.connectedPairs.get(controller.connectionId).getVBox2().getChildren().get(1);
        controller.endNameLabel.setText(label.getText());

        controller.typeLabel.setText(data.connectedPairs.get(controller.connectionId).getConnection().getClass().getSimpleName());

        String cardinality1 = data.connectedPairs.get((controller.connectionId)).getCardinality1();
        String cardinality2 = data.connectedPairs.get((controller.connectionId)).getCardinality2();

        controller.startCombo.setPromptText(cardinality1);
        controller.endCombo.setPromptText(cardinality2);
    }
}
