package pl.gf.umlcd;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import pl.gf.umlcd.connections.Association;

@Getter
@Setter
public class ConnectedPair {
    private ClassEntity vBox1, vBox2;
    private String cardinality1, cardinality2;
    private Association connection;
    private Data data;

    public ConnectedPair(ClassEntity vBox1, ClassEntity vBox2, Association connection) {
        this.vBox1 = vBox1;
        this.vBox2 = vBox2;
        this.connection = connection;
        this.cardinality1="not specified";
        this.cardinality2="not specified";
        this.data = Data.getInstance();
    }
    public ConnectedPair(ClassEntity vBox1, ClassEntity vBox2, Association connection, String startCardinality, String endCardinality) {
        this.vBox1 = vBox1;
        this.vBox2 = vBox2;
        this.connection = connection;
        this.cardinality1=startCardinality;
        this.cardinality2=endCardinality;
    }

    public boolean isPresent(Association connection) {
        if(data.getConnections().contains(connection)) return true;
        return false;
    }
    public boolean isPresent(ClassEntity entity) {
        if(data.getEntities().contains(entity)) return true;
        return false;
    }
}
