package com.drinksshop.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DBFunctions extends Remote {
    public List<SalesByBranch> getSalesByBranch() throws SQLException, RemoteException;

    public List<SalesByCustomer> getSalesByCustomer() throws SQLException, RemoteException;

    public List<SalesByDrink> getSalesByDrink() throws SQLException, RemoteException;

    public List<OrdersRecords> getSalesRecords() throws SQLException, RemoteException;

    public List<StockLevels> getStockLevels() throws SQLException, RemoteException;

    public List<ClientOrderHistory> clientGetOrderHistory(int customer_id) throws SQLException, RemoteException;

    public boolean addNewCustomer(String customer_email, String customer_name, String customer_phone, String customer_password) throws SQLException, RemoteException;

    public boolean addNewBranch(String branch_name) throws SQLException, RemoteException;

    public boolean addNewDrink(String drink_name, int drink_price) throws SQLException, RemoteException;

    public boolean addNewOrder(int branch_id, int customer_id, int drink_id, int order_quantity) throws SQLException, RemoteException;

    public boolean editStock(int drink_id, int branch_id, int drink_stock) throws SQLException, RemoteException;

    public boolean checkCustomerCredentials(String customer_email, String customer_password) throws SQLException, RemoteException;

    public boolean checkAdminCredentials(String admin_name, String admin_password) throws SQLException, RemoteException;

    public ArrayList<Integer> getIntegerColumn(String field_name, String table_name, String conditions) throws SQLException, RemoteException;

    public ArrayList<String> getStringColumn(String field_name, String table_name, String conditions) throws SQLException, RemoteException;
}
