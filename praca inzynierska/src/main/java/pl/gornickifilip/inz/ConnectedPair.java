package pl.gornickifilip.inz;

import javafx.scene.Group;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectedPair {
    private VBox vBox1, vBox2;
    private Group connection;

    public ConnectedPair(VBox vBox1, VBox vBox2, Group connection) {
        this.vBox1 = vBox1;
        this.vBox2 = vBox2;
        this.connection = connection;
    }
}
