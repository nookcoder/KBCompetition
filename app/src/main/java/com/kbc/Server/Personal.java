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
}
