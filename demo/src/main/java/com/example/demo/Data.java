package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;

public class Data {
    public ObservableList<ClassEntity> entityList = FXCollections.observableArrayList();
    public ObservableList<ClassEntity> pickedPair = FXCollections.observableArrayList();
    //public ObservableList<Group> connectionList = FXCollections.observableArrayList();
    public ObservableList<ConnectedPair> connectedPairs = FXCollections.observableArrayList();
    public Group pickedConnection;

    public Data() {}
}
