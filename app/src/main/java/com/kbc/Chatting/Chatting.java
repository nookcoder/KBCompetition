package com.kbc.Chatting;

public class Chatting {
    public static String userName;
    public static String profileUrl;

    //채팅창에서 정렬을 위한 매직넘버
    public static final int CENTER_CONTENT = 0;
    public static final int LEFT_CONTENT = 1;
    public static final int RIGHT_CONTENT = 2;

    //채팅목록 불러오기 / 채팅내역 불러오기 타입 분류
    public static final String GET_CHATROOMS = "채팅방 불러오기";
    public static final String GET_CHATTING_LIST = "채팅내역 불러오기";

    //파이어베이스에 등록된 채팅방인지 아닌지 검사
    public static final String NO_CHATTING_ROOM = "채팅방없음";
    public static final int ZERO_CHATTING_ROOM = 0;


    //파이어베이스 값 찾기
    public static final String DATE = "date";
    public static final String PROFILEUTL = "profileUrl";
    public static final String NAME = "name";
    public static final String TIME = "time";
    public static final String MESSAGE = "message";

    public static final String STORE_MANAGER = "StoreManager";
    public static final String PERSONAL = "Personal";

    public static final int ME = 1;
    public static final int OTHER = 2;

    public static final String PICK_UP_TYPE = "픽업채팅";
    public static final String CHATTING_LIST_TYPE = "채팅리스트";


}
