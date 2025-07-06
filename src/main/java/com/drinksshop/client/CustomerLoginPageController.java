package com.drinksshop.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

import java.util.Objects;

public class CustomerLoginPageController {

    @FXML
    TextField txtEmail, txtPassword;

    @FXML
    ComboBox<String> cBoxBranch;

    public void initialize(){
        try{
            for (String s : ServerConnection.dbFunctions().getStringColumn("branch_name", "branches", null)){
                cBoxBranch.getItems().add(s);
            }
        } catch (Exception e){

        }
    }

    public void login(ActionEvent actionEvent){
        if (txtEmail.getLength() == 0 || txtPassword.getLength() == 0 || cBoxBranch.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank fields!");
            alert.setContentText("Ensure all fields are filled");
            alert.showAndWait();
        } else {
            try {
                String customer_email = txtEmail.getText();
                String customer_password = txtPassword.getText();
                if (ServerConnection.dbFunctions().checkCustomerCredentials(customer_email, customer_password)) {
                    for (Integer i : ServerConnection.dbFunctions().getIntegerColumn("customer_id", "customers", "customer_email = '" + customer_email + "'")){
                        GlobalVariables.customer_id = i;
                    }
                    for (Integer i : ServerConnection.dbFunctions().getIntegerColumn("branch_id", "branches", "branch_name = '" + cBoxBranch.getValue() + "'")){
                        GlobalVariables.branch_id = i;
                    }

                    new Alert(Alert.AlertType.CONFIRMATION, "Successful login", ButtonType.OK).showAndWait();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CustomerLanding.fxml")));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unsuccessful login", ButtonType.OK).showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void goToRegisterScreen(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterScreen.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToSplashScreen(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SplashPage.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exitApp(ActionEvent actionEvent){
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
