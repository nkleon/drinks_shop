package com.DrinksShop.shared;

public class StockLevels {
    private String drinkName;
    private String branchName;
    private int drinkStock;

    public StockLevels(String drinkName, String branchName, int drinkStock) {
        this.drinkName = drinkName;
        this.branchName = branchName;
        this.drinkStock = drinkStock;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getBranchName() {
        return branchName;
    }

    public int getDrinkStock() {
        return drinkStock;
    }
}
