package com.drinksshop.client;

import com.drinksshop.shared.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.List;

public class AdminClientFX {

    private DBFunctions db;

    // Branch Sales Table
    private TableView<SalesByBranch> branchTable;
    private ObservableList<SalesByBranch> branchData;

    // Stock Table
    private TableView<StockLevels> stockTable;
    private ObservableList<StockLevels> stockData;

    // Sales Order Table
    private TableView<SalesOrderReport> salesOrderTable;
    private ObservableList<SalesOrderReport> salesOrderData;

    // Low Stock Table
    private TableView<StockAlert> stockAlertTable;
    private ObservableList<StockAlert> stockAlertData;

    // Restock fields
    private TextField drinkIdField;
    private TextField branchIdField;
    private TextField quantityField;
    private Label restockStatusLabel;

    // Add Admin fields
    private TextField adminNameField;
    private PasswordField adminPasswordField;
    private Label addAdminStatusLabel;

    // Total sales label
    private Label totalSalesLabel = new Label();

    public AdminClientFX() throws Exception {
        // Connect to RMI
        Registry registry = LocateRegistry.getRegistry("localhost", 10800);
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

        Tab salesOrderTab = new Tab("Sales by Order", createSalesOrderPane());
        salesOrderTab.setClosable(false);

        Tab stockAlertTab = new Tab("Low Stock Alerts", createStockAlertPane());
        stockAlertTab.setClosable(false);

        Tab totalSalesTab = new Tab("Total Sales", createTotalSalesPane());
        totalSalesTab.setClosable(false);

        tabPane.getTabs().addAll(
                viewReportTab, restockTab, viewStockTab, addAdminTab,
                salesOrderTab, stockAlertTab, totalSalesTab
        );

        return tabPane;
    }
    private VBox createSalesOrderPane() {
        TableView<SalesOrderReport> orderTable = new TableView<>();
        ObservableList<SalesOrderReport> orderData = FXCollections.observableArrayList();

        TableColumn<SalesOrderReport, Integer> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<SalesOrderReport, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<SalesOrderReport, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<SalesOrderReport, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));

        TableColumn<SalesOrderReport, String> branchCol = new TableColumn<>("Branch");
        branchCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));

        TableColumn<SalesOrderReport, String> drinkCol = new TableColumn<>("Drink");
        drinkCol.setCellValueFactory(new PropertyValueFactory<>("drinkName"));

        TableColumn<SalesOrderReport, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));

        TableColumn<SalesOrderReport, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("orderAmount"));

        orderTable.getColumns().addAll(idCol, dateCol, customerCol, emailCol, branchCol, drinkCol, qtyCol, amountCol);
        orderTable.setItems(orderData);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> {
            orderData.clear();
            try {
                List<SalesOrderReport> list = db.getSalesOrderReport();
                orderData.addAll(list);
            } catch (Exception ex) {
                showError("Could not load sales order data:\n" + ex.getMessage());
            }
        });

        VBox vbox = new VBox(10, orderTable, refreshBtn);
        vbox.setPadding(new Insets(10));

        // Optionally, load data on pane creation
        refreshBtn.fire();

        return vbox;
    }

    // 1. Sales by Branch
    private VBox createBranchTablePane() {
        branchTable = new TableView<>();
        branchData = FXCollections.observableArrayList();

        TableColumn<SalesByBranch, Integer> idCol = new TableColumn<>("Branch ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("branchId"));

        TableColumn<SalesByBranch, String> nameCol = new TableColumn<>("Branch Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));

        TableColumn<SalesByBranch, Integer> ordersCol = new TableColumn<>("Total Orders");
        ordersCol.setCellValueFactory(new PropertyValueFactory<>("branchOrderQuantity"));

        TableColumn<SalesByBranch, Double> salesCol = new TableColumn<>("Total Sales");
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

    // 2. Sales by Order (with customer and branch info)
    private TableView<SalesOrderReport> orderTable;
    private ObservableList<SalesOrderReport> orderData;

    private VBox createOrderTablePane() {
        orderTable = new TableView<>();
        orderData = FXCollections.observableArrayList();

        TableColumn<SalesOrderReport, Integer> idCol = new TableColumn<>("Order ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<SalesOrderReport, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<SalesOrderReport, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<SalesOrderReport, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));

        TableColumn<SalesOrderReport, String> branchCol = new TableColumn<>("Branch");
        branchCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));

        TableColumn<SalesOrderReport, String> drinkCol = new TableColumn<>("Drink");
        drinkCol.setCellValueFactory(new PropertyValueFactory<>("drinkName"));

        TableColumn<SalesOrderReport, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));

        TableColumn<SalesOrderReport, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("orderAmount"));

        orderTable.getColumns().addAll(idCol, dateCol, customerCol, emailCol, branchCol, drinkCol, qtyCol, amountCol);
        orderTable.setItems(orderData);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> loadOrderData());

        VBox vbox = new VBox(10, orderTable, refreshBtn);
        vbox.setPadding(new Insets(10));

        loadOrderData();

        return vbox;
    }
    private void loadOrderData() {
        orderData.clear();
        try {
            List<SalesOrderReport> list = db.getSalesOrderReport();
            orderData.addAll(list);
        } catch (Exception e) {
            showError("Could not load sales order data:\n" + e.getMessage());
        }
    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadSalesOrderData() {
        try {
            List<SalesOrderReport> report = db.getSalesOrderReport();
            salesOrderData.setAll(report);
        } catch (Exception e) {
            showAlert("Error", "Failed to load sales order data: " + e.getMessage());
        }
    }

    // 3. Low Stock Alerts
    private VBox createStockAlertPane() {
        TableView<StockAlert> stockAlertTable = new TableView<>();
        ObservableList<StockAlert> stockAlertData = FXCollections.observableArrayList();

        stockAlertTable.setItems(stockAlertData);

        TableColumn<StockAlert, String> drinkCol = new TableColumn<>("Drink");
        drinkCol.setCellValueFactory(new PropertyValueFactory<>("drinkName"));

        TableColumn<StockAlert, String> branchCol = new TableColumn<>("Branch");
        branchCol.setCellValueFactory(new PropertyValueFactory<>("branchName"));

        TableColumn<StockAlert, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stockLevel"));

        TableColumn<StockAlert, Integer> thresholdCol = new TableColumn<>("Threshold");
        thresholdCol.setCellValueFactory(new PropertyValueFactory<>("threshold"));

        stockAlertTable.getColumns().addAll(drinkCol, branchCol, stockCol, thresholdCol);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> loadStockAlertData(stockAlertData));

        VBox vbox = new VBox(10, stockAlertTable, refreshBtn);
        vbox.setPadding(new Insets(10));

        loadStockAlertData(stockAlertData);

        return vbox;
    }

    // Sample loader method
    private void loadStockAlertData(ObservableList<StockAlert> stockAlertData) {
        stockAlertData.clear();
        try {
            // Replace with your RMI/db fetch logic:
            List<StockAlert> list = db.getStockAlerts();
            stockAlertData.addAll(list);
        } catch (Exception e) {
            showError("Could not load stock alert data:\n" + e.getMessage());
        }
    }

    

    // 4. Total Sales Report
    private VBox createTotalSalesPane() {
        VBox vbox = new VBox(10, totalSalesLabel);
        vbox.setPadding(new Insets(20));
        updateTotalSales();
        return vbox;
    }

    private void updateTotalSales() {
        try {
            double total = db.getTotalSales();
            totalSalesLabel.setText("Total Sales: $" + String.format("%.2f", total));
        } catch (Exception e) {
            totalSalesLabel.setText("Total Sales: error");
        }
    }

    // 5. Stock Table (existing)
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

    // Restock
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
                loadStockAlertData(stockAlertData);
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

    // Add Admin
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