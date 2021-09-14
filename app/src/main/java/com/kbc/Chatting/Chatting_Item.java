package com.kbc.Chatting;

public class Chatting_Item {

    private String name;
    private String message;
    private String time;
    private String profileUrl;
    private int viewType;

    //채팅방 목록
    public Chatting_Item(String name, String profileUrl, String message, String time){
        this.name = name;
        this.profileUrl = profileUrl;
        this.message = message;
        this.time = time;
    }
    //채팅내
    public Chatting_Item(String name, String profileUrl, String message, String time, int viewType){
        this.name = name;
        this.profileUrl = profileUrl;
        this.message = message;
        this.time = time;
        this.viewType = viewType;
    }

    //firebase DB에 객체로 값을 읽어올 때..
    //파라미터가 비어있는 생성자가 핑요함.
    public Chatting_Item() {
    }

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

    public void setProfileUrl(String pofileUrl) {
        this.profileUrl = profileUrl;
    }

    public int getViewType(){return viewType;}
    public void setViewType(int viewType){
        this.viewType = viewType;
    }
}


