package com.kbc.Chatting;

public class Chatting {
    public static String userName;
    public static String profileUrl;

    //채팅창에서 정렬을 위한 매직넘버
    public static final int LEFT_CONTENT = 111;
    public static final int RIGHT_CONTENT = 222;

    //채팅목록 불러오기 / 채팅내역 불러오기 타입 분류
    public static final String GET_CHATROOMS = "채팅방 불러오기";
    public static final String GET_CHATTING_LIST = "채팅내역 불러오기";

    //파이어베이스에 등록된 채팅방인지 아닌지 검사
    public static final String NO_CHATTING_ROOM = "채팅방없음";
    public static final int ZERO_CHATTING_ROOM = 0;
}
