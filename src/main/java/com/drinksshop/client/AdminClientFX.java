package com.drinksshop.client;

import com.drinksshop.shared.DBFunctions;
import com.drinksshop.shared.SalesByBranch;
import com.drinksshop.shared.StockLevels;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class AdminClientFX {

    private DBFunctions db;

    // Branch Sales Table
    private TableView<SalesByBranch> branchTable;
    private ObservableList<SalesByBranch> branchData;

    // Stock Table
    private TableView<StockLevels> stockTable;
    private ObservableList<StockLevels> stockData;

    // Restock fields
    private TextField drinkIdField;
    private TextField branchIdField;
    private TextField quantityField;
    private Label restockStatusLabel;


    private TextField adminNameField;
    private PasswordField adminPasswordField;
    private Label addAdminStatusLabel;

    public AdminClientFX() throws Exception {
        // Connecting to RMI
        Registry registry = LocateRegistry.getRegistry("localhost", 10800); // Change to server IP if needed
        db = (DBFunctions) registry.lookup("DBFunctions");
    }

    public Parent getDashboardRoot() {
        TabPane tabPane = new TabPane();

        Tab viewReportTab = new Tab("View Sales by Branch", createBranchTablePane());
        viewReportTab.setClosable(false);

        Tab restockTab = new Tab("Restock Drink", createRestockPane());
        restockTab.setClosable(false);

        Tab viewStockTab = new Tab("View Stock", createStockPane());
        viewStockTab.setClosable(false);

        Tab addAdminTab = new Tab("Add Admin", createAddAdminPane());
        addAdminTab.setClosable(false);

        tabPane.getTabs().addAll(viewReportTab, restockTab, viewStockTab, addAdminTab);

        return tabPane;
    }

    private VBox createBranchTablePane() {
        branchTable = new TableView<>();
        branchData = FXCollections.observableArrayList();

        TableColumn<SalesByBranch, Integer> idCol = new TableColumn<>("Branch ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("branchId"));

        TableColumn<SalesByBranch, String> nameCol = new TableColumn<>("Branch Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));

        TableColumn<SalesByBranch, Integer> ordersCol = new TableColumn<>("Total Orders");
        ordersCol.setCellValueFactory(new PropertyValueFactory<>("branchOrderQuantity"));

        TableColumn<SalesByBranch, Integer> salesCol = new TableColumn<>("Total Sales");
        salesCol.setCellValueFactory(new PropertyValueFactory<>("branchTotalSales"));

        branchTable.getColumns().addAll(idCol, nameCol, ordersCol, salesCol);
        branchTable.setItems(branchData);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> loadBranchData());

        VBox vbox = new VBox(10, branchTable, refreshBtn);
        vbox.setPadding(new Insets(10));

        loadBranchData();

        return vbox;
    }

    private void loadBranchData() {
        try {
            List<SalesByBranch> report = db.getSalesByBranch();
            branchData.setAll(report);
        } catch (Exception e) {
            showAlert("Error", "Failed to load branch data: " + e.getMessage());
        }
    }

    private VBox createStockPane() {
        stockTable = new TableView<>();
        stockData = FXCollections.observableArrayList();

        TableColumn<StockLevels, String> drinkCol = new TableColumn<>("Drink Name");
        drinkCol.setCellValueFactory(new PropertyValueFactory<>("drinkName"));

        TableColumn<StockLevels, String> branchCol = new TableColumn<>("Branch Name");
        branchCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));

        TableColumn<StockLevels, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("drinkStock"));

        stockTable.getColumns().addAll(drinkCol, branchCol, stockCol);
        stockTable.setItems(stockData);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> loadStockData());

        VBox vbox = new VBox(10, stockTable, refreshBtn);
        vbox.setPadding(new Insets(10));

        loadStockData();

        return vbox;
    }

    private void loadStockData() {
        try {
            List<StockLevels> stocks = db.getStockLevels();
            stockData.setAll(stocks);
        } catch (Exception e) {
            showAlert("Error", "Failed to load stock: " + e.getMessage());
        }
    }

    private VBox createRestockPane() {
        drinkIdField = new TextField();
        drinkIdField.setPromptText("Drink ID");

        branchIdField = new TextField();
        branchIdField.setPromptText("Branch ID");

        quantityField = new TextField();
        quantityField.setPromptText("Quantity to add");

        Button restockBtn = new Button("Restock");
        restockStatusLabel = new Label();

        restockBtn.setOnAction(e -> handleRestock());

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(new Label("Drink ID:"), 0, 0);
        form.add(drinkIdField, 1, 0);
        form.add(new Label("Branch ID:"), 0, 1);
        form.add(branchIdField, 1, 1);
        form.add(new Label("Quantity:"), 0, 2);
        form.add(quantityField, 1, 2);
        form.add(restockBtn, 1, 3);

        VBox vbox = new VBox(10, form, restockStatusLabel);
        vbox.setPadding(new Insets(20));
        return vbox;
    }

    private void handleRestock() {
        try {
            int drinkId = Integer.parseInt(drinkIdField.getText());
            int branchId = Integer.parseInt(branchIdField.getText());
            int qty = Integer.parseInt(quantityField.getText());

            boolean ok = db.restockDrink(drinkId, branchId, qty);
            if (ok) {
                restockStatusLabel.setText("Restock successful!");
                restockStatusLabel.setStyle("-fx-text-fill: green;");
                loadStockData();
            } else {
                restockStatusLabel.setText("Restock failed.");
                restockStatusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (NumberFormatException nfe) {
            restockStatusLabel.setText("Invalid input. Please enter valid numbers.");
            restockStatusLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            restockStatusLabel.setText("Error: " + e.getMessage());
            restockStatusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private VBox createAddAdminPane() {
        adminNameField = new TextField();
        adminNameField.setPromptText("Admin Name");

        adminPasswordField = new PasswordField();
        adminPasswordField.setPromptText("Password");

        Button addBtn = new Button("Add Admin");
        addAdminStatusLabel = new Label();

        addBtn.setOnAction(e -> handleAddAdmin());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Admin Name:"), 0, 0);
        grid.add(adminNameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(adminPasswordField, 1, 1);
        grid.add(addBtn, 1, 2);

        VBox vbox = new VBox(10, grid, addAdminStatusLabel);
        vbox.setPadding(new Insets(20));
        return vbox;
    }

    private void handleAddAdmin() {
        String adminName = adminNameField.getText();
        String adminPass = adminPasswordField.getText();
        if (adminName.isEmpty() || adminPass.isEmpty()) {
            addAdminStatusLabel.setText("Both fields required.");
            addAdminStatusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        try {
            boolean ok = db.addNewAdmin(adminName, adminPass);
            if (ok) {
                addAdminStatusLabel.setText("Admin added!");
                addAdminStatusLabel.setStyle("-fx-text-fill: green;");
            } else {
                addAdminStatusLabel.setText("Failed to add admin.");
                addAdminStatusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            addAdminStatusLabel.setText("Error: " + e.getMessage());
            addAdminStatusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}