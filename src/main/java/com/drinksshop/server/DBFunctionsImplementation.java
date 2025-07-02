package com.drinksshop.server;

import com.drinksshop.shared.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBFunctionsImplementation extends UnicastRemoteObject implements DBFunctions {

    public DBFunctionsImplementation() throws RemoteException {
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
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                selections.add(new SalesByBranch(
                        resultSet.getInt("branch_id"),
                        resultSet.getString("branch_name"),
                        resultSet.getInt("branch_order_quantity"),
                        resultSet.getDouble("branch_total_sales")
                ));
            }
        }
        return selections;
    }
    @Override
    public List<StockAlert> getStockAlerts() throws RemoteException {
        // TODO: Replace with your actual logic (e.g., fetch from database)
        List<StockAlert> alerts = new ArrayList<>();
        // Example dummy data
        alerts.add(new StockAlert("Coke", "Nakuru", 5, 10));
        return alerts;
    }

    @Override
    public List<SalesByCustomer> getSalesByCustomer() throws SQLException, RemoteException {
        List<SalesByCustomer> selections = new ArrayList<>();
        String query = "SELECT c.customer_id, c.customer_name, SUM(o.order_quantity) AS customer_order_quantity, SUM(o.order_quantity * d.drink_price) AS customer_total_purchases " +
                "FROM orders o " +
                "JOIN drinks d ON o.drink_id = d.drink_id " +
                "RIGHT JOIN customers c ON o.customer_id = c.customer_id " +
                "GROUP BY c.customer_id;";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                selections.add(new SalesByCustomer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getInt("customer_order_quantity"),
                        resultSet.getDouble("customer_total_purchases")
                ));
            }
        }
        return selections;
    }

    @Override
    public List<SalesByDrink> getSalesByDrink() throws SQLException, RemoteException {
        List<SalesByDrink> selections = new ArrayList<>();
        String query = "SELECT d.drink_id, d.drink_name, SUM(o.order_quantity) AS drink_order_quantity, SUM(o.order_quantity * d.drink_price) AS drink_total_sales " +
                "FROM orders o " +
                "RIGHT JOIN drinks d ON o.drink_id = d.drink_id " +
                "GROUP BY d.drink_id;";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                selections.add(new SalesByDrink(
                        resultSet.getInt("drink_id"),
                        resultSet.getString("drink_name"),
                        resultSet.getInt("drink_order_quantity"),
                        (int) resultSet.getDouble("drink_total_sales")
                ));
            }
        }
        return selections;
    }

    @Override
    public List<OrdersRecords> getSalesRecords() throws SQLException, RemoteException {
        List<OrdersRecords> selections = new ArrayList<>();
        String query = "SELECT o.order_id, o.order_date, b.branch_name, c.customer_name, d.drink_name, o.order_quantity, (o.order_quantity * d.drink_price) AS total_price " +
                "FROM orders o " +
                "JOIN drinks d on d.drink_id = o.drink_id " +
                "JOIN branches b on b.branch_id = o.branch_id " +
                "JOIN customers c on c.customer_id = o.customer_id " +
                "ORDER BY o.order_date DESC;";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                selections.add(new OrdersRecords(
                        resultSet.getInt("order_id"),
                        resultSet.getDate("order_date").toLocalDate(),
                        resultSet.getString("branch_name"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("drink_name"),
                        resultSet.getInt("order_quantity"),
                        (int) resultSet.getDouble("total_price")
                ));
            }
        }
        return selections;
    }

    @Override
    public List<StockLevels> getStockLevels() throws SQLException, RemoteException {
        List<StockLevels> selections = new ArrayList<>();
        String query = "SELECT d.drink_name, b.branch_name, s.drink_stock " +
                "FROM stock s " +
                "JOIN branches b on b.branch_id = s.branch_id " +
                "JOIN drinks d on d.drink_id = s.drink_id;";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                selections.add(new StockLevels(
                        resultSet.getString("drink_name"),
                        resultSet.getString("branch_name"),
                        resultSet.getInt("drink_stock")
                ));
            }
        }
        return selections;
    }

    @Override
    public List<ClientOrderHistory> clientGetOrderHistory(int customer_id) throws SQLException, RemoteException {
        List<ClientOrderHistory> selections = new ArrayList<>();
        String query = "SELECT o.order_id, o.order_date, b.branch_name, d.drink_name, o.order_quantity, d.drink_price, (o.order_quantity * d.drink_price) AS total_price " +
                "FROM orders o " +
                "JOIN drinks d ON o.drink_id = d.drink_id " +
                "JOIN branches b ON o.branch_id = b.branch_id " +
                "WHERE o.customer_id = ? " +
                "ORDER BY o.order_date DESC;";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, customer_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    selections.add(new ClientOrderHistory(
                            resultSet.getInt("order_id"),
                            resultSet.getDate("order_date").toLocalDate(),
                            resultSet.getString("branch_name"),
                            resultSet.getString("drink_name"),
                            resultSet.getInt("order_quantity"),
                            (int) resultSet.getDouble("drink_price"),
                            (int) resultSet.getDouble("total_price")
                    ));
                }
            }
        }
        return selections;
    }

    @Override
    public boolean addNewCustomer(String customer_email, String customer_name, String customer_phone, String customer_password) throws SQLException, RemoteException {
        String sql = "INSERT INTO customers (customer_email, customer_name, customer_phone, customer_password) VALUES (?,?,?,?)";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, customer_email);
            preparedStatement.setString(2, customer_name);
            preparedStatement.setString(3, customer_phone);
            preparedStatement.setString(4, customer_password);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean addNewBranch(String branch_name) throws SQLException, RemoteException {
        String sql = "INSERT INTO branches (branch_name) VALUES (?)";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, branch_name);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean addNewDrink(String drink_name, int drink_price) throws SQLException, RemoteException {
        String sql = "INSERT INTO drinks (drink_name, drink_price) VALUES (?,?)";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, drink_name);
            preparedStatement.setInt(2, drink_price);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean addNewOrder(int branch_id, int customer_id, int drink_id, int order_quantity) throws SQLException, RemoteException {
        String orderSql = "INSERT INTO orders (order_date, branch_id, customer_id, drink_id, order_quantity) VALUES (?,?,?,?,?)";
        String stockSql = "UPDATE stock SET drink_stock = (drink_stock - ?) WHERE drink_id = ? AND branch_id = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement orderStmt = connection.prepareStatement(orderSql);
                PreparedStatement stockStmt = connection.prepareStatement(stockSql)
        ) {
            orderStmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            orderStmt.setInt(2, branch_id);
            orderStmt.setInt(3, customer_id);
            orderStmt.setInt(4, drink_id);
            orderStmt.setInt(5, order_quantity);

            stockStmt.setInt(1, order_quantity);
            stockStmt.setInt(2, drink_id);
            stockStmt.setInt(3, branch_id);

            return orderStmt.executeUpdate() > 0 && stockStmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean editStock(int drink_id, int branch_id, int drink_stock) throws SQLException, RemoteException {
        String sql = "UPDATE stock SET drink_stock = ? WHERE drink_id = ? AND branch_id = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, drink_stock);
            preparedStatement.setInt(2, drink_id);
            preparedStatement.setInt(3, branch_id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean checkCustomerCredentials(String customer_email, String customer_password) throws SQLException, RemoteException {
        String sql = "SELECT * FROM customers WHERE customer_email = ? AND customer_password = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, customer_email);
            preparedStatement.setString(2, customer_password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public boolean checkAdminCredentials(String admin_name, String admin_password) throws SQLException, RemoteException {
        String sql = "SELECT * FROM admins WHERE admin_name = ? AND admin_password = ?";
        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, admin_name);
            preparedStatement.setString(2, admin_password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public ArrayList<Integer> getIntegerColumn(String field_name, String table_name, String conditions) throws SQLException, RemoteException {
        ArrayList<Integer> arrayList = new ArrayList<>();
        String query = "SELECT " + field_name + " FROM " + table_name + (conditions == null ? "" : " WHERE " + conditions);
        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                arrayList.add(resultSet.getInt(field_name));
            }
        }
        return arrayList;
    }

    @Override
    public ArrayList<String> getStringColumn(String field_name, String table_name, String conditions) throws SQLException, RemoteException {
        ArrayList<String> arrayList = new ArrayList<>();
        String query = "SELECT " + field_name + " FROM " + table_name + (conditions == null ? "" : " WHERE " + conditions);
        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                arrayList.add(resultSet.getString(field_name));
            }
        }
        return arrayList;
    }

    @Override
    public boolean restockDrink(int drinkId, int branchId, int quantity) throws RemoteException {
        String sql = "UPDATE stock SET drink_stock = drink_stock + ? WHERE drink_id = ? AND branch_id = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, drinkId);
            stmt.setInt(3, branchId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RemoteException("Error restocking drink", e);
        }
    }

    @Override
    public boolean addNewAdmin(String adminName, String adminPassword) throws RemoteException {
        String sql = "INSERT INTO admins (admin_name, admin_password) VALUES (?,?)";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, adminName);
            ps.setString(2, adminPassword); // plaintext for demo; use hashing for real apps!
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RemoteException("Failed to add admin: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SalesOrderReport> getSalesOrderReport() throws RemoteException {
        List<SalesOrderReport> report = new ArrayList<>();
        String sql = "SELECT o.order_id, o.order_date, c.customer_name, c.customer_email, b.branch_name, d.drink_name, o.order_quantity, (o.order_quantity * d.drink_price) AS order_amount " +
                "FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN branches b ON o.branch_id = b.branch_id " +
                "JOIN drinks d ON o.drink_id = d.drink_id " +
                "ORDER BY o.order_date DESC, o.order_id DESC";
        try (
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                report.add(new SalesOrderReport(
                        rs.getInt("order_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getString("branch_name"),
                        rs.getString("drink_name"),
                        rs.getInt("order_quantity"),
                        rs.getDouble("order_amount")
                ));
            }
        } catch (SQLException e) {
            throw new RemoteException("Failed to fetch sales order report", e);
        }
        return report;
    }

    @Override
    public double getTotalSales() throws RemoteException {
        String sql = "SELECT SUM(o.order_quantity * d.drink_price) as total_sales " +
                "FROM orders o " +
                "JOIN drinks d ON o.drink_id = d.drink_id";
        try (
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            if (rs.next()) {
                return rs.getDouble("total_sales");
            }
        } catch (SQLException e) {
            throw new RemoteException("Failed to fetch total sales", e);
        }
        return 0;
    }

    @Override
    public List<StockAlert> getLowStockAlerts() throws RemoteException {
        List<StockAlert> alerts = new ArrayList<>();
        String sql = "SELECT d.drink_name, b.branch_name, s.drink_stock, s.threshold " +
                "FROM stock s " +
                "JOIN drinks d ON s.drink_id = d.drink_id " +
                "JOIN branches b ON s.branch_id = b.branch_id " +
                "WHERE s.drink_stock < s.threshold";
        try (
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                alerts.add(new StockAlert(
                        rs.getString("drink_name"),
                        rs.getString("branch_name"),
                        rs.getInt("drink_stock"),
                        rs.getInt("threshold")
                ));
            }
        } catch (SQLException e) {
            throw new RemoteException("Failed to fetch low stock alerts", e);
        }
        return alerts;
    }
}