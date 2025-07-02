package com.drinksshop.client;

import com.drinksshop.shared.DBFunctions;
import com.drinksshop.shared.SalesOrderReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.List;

public class SalesOrderReportController {

    @FXML private TableView<SalesOrderReport> salesOrderTable;
    @FXML private TableColumn<SalesOrderReport, Integer> orderIdCol;
    @FXML private TableColumn<SalesOrderReport, LocalDate> orderDateCol;
    @FXML private TableColumn<SalesOrderReport, String> customerEmailCol;
    @FXML private TableColumn<SalesOrderReport, String> branchNameCol;
    @FXML private TableColumn<SalesOrderReport, String> drinkNameCol;
    @FXML private TableColumn<SalesOrderReport, Integer> orderQuantityCol;

    private DBFunctions db;
    private ObservableList<SalesOrderReport> orderData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 10800);
            db = (DBFunctions) registry.lookup("DBFunctions");
        } catch (Exception e) {
            showAlert("Error", "Failed to connect to server: " + e.getMessage());
        }

        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        customerEmailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        branchNameCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        drinkNameCol.setCellValueFactory(new PropertyValueFactory<>("drinkName"));
        orderQuantityCol.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));

        salesOrderTable.setItems(orderData);

        loadOrderData();
    }

    @FXML
    private void onRefreshSalesOrder() {
        loadOrderData();
    }

    private void loadOrderData() {
        if (db == null) return;
        try {
            List<SalesOrderReport> report = db.getSalesOrderReport();
            orderData.setAll(report);
        } catch (Exception e) {
            showAlert("Error", "Failed to load order data: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}