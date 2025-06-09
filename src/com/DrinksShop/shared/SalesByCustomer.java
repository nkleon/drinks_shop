package com.DrinksShop.shared;

public class SalesByCustomer {
    private int customerID;
    private String customerName;
    private int customerOrderQuantity;
    private int customerTotalPurchases;

    public SalesByCustomer(int customerID, String customerName, int customerOrderQuantity, int customerTotalPurchases){
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerOrderQuantity = customerOrderQuantity;
        this.customerTotalPurchases = customerTotalPurchases;
    }

    public int getCustomerID(){ return customerID; }
    public String getCustomerName(){ return customerName; }
    public int getCustomerOrderQuantity(){ return customerOrderQuantity; }
    public int getCustomerTotalPurchases(){ return customerTotalPurchases; }
}
