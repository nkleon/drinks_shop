package com.drinksshop.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static final String databaseURL = "jdbc:mysql://localhost:3306/drinks_shop";
    public static final String databaseUsername = "root";
    public static final String databasePassword = "";
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(databaseURL,databaseUsername,databasePassword);
    }
}
