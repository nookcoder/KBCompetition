package com.kbc;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
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

    private Map<String, Object> map, id_map,id_inside_map;
    private ArrayList<HashMap<String, String>> chatrooms_arraylist, chatting_arraylist;



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

                    Log.d(TAG, "아이디 키 : " +id_map.keySet());

                    for(String id_inside : id_map.keySet()){
                        id_inside_map = (Map<String, Object>)id_map.get(id_inside);
                        Log.d(TAG, "아이디 내부 : "+ id_inside_map);

                    }

                    Log.d(TAG, "아이디 내부 키 : " +id_inside_map.keySet());


                    for(String in_inside_key: id_inside_map.keySet()){
                        Log.d(TAG, "키 -> " + in_inside_key );


                        switch (in_inside_key){
                            //아이디 -> 채팅방 리스트에 담기
                            case "chatrooms":
                                chatrooms_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);

                                break;
                            //아이디 -> 채팅 내역 리스트에 담기
                            case "chatting":
                                chatting_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);
                                break;
                        }


                    }

                }

                Log.d(TAG, "채팅방 내역 : " + chatrooms_arraylist);
                Log.d(TAG, "채팅방 사이즈 : " + chatrooms_arraylist.size());
                Log.d(TAG, "채팅방 내용 : " + chatting_arraylist);
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
