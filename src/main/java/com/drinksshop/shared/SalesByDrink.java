package com.drinksshop.shared;

import java.io.Serializable;

public class SalesByDrink implements Serializable {
    private int drinkId;
    private String drinkName;
    private int drinkOrderQuantity;
    private double drinkTotalSales;

    public SalesByDrink(int drinkId, String drinkName, int drinkOrderQuantity, double drinkTotalSales) {
        this.drinkId = drinkId;
        this.drinkName = drinkName;
        this.drinkOrderQuantity = drinkOrderQuantity;
        this.drinkTotalSales = drinkTotalSales;
    }

    public int getDrinkId() { return drinkId; }
    public String getDrinkName() { return drinkName; }
    public int getDrinkOrderQuantity() { return drinkOrderQuantity; }
    public double getDrinkTotalSales() { return drinkTotalSales; }
}