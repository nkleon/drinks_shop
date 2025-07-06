package com.drinksshop.client;

import com.drinksshop.shared.ClientOrderHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ClientOrderHistoryController {

    @FXML
    private TableView<ClientOrderHistory> tblClientOrderHistory;

    @FXML
    private TableColumn<ClientOrderHistory, Integer> colOrderID, colOrderQuantity, colDrinkPrice, colTotalCost;

    @FXML
    private TableColumn<ClientOrderHistory, String> colBranchName, colDrinkName;

    @FXML
    private TableColumn<ClientOrderHistory, LocalDate> colOrderDate;

    public void initialize(){
        try{
            colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            colBranchName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
            colDrinkName.setCellValueFactory(new PropertyValueFactory<>("drinkName"));
            colOrderQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
            colDrinkPrice.setCellValueFactory(new PropertyValueFactory<>("drinkPrice"));
            colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            List<ClientOrderHistory> list = ServerConnection.dbFunctions().clientGetOrderHistory(GlobalVariables.customer_id);
            ObservableList<ClientOrderHistory> selections = FXCollections.observableArrayList(list);
            tblClientOrderHistory.setItems(selections);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToLandingPage(ActionEvent actionEvent){
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
}
