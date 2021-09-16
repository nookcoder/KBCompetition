package com.kbc.Pickup;

import android.widget.ImageView;

public class Pickup_Item {
    private String buyerName, productNameInPickupList, pickupDate, pickupTime;
    private int pickupQuantity;
    private ImageView imageView; //임의로 설정


    public Pickup_Item(String buyerName, String productNameInPickupList, String pickupDate, String pickupTime, int pickupQuantity) {
        this.buyerName = buyerName;
        this.productNameInPickupList = productNameInPickupList;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.pickupQuantity = pickupQuantity;
    }

    public String getBuyerName() {
        return buyerName;
    }
    public String getProductNameInPickupList() {
        return productNameInPickupList;
    }
    public String getPickupDate() {
        return pickupDate;
    }
    public String getPickupTime() {
        return pickupTime;
    }
    public int getPickupQuantity() {
        return pickupQuantity;
    }


    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }
    public void setProductNameInPickupList(String productNameInPickupList) { this.productNameInPickupList = productNameInPickupList; }
    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }
    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
    public void setPickupQuantity(int pickupQuantity) {
        this.pickupQuantity = pickupQuantity;
    }

}