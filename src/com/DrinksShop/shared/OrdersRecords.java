package com.DrinksShop.shared;


import java.time.LocalDate;

public class OrdersRecords {
    private int orderID;
    private LocalDate orderDate;
    private String branchName;
    private String customerName;
    private String drinkName;
    private int orderQuantity;
    private int totalPrice;

    public OrdersRecords(int orderID, LocalDate orderDate, String branchName, String customerName, String drinkName, int orderQuantity, int totalPrice) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.branchName = branchName;
        this.customerName = customerName;
        this.drinkName = drinkName;
        this.orderQuantity = orderQuantity;
        this.totalPrice = totalPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
