package com.drinksshop.shared;

import java.io.Serializable;
import java.time.LocalDate;

public class ClientOrderHistory implements Serializable {
    private int orderId;
    private LocalDate orderDate;
    private String branchName;
    private String drinkName;
    private int orderQuantity;
    private double drinkPrice;
    private double totalPrice;

    public ClientOrderHistory(int orderId, LocalDate orderDate, String branchName, String drinkName, int orderQuantity, double drinkPrice, double totalPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.branchName = branchName;
        this.drinkName = drinkName;
        this.orderQuantity = orderQuantity;
        this.drinkPrice = drinkPrice;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() { return orderId; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getBranchName() { return branchName; }
    public String getDrinkName() { return drinkName; }
    public int getOrderQuantity() { return orderQuantity; }
    public double getDrinkPrice() { return drinkPrice; }
    public double getTotalPrice() { return totalPrice; }
}