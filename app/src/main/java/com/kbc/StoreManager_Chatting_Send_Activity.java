package com.kbc;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class StoreManager_Chatting_Send_Activity extends AppCompatActivity {


    private EditText editText;
    private ListView listView;
    private ArrayList<Chatting_Item> chatting_items = new ArrayList<>();

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
                databaseReference.child("서버").push().setValue("현");
                Log.d("클릭","들어가!!!");
            }
        });



    }
}