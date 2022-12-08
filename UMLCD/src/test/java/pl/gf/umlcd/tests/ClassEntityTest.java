package pl.gf.umlcd.tests;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.gf.umlcd.*;


class ClassEntityTest {
    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }

    @Test
    void testSetNodesToVBox() {
        ClassEntity entity = new ClassEntity();
        entity.setNodesToVBox();
        Assertions.assertEquals(5,entity.getVBox().getChildren().size());
    }

    @Test
    void testInitializeEntityAddedToPane() {
        ClassEntity entity = new ClassEntity();
        Pane pane = new Pane();
        entity.initializeEntity(/*new DraggableMaker(),*/ new MainViewController(),pane);
        Assertions.assertTrue(pane.getChildren().contains(entity.getVBox()));
    }
    @Test
    void testInitializeEntityAddedDataList() {
        ClassEntity entity = new ClassEntity();
        Data data = Data.getInstance();
        entity.initializeEntity(/*new DraggableMaker(),*/ new MainViewController(),new Pane());
        Assertions.assertTrue(data.getEntities().contains(entity));
    }

    @Test
    void testPickEntity() {
        ClassEntity entity = new ClassEntity();
        MainViewController mainController = new MainViewController();
        ObjectViewController controller = new ObjectViewController();
        Pane pane = new Pane();
        entity.initializeEntity(/*new DraggableMaker(),*/mainController,pane);
        entity.pickEntity(controller, "class0");
        Assertions.assertEquals(Integer.parseInt(entity.getVBox().getId().substring(5)),controller.getPickedId());
    }
}