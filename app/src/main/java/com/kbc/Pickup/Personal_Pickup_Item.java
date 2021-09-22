package com.kbc.Pickup;

import android.widget.ImageView;

public class Personal_Pickup_Item {

    private String storeName, productNameInPickupList, pickupDate, pickupTime;
    private ImageView imageView; //임의로 설정


    public Personal_Pickup_Item(String storeName, String productNameInPickupList, String pickupDate, String pickupTime) {
        this.storeName = storeName;
        this.productNameInPickupList = productNameInPickupList;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
    }

    public String getStoreName() {
        return storeName;
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


    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setProductNameInPickupList(String productNameInPickupList) {
        this.productNameInPickupList = productNameInPickupList;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

}
