package com.kbc.Sale;

import android.widget.ImageView;

import java.io.Serializable;

public class Sale_Item implements Serializable {
    //구입자 아이디
    private String personal_Id;

    //유저아이디
    private String user_Id, user_location;
    //상품사진
    private String productImageSrc;
    //상품 이름, 카테고리
    private String name, category;
    //상품 가격
    private String price;
    //기한날짜, 유통/구입 구분변수 ,원산지(거래처)
    private String date_year, date_month, date_day, date_type, origin;
    //상품 게시글 내용, 상품 등록 시간- (yyyy년 MM월 dd일 HH:mm)
    private String details, register_time;

    public Sale_Item(){

    }

    public Sale_Item(String productImageSrc, String name, String category, String price,
                     String date_year, String date_month, String date_day, String date_type,
                     String origin, String details, String register_time){
        this.productImageSrc = productImageSrc;

        this.name = name;
        this.category = category;
        this.price = price;

        this.date_year = date_year;
        this.date_month =date_month;
        this.date_day = date_day;
        this.date_type = date_type;

        this.origin = origin;
        this.details = details;
        this.register_time = register_time;
    }


    public Sale_Item(String productImageSrc,  String name, String category, String price,
                     String date_year, String date_month, String date_day, String date_type,
                     String origin, String details, String register_time, String personal_Id, String user_Id,String user_location){
        this.productImageSrc = productImageSrc;

        this.name = name;
        this.category = category;
        this.price = price;

        this.date_year = date_year;
        this.date_month =date_month;
        this.date_day = date_day;
        this.date_type = date_type;

        this.origin = origin;
        this.details = details;
        this.register_time = register_time;

        this.personal_Id = personal_Id;
        this.user_Id = user_Id;
        this.user_location = user_location;
    }


    public String getUser_Id(){return user_Id;}
    public String getUser_location(){return user_location;}
    public String getProductImageSrc() {return productImageSrc;}
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public String getPrice() {
        return price;
    }
    public String getDate_year(){return date_year;}
    public String getDate_month(){return date_month;}
    public String getDate_day(){ return date_day;}
    public String getDate_type(){return date_type;}

    public String getOrigin(){return origin;}
    public String getDetails(){return  details;}
    public String getRegister_time(){return register_time;}

    public String getPersonal_Id() { return personal_Id; }

    public void setPersonal_Id(String personal_Id) {
        this.personal_Id = personal_Id;
    }

    public void setUser_Id(String user_Id){this.user_Id = user_Id;}
    public void setUser_location(String user_location){this.user_location = user_location;}
    public void serProductImageSrc(String productImageSrc) { this.productImageSrc = productImageSrc; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setDate_year(String date_year){this.date_year = date_year;}
    public void setDate_month(String date_month){this.date_month = date_month;}
    public void setDate_day(String date_day){this.date_day = date_day;}
    public void setDate_type(String date_type){this.date_type = date_type;}

    public void setOrigin(String origin){this.origin= origin;}
    public void setDetails(String details){this.details = details;}
    public void setRegister_time(String register_time){this.register_time = register_time;}
}