package com.kbc.Server;

public class PickUpData {
    String merchantId;
    String merchantName;
    String personalId;
    String personalName;
    String pickUpYear, pickUpMonth,pickUpDay;
    String pickUpNoon, pickUpMinute,pickUpHour;
    String location, productName;
    String registerTime;
    String pickregisterTime;
    Boolean isPickUp;

    public PickUpData(String merchantId, String personalId, String registerTime) {
        this.merchantId = merchantId;
        this.personalId = personalId;
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

    public Boolean getPickUp() {
        return isPickUp;
    }

    public void setPickUp(Boolean pickUp) {
        isPickUp = pickUp;
    }


    public String getPickregisterTime() {
        return pickregisterTime;
    }

    public void setPickregisterTime(String pickregisterTime) {
        this.pickregisterTime = pickregisterTime;
    }
}
