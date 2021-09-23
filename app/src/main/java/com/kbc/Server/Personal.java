package com.kbc.Server;

public class Personal {
    String userId;
    String userName;
    String nickName;
    String userPhoneNumber;
    String firstLocation;
    String secondLocation;
    Boolean isRegister;
    String registerTime;
    int townPosition1;
    int townPosition2;

    public Personal(String userId, String userName, String nickName, String userPhoneNumber, String firstLocation, String secondLocation, int townPosition1, int townPosition2) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.userPhoneNumber = userPhoneNumber;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
        this.townPosition1 = townPosition1;
        this.townPosition2 = townPosition2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(String firstLocation) {
        this.firstLocation = firstLocation;
    }

    public String getSecondLocation() {
        return secondLocation;
    }

    public void setSecondLocation(String secondLocation) {
        this.secondLocation = secondLocation;
    }

    public Boolean getRegister() {
        return isRegister;
    }

    public void setRegister(Boolean register) {
        isRegister = register;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public int getTownPosition1() {
        return townPosition1;
    }

    public void setTownPosition1(int townPosition1) {
        this.townPosition1 = townPosition1;
    }

    public int getTownPosition2() {
        return townPosition2;
    }

    public void setTownPosition2(int townPosition2) {
        this.townPosition2 = townPosition2;
    }
}
