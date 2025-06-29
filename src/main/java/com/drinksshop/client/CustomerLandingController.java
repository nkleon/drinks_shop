package com.drinksshop.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class CustomerLandingController {

    @FXML
    Label lblCustomerName, lblBranchName, lblDate;

    String strDate = LocalDate.now().toString();
    String strTime = LocalTime.now().toString();

    public void initialize(){
        try {
            lblCustomerName.setText(ServerConnection.dbFunctions().getStringColumn("customer_name", "customers", "customer_id = " + GlobalVariables.customer_id).get(0));
            lblBranchName.setText(ServerConnection.dbFunctions().getStringColumn("branch_name", "branches", "branch_id = " + GlobalVariables.branch_id).get(0));
            strTime = strTime.substring(0, 8);
            lblDate.setText(strDate + " " + strTime);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exitApplication(ActionEvent actionEvent){
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void goToOrderPage(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OrderPage.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToOrderHistoryPage(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ClientOrderHistoryPage.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
