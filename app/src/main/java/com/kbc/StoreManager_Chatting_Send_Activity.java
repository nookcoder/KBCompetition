package com.kbc;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StoreManager_Chatting_Send_Activity extends AppCompatActivity {

    //파이어베이스 데베 연동
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //데베 연결
    private DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_chatting_send_activity);


        Button send_btn = findViewById(R.id.send_message);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터베이스에 쓰기
                databaseReference.child("프론트").push().setValue("서히");
                Log.d("클릭","들어가!!!");
            }
        });


    }
}