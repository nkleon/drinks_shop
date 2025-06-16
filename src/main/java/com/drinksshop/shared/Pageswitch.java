package com.drinksshop.shared;

import com.drinksshop.client.StartExampleGUIScreen;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public class Pageswitch {

    public Pageswitch(VBox currentVBox, String fxml) throws IOException {
        VBox nextVBox= FXMLLoader.load(Objects.requireNonNull(StartExampleGUIScreen.class.getResource(fxml)));
        currentVBox.getChildren().removeAll();
        currentVBox.getChildren().setAll(nextVBox);
    }
}
