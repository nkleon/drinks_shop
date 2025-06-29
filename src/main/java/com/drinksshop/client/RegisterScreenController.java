package com.drinksshop.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

import java.util.Objects;

public class RegisterScreenController {

    @FXML
    TextField customer_phone, customer_name, customer_email, customer_password;

    public void initialize(){
        TextFormatter<String> customer_email_formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 30) {
                return change; // accept the change
            } else {
                return null; // reject the change
            }
        });

        TextFormatter<String> customer_name_formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 50) {
                return change; // accept the change
            } else {
                return null; // reject the change
            }
        });

        TextFormatter<String> customer_phone_formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 10) {
                return change; // accept the change
            } else {
                return null; // reject the change
            }
        });

        TextFormatter<String> customer_password_formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() <= 8) {
                return change; // accept the change
            } else {
                return null; // reject the change
            }
        });

        customer_email.setTextFormatter(customer_email_formatter);
        customer_name.setTextFormatter(customer_name_formatter);
        customer_phone.setTextFormatter(customer_phone_formatter);
        customer_password.setTextFormatter(customer_password_formatter);
    }

    public void goToLoginScreen(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CustomerLoginPage.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerNewCustomer(ActionEvent actionEvent){
        if (customer_email.getLength() == 0 || customer_name.getLength() == 0 || customer_phone.getLength() == 0 || customer_password.getLength() == 0 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank fields!");
            alert.setContentText("Ensure all fields are filled");
            alert.showAndWait();
        } else {
            try {
                String customerEmail = customer_email.getText();
                String customerPhone = customer_phone.getText();
                String customerName = customer_name.getText();
                String customerPassword = customer_password.getText();
                if (ServerConnection.dbFunctions().addNewCustomer(customerEmail, customerName, customerPhone, customerPassword)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Successful registration", ButtonType.OK).showAndWait();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CustomerLanding.fxml")));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Unsuccessful registration", ButtonType.OK).showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}