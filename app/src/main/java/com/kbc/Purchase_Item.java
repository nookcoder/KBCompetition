package com.kbc;

public class Purchase_Item {
    private String storeName, productName, category;
    private int  price;


    public Purchase_Item(String storeName, String productName, String category,  int price) {
        this.storeName = storeName;
        this.productName = productName;
        this.category = category;
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }


    public int getPrice() {
        return price;
    }


    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public void setPrice(int price) {
        this.price = price;
    }

}