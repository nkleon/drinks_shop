package com.drinksshop.shared;

import java.io.Serializable;

public class StockAlert implements Serializable {
    private int drinkId;
    private int branchId;
    private int drinkStock;


    public StockAlert(int drinkId, int branchId, int drinkStock) {
        this.drinkId= drinkId;
        this.branchId = branchId;
        this.drinkStock = drinkStock;
    }

    public int getdrinkId() { return drinkId; }
    public int getbranchId() { return branchId; }
    public int getStockLevel() { return drinkStock; }
}