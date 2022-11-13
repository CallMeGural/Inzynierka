package pl.gf.umlcd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;

public final class Data {

    private static Data instance;
    public ObservableList<ClassEntity> entityList = FXCollections.observableArrayList();
    public ObservableList<ClassEntity> pickedPair = FXCollections.observableArrayList();
    public ObservableList<ConnectedPair> connectedPairs = FXCollections.observableArrayList();
    public Group pickedConnection;

    //public Data() {}

    private Data() {}

    public static Data getInstance() {
        if(instance == null) instance = new Data();
        return instance;
    }
}

