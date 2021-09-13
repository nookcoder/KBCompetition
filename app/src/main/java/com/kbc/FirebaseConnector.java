package com.kbc;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class FirebaseConnector {

    //싱글톤
    private static FirebaseConnector firebaseConnector = null;

    //데이터베이스 연결 변수
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseReference;

    //액티비티에 연결 객체 생성
    public static FirebaseConnector getInstance(Activity activity, String mode){
        firebaseConnector = new FirebaseConnector(activity, mode);
        return firebaseConnector;
    }

    public static FirebaseConnector getInstance(){
        return firebaseConnector;
    }

    //데베 구축
    private FirebaseConnector(Activity activity, String mode){
        FirebaseApp.initializeApp(activity);
        firebaseDatabase = FirebaseDatabase.getInstance();

        //유저모드, 점주모드로 나눠서 dbRef 객체 생성
        switch (mode){
            case "Person":
                databaseReference = firebaseDatabase.getReference("Person");
                break;

            case "StoreManager":
                databaseReference = firebaseDatabase.getReference("StoreManager");
                break;
        }
    }

    private Map<String, Object> map, id_map, chatrooms_map, chatting_map;


    //정보 가져오기
    public void Read_All_Data(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = (Map<String, Object>) dataSnapshot.getValue();

                if(map != null){
                    //아이디 별로 맵에 담기
                    for(String id: map.keySet()){
                        id_map = (Map<String, Object>) map.get(id);
                        Log.d(TAG, "카카오아이디 : "+ id_map);
                    }


                    //아이디 -> 채팅방 맵에 담기
                    for(String chatrooms: id_map.keySet()){
                        chatrooms_map = (Map<String, Object>)id_map.get(chatrooms);
                        Log.d(TAG, "채팅방 내역 : " + chatrooms_map);
                    }
                    //아이디 -> 채팅내용 맵에 담기
                    for(String chatting: id_map.keySet()){
                        chatting_map = (Map<String, Object>)id_map.get(chatting);
                        Log.d(TAG, "채팅방 내용 : " + chatting_map);
                    }
                }
                Log.d(TAG, "데이터 : " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



}
