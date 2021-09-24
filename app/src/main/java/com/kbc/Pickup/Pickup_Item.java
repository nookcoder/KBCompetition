package com.kbc.Pickup;

import android.widget.ImageView;

public class Pickup_Item {

    private String buyerName, productNameInPickupList, pickupDate, pickupTime;
    private ImageView imageView; //임의로 설정
    private String registerTime;

    public Pickup_Item(String buyerName, String productNameInPickupList, String pickupDate, String pickupTime, String registerTime) {
        this.buyerName = buyerName;
        this.productNameInPickupList = productNameInPickupList;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.registerTime = registerTime;
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


    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }
    public void setProductNameInPickupList(String productNameInPickupList) { this.productNameInPickupList = productNameInPickupList; }
    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }
    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }


    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

}