package com.drinksshop.server;

import com.drinksshop.shared.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBFunctionsImplementation extends UnicastRemoteObject implements DBFunctions {

    private Connection connection;

    public DBFunctionsImplementation() throws RemoteException{
        super();
    }

    @Override
    public List<SalesByBranch> getSalesByBranch() throws SQLException, RemoteException {
        List<SalesByBranch> selections = new ArrayList<>();
        String query = "SELECT b.branch_id, b.branch_name, SUM(o.order_quantity) AS branch_order_quantity, SUM(o.order_quantity * d.drink_price) AS branch_total_sales " +
                "FROM orders o " +
                "JOIN drinks d ON o.drink_id = d.drink_id " +
                "RIGHT JOIN branches b ON o.branch_id = b.branch_id " +
                "GROUP BY b.branch_id;";

        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            selections.add(new SalesByBranch(
                    resultSet.getInt("branch_id"),
                    resultSet.getString("branch_name"),
                    resultSet.getInt("branch_order_quantity"),
                    resultSet.getInt("branch_total_sales")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return selections;
    }

    @Override
    public List<SalesByCustomer> getSalesByCustomer() throws SQLException, RemoteException{
        List<SalesByCustomer> selections = new ArrayList<>();
        String query = "SELECT c.customer_id, c.customer_name, SUM(o.order_quantity) AS customer_order_quantity, SUM(o.order_quantity * d.drink_price) AS customer_total_purchases " +
                "FROM orders o " +
                "JOIN drinks d ON o.drink_id = d.drink_id " +
                "RIGHT JOIN customers c ON o.customer_id = c.customer_id " +
                "GROUP BY c.customer_id;";

        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            selections.add(new SalesByCustomer(
                    resultSet.getInt("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getInt("customer_order_quantity"),
                    resultSet.getInt("customer_total_purchases")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return selections;
    }

    @Override
    public List<SalesByDrink> getSalesByDrink()  throws SQLException, RemoteException{
        List<SalesByDrink> selections = new ArrayList<>();
        String query = "SELECT d.drink_id, d.drink_name, SUM(o.order_quantity) AS drink_order_quantity, SUM(o.order_quantity * d.drink_price) AS drink_total_sales " +
                "FROM orders o " +
                "RIGHT JOIN drinks d ON o.drink_id = d.drink_id " +
                "GROUP BY d.drink_id;";

        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            selections.add(new SalesByDrink(
                    resultSet.getInt("drink_id"),
                    resultSet.getString("drink_name"),
                    resultSet.getInt("drink_order_quantity"),
                    resultSet.getInt("drink_total_sales")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return selections;
    }

    @Override
    public List<OrdersRecords> getSalesRecords()  throws SQLException, RemoteException{
        List<OrdersRecords> selections = new ArrayList<>();
        String query = "SELECT o.order_id, o.order_date, b.branch_name, c.customer_name, d.drink_name, o.order_quantity, (o.order_quantity * d.drink_price) AS total_price " +
                "FROM orders o " +
                "JOIN drinks d on d.drink_id = o.drink_id " +
                "JOIN branches b on b.branch_id = o.branch_id " +
                "JOIN customers c on c.customer_id = o.customer_id " +
                "ORDER BY o.order_date DESC;";

        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            selections.add(new OrdersRecords(
                    resultSet.getInt("order_id"),
                    resultSet.getDate("order_date").toLocalDate(),
                    resultSet.getString("branch_name"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("drink_name"),
                    resultSet.getInt("order_quantity"),
                    resultSet.getInt("total_price")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return selections;
    }

    @Override
    public List<StockLevels> getStockLevels()  throws SQLException, RemoteException{
        List<StockLevels> selections = new ArrayList<>();
        String query = "SELECT d.drink_name, b.branch_name, s.drink_stock " +
                "FROM stock s " +
                "JOIN branches b on b.branch_id = s.branch_id " +
                "JOIN drinks d on d.drink_id = s.drink_id;";

        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            selections.add(new StockLevels(
                    resultSet.getString("drink_name"),
                    resultSet.getString("branch_name"),
                    resultSet.getInt("drink_stock")
            ));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return selections;
    }

    @Override
    public boolean addNewCustomer (String customer_email, String customer_name, String customer_phone, String customer_password)  throws SQLException, RemoteException {
        boolean executedSuccessfully;
        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customers (customer_email, customer_name, customer_phone, customer_password) " +
                "VALUES (?,?,?,?)");
        preparedStatement.setString(1, customer_email);
        preparedStatement.setString(2, customer_name);
        preparedStatement.setString(3, customer_phone);
        preparedStatement.setString(4, customer_password);
        if (preparedStatement.executeUpdate() > 0){
            executedSuccessfully = true;
        } else {
            executedSuccessfully = false;
        }
        preparedStatement.close();
        connection.close();
        return executedSuccessfully;
    }

    @Override
    public boolean addNewBranch (String branch_name)  throws SQLException, RemoteException {
        boolean executedSuccessfully;
        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO branches (branch_name) VALUES (?)");
        preparedStatement.setString(1, branch_name);
        if (preparedStatement.executeUpdate() > 0){
            executedSuccessfully = true;
        } else {
            executedSuccessfully = false;
        }
        preparedStatement.close();
        connection.close();
        return executedSuccessfully;
    }

    @Override
    public boolean addNewDrink(String drink_name, int drink_price)  throws SQLException, RemoteException{
        boolean executedSuccessfully;
        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO drinks (drink_name, drink_price) VALUES (?,?)");
        preparedStatement.setString(1, drink_name);
        preparedStatement.setInt(2, drink_price);
        if (preparedStatement.executeUpdate() > 0){
            executedSuccessfully = true;
        } else {
            executedSuccessfully = false;
        }
        preparedStatement.close();
        connection.close();
        return executedSuccessfully;
    }

    @Override
    public boolean addNewOrder(int branch_id, int customer_id, int drink_id, int order_quantity)  throws SQLException, RemoteException {
        boolean executedSuccessfully;
        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatementOne = connection.prepareStatement("INSERT INTO orders (order_date, branch_id, customer_id, drink_id, order_quantity) " +
                "VALUES (?,?,?,?,?)");
        preparedStatementOne.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
        preparedStatementOne.setInt(2, branch_id);
        preparedStatementOne.setInt(3, customer_id);
        preparedStatementOne.setInt(4, drink_id);
        preparedStatementOne.setInt(5, order_quantity);
        PreparedStatement preparedStatementTwo = connection.prepareStatement("UPDATE stock SET drink_stock = (drink_stock - 1) WHERE drink_id = ? AND branch_id = ?");
        preparedStatementTwo.setInt(1, drink_id);
        preparedStatementTwo.setInt(2, branch_id);
        if ((preparedStatementOne.executeUpdate() > 0) && (preparedStatementTwo.executeUpdate() > 0)){
            executedSuccessfully = true;
        } else {
            executedSuccessfully = false;
        }
        preparedStatementOne.close();
        preparedStatementTwo.close();
        connection.close();
        return executedSuccessfully;
    }

    @Override
    public boolean editStock(int drink_id, int branch_id, int drink_stock)  throws SQLException, RemoteException {
        boolean executedSuccessfully;
        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE stock SET drink_stock = ? WHERE drink_id = ? AND branch_id = ?");
        preparedStatement.setInt(1, drink_stock);
        preparedStatement.setInt(2, drink_id);
        preparedStatement.setInt(3, branch_id);
        if (preparedStatement.executeUpdate() > 0){
            executedSuccessfully = true;
        } else {
            executedSuccessfully = false;
        }
        preparedStatement.close();
        connection.close();
        return executedSuccessfully;
    }

    @Override
    public boolean checkCustomerCredentials(String customer_email, String customer_password)  throws SQLException, RemoteException {
        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customers WHERE customer_email = ? AND customer_password = ?");
        preparedStatement.setString(1, customer_email);
        preparedStatement.setString(2, customer_password);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean validLogin = resultSet.next();
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return validLogin;
    }

    @Override
    public boolean checkAdminCredentials(String admin_name, String admin_password)  throws SQLException, RemoteException {
        connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM admins WHERE admin_name = ? AND admin_password = ?");
        preparedStatement.setString(1, admin_name);
        preparedStatement.setString(2, admin_password);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean validLogin = resultSet.next();
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return validLogin;
    }

    @Override
    public ArrayList<Integer> getIntegerColumn(String field_name, String table_name, String conditions) throws SQLException, RemoteException{
        ArrayList<Integer> arrayList = new ArrayList<>();
        String query;
        connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        if (conditions == null){
            query = "SELECT " + field_name + " FROM " + table_name;
        } else {
            query = "SELECT " + field_name + " FROM " + table_name + " WHERE " + conditions;
        }
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            arrayList.add(resultSet.getInt(field_name));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return arrayList;
    }

    @Override
    public ArrayList<String> getStringColumn(String field_name, String table_name, String conditions) throws SQLException, RemoteException{
        ArrayList<String> arrayList = new ArrayList<>();
        String query;
        connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        if (conditions == null){
            query = "SELECT " + field_name + " FROM " + table_name;
        } else {
            query = "SELECT " + field_name + " FROM " + table_name + " WHERE " + conditions;
        }
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            arrayList.add(resultSet.getString(field_name));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return arrayList;
    }
}
