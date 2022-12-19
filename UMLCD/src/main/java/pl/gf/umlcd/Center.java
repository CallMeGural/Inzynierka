package pl.gf.umlcd;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
public class Center {
    private ReadOnlyDoubleWrapper centerX = new ReadOnlyDoubleWrapper();
    private ReadOnlyDoubleWrapper centerY = new ReadOnlyDoubleWrapper();

    //bounds - rozmiar i polozenie
    public Center(VBox vb) {
        calcCenter(vb.getBoundsInParent());
        vb.boundsInParentProperty().addListener(
                (observableValue, oldBounds, bounds) -> calcCenter(bounds));
    }


    private void calcCenter(Bounds bounds) {
        centerX.set(bounds.getMinX() + bounds.getWidth()  / 2);
        centerY.set(bounds.getMinY() + bounds.getHeight() / 2);
    }


}