package pl.gf.umlcd.tests;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.gf.umlcd.ClassEntity;
import pl.gf.umlcd.OtherClassEntity;
import pl.gf.umlcd.OtherClassType;

import static org.junit.jupiter.api.Assertions.*;

class OtherClassEntityTest {
    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }

    @Test
    void testSetNodesToVBox() {
        OtherClassEntity entity = new OtherClassEntity(new Label(), OtherClassType.ENUM);
        entity.setNodesToVBox();
        Assertions.assertEquals(6,entity.getVBox().getChildren().size());
    }
}