package com.drinksshop.shared;

import java.io.Serializable;

public class SalesByBranch implements Serializable {
    private int branchId;
    private String branchName;
    private int branchOrderQuantity;
    private double branchTotalSales;

    public SalesByBranch(int branchId, String branchName, int branchOrderQuantity, double branchTotalSales) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchOrderQuantity = branchOrderQuantity;
        this.branchTotalSales = branchTotalSales;
    }

    public int getBranchId() { return branchId; }
    public String getBranchName() { return branchName; }
    public int getBranchOrderQuantity() { return branchOrderQuantity; }
    public double getBranchTotalSales() { return branchTotalSales; }
}