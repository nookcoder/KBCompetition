package com.kbc.Chatting;

public class Chatting_Item {

    private String name;
    private String message;
    private String time;
    private String profileUrl;
    private String date;
    private int viewType;
    private String id;
    private String mode;

    //채팅방 목록
    public Chatting_Item(String name, String profileUrl, String message, String time, String date,String id,
                         String mode){
        this.name = name;
        this.profileUrl = profileUrl;
        this.message = message;
        this.time = time;
        this.date = date;
        this.id = id;
        this.mode = mode;
    }
    //채팅내
    public Chatting_Item(String name, String profileUrl, String message, String time, int viewType, String mode){
        this.name = name;
        this.profileUrl = profileUrl;
        this.message = message;
        this.time = time;
        this.viewType = viewType;
        this.mode = mode;
    }

    //firebase DB에 객체로 값을 읽어올 때..
    //파라미터가 비어있는 생성자가 핑요함.
    public Chatting_Item(String date, int viewType) {
        this.date = date;
        this.viewType = viewType;
    }
    public Chatting_Item(){}


    //Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getDate(){ return date;}

    public void setDate(String date){this.date = date;}

    public int getViewType(){return viewType;}
    public void setViewType(int viewType){
        this.viewType = viewType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}


