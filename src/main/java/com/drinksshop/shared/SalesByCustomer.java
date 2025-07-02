package com.drinksshop.shared;

import java.io.Serializable;

public class SalesByCustomer implements Serializable {
    private int customerId;
    private String customerName;
    private int customerOrderQuantity;
    private double customerTotalPurchases;

    public SalesByCustomer(int customerId, String customerName, int customerOrderQuantity, double customerTotalPurchases) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerOrderQuantity = customerOrderQuantity;
        this.customerTotalPurchases = customerTotalPurchases;
    }

    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public int getCustomerOrderQuantity() { return customerOrderQuantity; }
    public double getCustomerTotalPurchases() { return customerTotalPurchases; }
}