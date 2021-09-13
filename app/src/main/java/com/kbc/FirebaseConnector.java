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
    public static FirebaseConnector getInstance(Activity activity){
        firebaseConnector = new FirebaseConnector(activity);
        return firebaseConnector;
    }

    public static FirebaseConnector getInstance(){
        return firebaseConnector;
    }

    //데베 구축
    private FirebaseConnector(Activity activity){
        FirebaseApp.initializeApp(activity);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //storemanager 정보 가져오기
    public void Read_StoreManager(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "이름: " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    //person 정보가져오기
    public void Read_People_(){

    }

}
