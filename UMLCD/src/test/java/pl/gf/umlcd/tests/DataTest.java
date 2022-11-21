package pl.gf.umlcd.tests;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.ini4j.Wini;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.gf.umlcd.*;
import pl.gf.umlcd.connections.Association;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    Data data = Data.getInstance();
    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }

    @Test
    void saveClassEntityTest() throws IOException {
        if(new File("src/test/java/pl/gf/umlcd/tests/test.ini").exists())
            new File("src/test/java/pl/gf/umlcd/tests/test.ini").delete();
        Path path = Paths.get("src/test/java/pl/gf/umlcd/tests/test.ini");
        Files.createFile(path);
        File file = new File(String.valueOf(path));
        ClassEntity entity = new ClassEntity();
        entity.initializeEntity(new DraggableMaker(),new MainViewController(),
                Data.getInstance(),new Pane());
        Wini ini = new Wini(file);
        data.saveClassEntity(entity.getVbox(),ini);
        assertEquals(data.getClassCounter(),1);
    }

    @Test
    void saveOtherClassEntityTest() throws IOException {
        if(new File("src/test/java/pl/gf/umlcd/tests/test.ini").exists())
            new File("src/test/java/pl/gf/umlcd/tests/test.ini").delete();
        Path path = Paths.get("src/test/java/pl/gf/umlcd/tests/test.ini");
        Files.createFile(path);
        File file = new File(String.valueOf(path));
        OtherClassEntity entity = new OtherClassEntity(new Label(), OtherClassType.ENUM);
        entity.initializeEntity(new DraggableMaker(),new MainViewController(),
                Data.getInstance(),new Pane());
        Wini ini = new Wini(file);
        data.saveOtherClassEntity(entity.getVbox(),ini);
        assertEquals(data.getClassCounter(),1);
    }

    @Test
    void saveConnectionTest() throws IOException {
        if(new File("src/test/java/pl/gf/umlcd/tests/test.ini").exists())
            new File("src/test/java/pl/gf/umlcd/tests/test.ini").delete();
        Path path = Paths.get("src/test/java/pl/gf/umlcd/tests/test.ini");
        Files.createFile(path);
        File file = new File(String.valueOf(path));
        ClassEntity classEntity = new ClassEntity();
        OtherClassEntity otherEntity = new OtherClassEntity(new Label(), OtherClassType.ENUM);
        Association association = new Association();
        ConnectedPair connectedPair = new ConnectedPair(classEntity.getVbox(),
                otherEntity.getVbox(),association);
        Wini ini = new Wini(file);
        data.saveConnection(connectedPair,ini);
        assertEquals(data.getConnectionCounter(),1);
    }


}