package pl.gf.umlcd;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import pl.gf.umlcd.connections.Association;

@Getter
@Setter
public class ConnectedPair {
    private VBox vBox1, vBox2;
    private String cardinality1, cardinality2;
    private Association connection;

    public ConnectedPair(VBox vBox1, VBox vBox2, Association connection) {
        this.vBox1 = vBox1;
        this.vBox2 = vBox2;
        this.connection = connection;
        this.cardinality1="not specified";
        this.cardinality2="not specified";
    }
    public ConnectedPair(VBox vBox1, VBox vBox2, Association connection, String startCardinality, String endCardinality) {
        this.vBox1 = vBox1;
        this.vBox2 = vBox2;
        this.connection = connection;
        this.cardinality1=startCardinality;
        this.cardinality2=endCardinality;
    }
}
