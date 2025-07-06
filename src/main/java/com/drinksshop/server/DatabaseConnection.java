package com.drinksshop.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.SQLException;

//class TestDatabaseConnection {
//    public static void main(String[] args) {
//        try (Connection conn = DatabaseConnection.getConnection()) {
//            if (conn != null && !conn.isClosed()) {
//                System.out.println("✅ Connection to database was successful!");
//            } else {
 //               System.out.println("⚠️ Connection object is null or closed.");
   //         }
     //   } catch (SQLException e) {
       //     System.out.println("❌ Failed to connect to the database.");
         //   e.printStackTrace();
        //}
    //}
//}

public class DatabaseConnection {
    public static final String databaseURL = "jdbc:mysql://localhost:3306/drinks_shop";
    public static final String databaseUsername = "root";
    public static final String databasePassword = "";
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(databaseURL,databaseUsername,databasePassword);
    }
}
