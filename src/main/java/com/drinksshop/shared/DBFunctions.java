package com.drinksshop.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DBFunctions extends Remote {
    List<SalesByBranch> getSalesByBranch() throws SQLException, RemoteException;

    List<SalesByCustomer> getSalesByCustomer() throws SQLException, RemoteException;

    List<SalesByDrink> getSalesByDrink() throws SQLException, RemoteException;

    List<OrdersRecords> getSalesRecords() throws SQLException, RemoteException;

    List<StockLevels> getStockLevels() throws SQLException, RemoteException;

    List<ClientOrderHistory> clientGetOrderHistory(int customer_id) throws SQLException, RemoteException;

    boolean addNewCustomer(String customer_email, String customer_name, String customer_phone, String customer_password) throws SQLException, RemoteException;

    boolean addNewBranch(String branch_name) throws SQLException, RemoteException;

    boolean addNewDrink(String drink_name, int drink_price) throws SQLException, RemoteException;

    boolean addNewOrder(int branch_id, int customer_id, int drink_id, int order_quantity) throws SQLException, RemoteException;

    boolean editStock(int drink_id, int branch_id, int drink_stock) throws SQLException, RemoteException;

    boolean checkCustomerCredentials(String customer_email, String customer_password) throws SQLException, RemoteException;

    boolean checkAdminCredentials(String admin_name, String admin_password) throws SQLException, RemoteException;

    ArrayList<Integer> getIntegerColumn(String field_name, String table_name, String conditions) throws SQLException, RemoteException;

    ArrayList<String> getStringColumn(String field_name, String table_name, String conditions) throws SQLException, RemoteException;

    boolean restockDrink(int drinkId, int branchId, int quantity) throws RemoteException;

    boolean addNewAdmin(String adminName, String adminPassword) throws RemoteException;

    List<SalesOrderReport> getSalesOrderReport() throws RemoteException;

    double getTotalSales() throws RemoteException;

    List<StockLevels> getLowStockAlerts() throws RemoteException;

    List<StockAlert> getStockAlerts() throws RemoteException;
}