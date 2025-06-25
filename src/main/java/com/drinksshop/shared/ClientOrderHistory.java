package com.drinksshop.shared;

import java.io.Serializable;
import java.time.LocalDate;

public class ClientOrderHistory implements Serializable {
    public int orderID;
    public LocalDate orderDate;
    public String branchName;
    public String drinkName;
    public int orderQuantity;
    public int drinkPrice;
    public int totalPrice;

    public ClientOrderHistory(int orderID, LocalDate orderDate, String branchName, String drinkName, int orderQuantity, int drinkPrice, int totalPrice) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.branchName = branchName;
        this.drinkName = drinkName;
        this.orderQuantity = orderQuantity;
        this.drinkPrice = drinkPrice;
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

    public String getDrinkName() {
        return drinkName;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public int getDrinkPrice() {
        return drinkPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
