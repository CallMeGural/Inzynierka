package pl.gf.umlcd;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectedPair {
    private VBox vBox1, vBox2;
    private String cardinality1, cardinality2;
    private Group connection;

    public ConnectedPair(VBox vBox1, VBox vBox2, Group connection) {
        this.vBox1 = vBox1;
        this.vBox2 = vBox2;
        this.connection = connection;
        this.cardinality1="not specified";
        this.cardinality2="not specified";
    }
    public ConnectedPair(VBox vBox1, VBox vBox2, Group connection, String startCardinality, String endCardinality) {
        this.vBox1 = vBox1;
        this.vBox2 = vBox2;
        this.connection = connection;
        this.cardinality1=startCardinality;
        this.cardinality2=endCardinality;
    }
}
