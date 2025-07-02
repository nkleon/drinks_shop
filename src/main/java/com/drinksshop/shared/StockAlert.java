package com.drinksshop.shared;

import java.io.Serializable;

public class StockAlert implements Serializable {
    private String drinkName;
    private String branchName;
    private int stockLevel;
    private int threshold;

    public StockAlert(String drinkName, String branchName, int stockLevel, int threshold) {
        this.drinkName = drinkName;
        this.branchName = branchName;
        this.stockLevel = stockLevel;
        this.threshold = threshold;
    }

    public String getDrinkName() { return drinkName; }
    public String getBranchName() { return branchName; }
    public int getStockLevel() { return stockLevel; }
    public int getThreshold() { return threshold; }
}