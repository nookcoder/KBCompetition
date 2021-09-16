package com.kbc;

public class Sale_Item {
    private String name, category;
    private int stock, price;


    public Sale_Item(String name, String category, int stock, int price) {
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public int getStock() {
        return stock;
    }
    public int getPrice() {
        return price;
    }

    public void setName(String name) { this.name = name; }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}