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
        Assertions.assertEquals(5,entity.getVbox().getChildren().size());
    }

    @Test
    void testInitializeEntityAddedToPane() {
        ClassEntity entity = new ClassEntity();
        Pane pane = new Pane();
        entity.initializeEntity(new DraggableMaker(),new MainViewController(), Data.getInstance(),pane);
        Assertions.assertTrue(pane.getChildren().contains(entity.getVbox()));
    }
    @Test
    void testInitializeEntityAddedDataList() {
        ClassEntity entity = new ClassEntity();
        Data data = Data.getInstance();
        entity.initializeEntity(new DraggableMaker(),new MainViewController(),Data.getInstance(),new Pane());
        Assertions.assertTrue(data.entityList.contains(entity));
    }

    @Test
    void testPickEntity() {
        ClassEntity entity = new ClassEntity();
        MainViewController mainController = new MainViewController();
        ObjectDetailController controller = new ObjectDetailController();
        Pane pane = new Pane();
        entity.initializeEntity(new DraggableMaker(),mainController,
                Data.getInstance(),pane);
        entity.pickEntity(Data.getInstance(),controller,
                entity.getVbox(),"class0");
        Assertions.assertEquals(Integer.parseInt(entity.getVbox().getId().substring(5)),controller.pickedId);
    }
}