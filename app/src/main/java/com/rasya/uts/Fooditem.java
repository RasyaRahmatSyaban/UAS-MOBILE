package com.rasya.uts;

public class Fooditem {
    private String idMeal;
    private String strMeal;
    private String strMealThumb;
    private String strInstructions;
    private String price; // Default harga
    private int quantity;       // Default jumlah 0

    public Fooditem(String idMeal, String strMeal, String strMealThumb, String strInstructions, int quantity, String price) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.strInstructions = strInstructions;
        this.quantity = quantity;
        this.price = price;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
