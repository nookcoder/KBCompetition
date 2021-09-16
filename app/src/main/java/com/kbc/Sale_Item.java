package com.kbc;

import android.widget.ImageView;

public class Sale_Item {
    private String name, category;
    private int stock, price;
    //private String productImageSrc;


    public Sale_Item(String name, String category, int stock, int price) {
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;
    }



    //public String getProductImageSrc() {return productImageSrc;}
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

    //public void serProductImageSrc(String productImageSrc) { this.productImageSrc = productImageSrc; }
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