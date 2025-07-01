package com.drinksshop.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class Pageswitch {
    public Pageswitch(VBox currentVBox, String fxml) throws Exception {
        VBox nextVBox = FXMLLoader.load(Objects.requireNonNull(ClientMain.class.getResource(fxml)));
        currentVBox.getChildren().removeAll();
        currentVBox.getChildren().setAll(nextVBox);
    }
}