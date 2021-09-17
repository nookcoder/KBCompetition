package com.kbc.Sale;

import android.widget.ImageView;

import java.io.Serializable;

public class Sale_Item implements Serializable {
    //상품사진
    private String productImageSrc;

    //상품 이름, 카테고리
    private String name, category;
    //상품 수량, 가격
    private int stock, price;
    //유통기한(구입날짜), 원산지(거래처)
    private String date, origin;
    //상품 게시글 내용, 상품 등록 시간
    private String details, register_time;


    public Sale_Item(String name, String category, int stock, int price) {
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;
    }

    //상품 상세 조회 및 수정 생성자
    public Sale_Item(String name, String category, String register_time, int stock, int price, String details) {
        this.name = name;
        this.category = category;
        this.register_time = register_time;
        this.stock = stock;
        this.price = price;
        this.details = details;
    }

    public Sale_Item(String productImageSrc, String name, String category, int stock, int price,
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
    public int getStock() {
        return stock;
    }
    public int getPrice() {
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
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setDate(String date){this.date = date;}
    public void setOrigin(String origin){this.origin= origin;}
    public void setDetails(String details){this.details = details;}
    public void setRegister_time(String register_time){this.register_time = register_time;}
}