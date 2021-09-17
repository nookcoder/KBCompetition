package com.kbc.Sale;

import android.widget.ImageView;

import java.io.Serializable;

public class Sale_Item implements Serializable {
    //상품사진
    private String productImageSrc;
    //상품 이름, 카테고리
    private String name, category;
    //상품 수량, 가격
    private String stock, price;
    //기한날짜, 유통/구입 구분변수 ,원산지(거래처)
    private String date, date_type, origin;
    //상품 게시글 내용, 상품 등록 시간- (yyyy년 MM월 dd일 HH:mm)
    private String details, register_time;


    public Sale_Item(String productImageSrc, String name, String category, String stock, String price,
                     String date, String origin, String details, String register_time){
        this.productImageSrc = productImageSrc;

        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;

        this.date = date;
        this.origin = origin;
        this.details = details;
        this.register_time = register_time;
    }


    public String getProductImageSrc() {return productImageSrc;}
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public String getStock() {
        return stock;
    }
    public String getPrice() {
        return price;
    }
    public String getDate(){return date;}
    public String getOrigin(){return origin;}
    public String getDetails(){return  details;}
    public String getRegister_time(){return register_time;}

    public void serProductImageSrc(String productImageSrc) { this.productImageSrc = productImageSrc; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setStock(String stock) {
        this.stock = stock;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setDate(String date){this.date = date;}
    public void setOrigin(String origin){this.origin= origin;}
    public void setDetails(String details){this.details = details;}
    public void setRegister_time(String register_time){this.register_time = register_time;}
}