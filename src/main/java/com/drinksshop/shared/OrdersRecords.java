package com.drinksshop.shared;

import java.io.Serializable;
import java.time.LocalDate;

public class OrdersRecords implements Serializable {
    private int orderId;
    private LocalDate orderDate;
    private String branchName;
    private String customerName;
    private String drinkName;
    private int orderQuantity;
    private double totalPrice;

    public OrdersRecords(int orderId, LocalDate orderDate, String branchName, String customerName, String drinkName, int orderQuantity, double totalPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.branchName = branchName;
        this.customerName = customerName;
        this.drinkName = drinkName;
        this.orderQuantity = orderQuantity;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() { return orderId; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getBranchName() { return branchName; }
    public String getCustomerName() { return customerName; }
    public String getDrinkName() { return drinkName; }
    public int getOrderQuantity() { return orderQuantity; }
    public double getTotalPrice() { return totalPrice; }
}