package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//이전 intent 에서 유저가 누군지 받아와야됨!
public class Added_Done_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.go_to_start);

        //유저 데이터 받기
        Intent intentForGet = getIntent();
        String user = intentForGet.getExtras().getString("user");

        //다음 버튼
        Button next = (Button) findViewById(R.id.start);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //유저가 사업자면
//                if(user.equals("store manager")){
//                    Intent intent1 = new Intent(getApplicationContext(), StoreManager_Chatting_Send_Activity.class);//변경 필요
//                    startActivity(intent1);
//                }
//                //유저가 개인이면
//                else if(user.equals("person")){
//                    Intent intent2 = new Intent(getApplicationContext(), StoreManager_Chatting_Send_Activity.class);//변경 필요
//                    startActivity(intent2);
//                }
            }
        });
    }
}