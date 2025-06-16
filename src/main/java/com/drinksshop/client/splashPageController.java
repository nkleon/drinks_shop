package com.drinksshop.client;

import com.drinksshop.shared.Pageswitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class splashPageController {

    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnCustomer;

    @FXML
    private VBox vboxSplashPage;

    @FXML
    void openAdminPage(ActionEvent event) throws IOException {
        new Pageswitch(vboxSplashPage,"adminLoginPage.fxml");


    }

    @FXML
    void openLoginPage(ActionEvent event) throws IOException {
        new Pageswitch(vboxSplashPage,"loginPg.fxml");

    }

}
