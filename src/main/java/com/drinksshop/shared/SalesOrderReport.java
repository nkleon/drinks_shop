package com.drinksshop.shared;

import java.io.Serializable;
import java.time.LocalDate;

public class SalesOrderReport implements Serializable {
    private int orderId;
    private LocalDate orderDate;
    private String customerName;
    private String customerEmail;
    private String branchName;
    private String drinkName;
    private int orderQuantity;
    private double orderAmount;

    public SalesOrderReport(int orderId, LocalDate orderDate, String customerName, String customerEmail, String branchName, String drinkName, int orderQuantity, double orderAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.branchName = branchName;
        this.drinkName = drinkName;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
    }

    public int getOrderId() { return orderId; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public String getBranchName() { return branchName; }
    public String getDrinkName() { return drinkName; }
    public int getOrderQuantity() { return orderQuantity; }
    public double getOrderAmount() { return orderAmount; }
}