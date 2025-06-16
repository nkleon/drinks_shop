package com.drinksshop.shared;

public class SalesByBranch {
    private int branchID;
    private String branchName;
    private int branchOrderQuantity;
    private int branchTotalSales;

    public SalesByBranch(int branchID, String branchName, int branchOrderQuantity, int branchTotalSales){
        this.branchID = branchID;
        this.branchName = branchName;
        this.branchOrderQuantity = branchOrderQuantity;
        this.branchTotalSales = branchTotalSales;
    }

    public int getBranchID(){ return branchID; }
    public String getBranchName(){ return branchName; }
    public int getBranchOrderQuantity(){ return branchOrderQuantity; }
    public int getBranchTotalSales(){ return branchTotalSales; }
}
