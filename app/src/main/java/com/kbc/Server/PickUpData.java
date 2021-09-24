package com.kbc.Server;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.stream.Stream;

public class PickUpData {
    @SerializedName("merchantId")
    String merchantId;
    @SerializedName("merchantName")
    String merchantName;
    @SerializedName("personalId")
    String personalId;
    @SerializedName("personalName")
    String personalName;
    @SerializedName("pickUpYear")
    String pickUpYear;
    @SerializedName("pickUpMonth")
    String pickUpMonth;
    @SerializedName("pickUpDay")
    String pickUpDay;
    @SerializedName("pickUpNoon")
    String pickUpNoon;
    @SerializedName("pickUpMinute")
    String pickUpMinute;
    @SerializedName("pickUpHour")
    String pickUpHour;
    @SerializedName("location")
    String location;
    @SerializedName("productName")
    String productName;
    @SerializedName("registerTime")
    String registerTime;
    @SerializedName("pickregisterTime")
    String pickregisterTime;
    @SerializedName("isPickUp")
    int isPickUp;

    public PickUpData(String merchantId, String personalId, String pickUpYear, String pickUpMonth, String pickUpDay, String pickUpNoon, String pickUpMinute, String pickUpHour, String location, String productName, String registerTime) {
        this.merchantId = merchantId;
        this.personalId = personalId;
        this.pickUpYear = pickUpYear;
        this.pickUpMonth = pickUpMonth;
        this.pickUpDay = pickUpDay;
        this.pickUpNoon = pickUpNoon;
        this.pickUpMinute = pickUpMinute;
        this.pickUpHour = pickUpHour;
        this.location = location;
        this.productName = productName;
        this.registerTime = registerTime;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getPickUpYear() {
        return pickUpYear;
    }

    public void setPickUpYear(String pickUpYear) {
        this.pickUpYear = pickUpYear;
    }

    public String getPickUpMonth() {
        return pickUpMonth;
    }

    public void setPickUpMonth(String pickUpMonth) {
        this.pickUpMonth = pickUpMonth;
    }

    public String getPickUpDay() {
        return pickUpDay;
    }

    public void setPickUpDay(String pickUpDay) {
        this.pickUpDay = pickUpDay;
    }

    public String getPickUpNoon() {
        return pickUpNoon;
    }

    public void setPickUpNoon(String pickUpNoon) {
        this.pickUpNoon = pickUpNoon;
    }

    public String getPickUpMinute() {
        return pickUpMinute;
    }

    public void setPickUpMinute(String pickUpMinute) {
        this.pickUpMinute = pickUpMinute;
    }

    public String getPickUpHour() {
        return pickUpHour;
    }

    public void setPickUpHour(String pickUpHour) {
        this.pickUpHour = pickUpHour;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public int getPickUp() {
        return isPickUp;
    }

    public void setPickUp(int pickUp) {
        isPickUp = pickUp;
    }

    public String getPickregisterTime() {
        return pickregisterTime;
    }

    public void setPickregisterTime(String pickregisterTime) {
        this.pickregisterTime = pickregisterTime;
    }

}
