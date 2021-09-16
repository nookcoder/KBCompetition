package com.kbc.Sale;

import android.widget.ImageView;

import java.io.Serializable;

public class Sale_Item implements Serializable {
    private String name, category;
    private int stock, price;
    //private String productImageSrc;

    private String details, register_time;


    public Sale_Item(String name, String category, int stock, int price) {
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;
    }

    public Sale_Item(String name, String category, String register_time, int stock, int price, String details) {
        this.name = name;
        this.category = category;
        this.register_time = register_time;
        this.stock = stock;
        this.price = price;
        this.details = details;
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
    public String getDetails(){return  details;}
    public String getRegister_time(){return register_time;}

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
    public void setDetails(String details){this.details = details;}
    public void setRegister_time(String register_time){this.register_time = register_time;}
}