package com.drinksshop.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

public class OrderPageController {

    @FXML
    ComboBox<String> cBoxDrinkName;

    @FXML
    TextField txtDrinkID, txtDrinkPrice, txtAvailableStock, txtOrderQuantity, txtTotalCost;

    public void initialize(){
        try {
            for (String str : ServerConnection.dbFunctions().getStringColumn("d.drink_name", "drinks d, stock s", "d.drink_id = s.drink_id AND s.drink_stock > 0 AND s.branch_id = " + GlobalVariables.branch_id)){
                cBoxDrinkName.getItems().add(str);
            }


            TextFormatter<String> numericFormatter = new TextFormatter<>(change -> {
                String newText = change.getControlNewText();
                if (newText.matches("[1-9][0-9]*") || newText.isEmpty()) {
                    return change;
                } else {
                    return null;
                }
            });

            txtOrderQuantity.setTextFormatter(numericFormatter);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onSelectDrink(ActionEvent actionEvent){
        try {
            String drinkname = cBoxDrinkName.getValue();
            int drinkID = ServerConnection.dbFunctions().getIntegerColumn("drink_id", "drinks", "drink_name = '" + drinkname+"'").get(0);
            txtDrinkID.setText(String.valueOf(drinkID));
            int drinkPrice = ServerConnection.dbFunctions().getIntegerColumn("drink_price", "drinks", "drink_id = " + drinkID).get(0);
            txtDrinkPrice.setText(String.valueOf(drinkPrice));
            int availableStock = ServerConnection.dbFunctions().getIntegerColumn("drink_stock", "stock", "branch_id = "+GlobalVariables.branch_id).get(0);
            txtAvailableStock.setText(String.valueOf(availableStock));
            if (txtOrderQuantity.getLength() > 0){
                txtTotalCost.setText(String.valueOf((Integer.parseInt(txtDrinkPrice.getText()) * Integer.parseInt(txtOrderQuantity.getText()))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSelectQuantity(KeyEvent keyEvent){
        if (txtOrderQuantity.getLength() != 0){
            txtTotalCost.setText(String.valueOf((Integer.parseInt(txtDrinkPrice.getText()) * Integer.parseInt(txtOrderQuantity.getText()))));
        } else {
            txtTotalCost.clear();
        }
    }

    public void goToCustomerLanding(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CustomerLanding.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeDrinkOrder(ActionEvent actionEvent){
        if (cBoxDrinkName.getValue() == null || txtOrderQuantity.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank fields!");
            alert.setContentText("Ensure all fields are filled");
            alert.showAndWait();
        } else if (Integer.parseInt(txtOrderQuantity.getText()) > Integer.parseInt(txtAvailableStock.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stock level!");
            alert.setContentText("You cannot order higher than the available stock");
            alert.showAndWait();
        } else {
            Dialog dialog = new Dialog<>();
            dialog.setContentText("Make a payment of Sh."+txtTotalCost.getText());
            dialog.setHeaderText("Proceed to make payment");
            dialog.setTitle("Payment");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int drinkID = Integer.parseInt(txtDrinkID.getText());
                    int orderQuantity = Integer.parseInt(txtOrderQuantity.getText());
                    if (ServerConnection.dbFunctions().addNewOrder(GlobalVariables.branch_id, GlobalVariables.customer_id, drinkID, orderQuantity)){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Success!");
                        alert.setContentText("Order made successfully");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Failure!");
                        alert.setContentText("Making an order was unsuccessful. Try again");
                        alert.showAndWait();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }
}
