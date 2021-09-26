package com.kbc.Server;

public class ProductData {
    private String userId;
    private String name;
    private String category;
    private String price;
    private String dateYear;
    private String dateMonth;
    private String dateDay;
    private String dateType;
    private String origin;
    private String details;
    private String location;
    private String town;
    private String registerTime;

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public ProductData(String userId, String name, String category, String price, String dateYear, String dateMonth, String dateDay, String dateType, String origin, String details) {
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.dateYear = dateYear;
        this.dateMonth = dateMonth;
        this.dateDay = dateDay;
        this.dateType = dateType;
        this.origin = origin;
        this.details = details;
    }

    public ProductData(String userId, String name, String category, String price, String dateYear, String dateMonth, String dateDay, String dateType, String origin, String details,String registerTime) {
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.dateYear = dateYear;
        this.dateMonth = dateMonth;
        this.dateDay = dateDay;
        this.dateType = dateType;
        this.origin = origin;
        this.details = details;
        this.registerTime = registerTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDateYear() {
        return dateYear;
    }

    public void setDateYear(String dateYear) {
        this.dateYear = dateYear;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString(){
        return "name : " + name + " userId : " +userId + " price : "+price;
    }
}
