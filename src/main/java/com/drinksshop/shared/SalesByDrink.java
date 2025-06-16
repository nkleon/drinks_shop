package com.drinksshop.shared;

public class SalesByDrink {
    private int drinkID;
    private String drinkName;
    private int drinkOrderQuantity;
    private int drinkTotalSales;

    public SalesByDrink(int drinkID, String drinkName, int drinkOrderQuantity, int drinkTotalSales){
        this.drinkID = drinkID;
        this.drinkName = drinkName;
        this.drinkOrderQuantity = drinkOrderQuantity;
        this.drinkTotalSales = drinkTotalSales;
    }

    public int getDrinkId(){ return drinkID; }
    public String getDrinkName(){ return drinkName; }
    public int getDrinkOrderQuantity(){ return drinkOrderQuantity; }
    public int getDrinkTotalSales(){ return drinkTotalSales; }
}
