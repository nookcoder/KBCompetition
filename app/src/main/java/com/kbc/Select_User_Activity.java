package com.kbc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Select_User_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_user_activity);

        //로그 받아오기
        Intent intentForGet = getIntent();
        String getLog = intentForGet.getExtras().getString("log");

        //버튼 지정
        Button storemanager = (Button) findViewById(R.id.storemanager);
        Button person = (Button) findViewById(R.id.person);

        //회원가입인 경우
        if(getLog.equals("sign up")){
            //사업자
            storemanager.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), StoreManager_CheckNum.class);
                    startActivity(intent);
                }
            });
            //개인
            person.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //화면 전환 + 시용자:사업자 전달
                    Intent intent = new Intent(getApplicationContext(), Select_Log_Activity.class);
                    startActivity(intent);
                }
            });
        }
        //로그인인 경우
        else if(getLog.equals("sign in")){
            //사업자
            storemanager.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //화면 전환 + 시용자:사업자 전달
                    Intent intent1 = new Intent(getApplicationContext(),Select_User_Activity.class);
                    intent1.putExtra("user","storemanager");
                    startActivity(intent1);
                }
            });
            //개인
            person.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //화면 전환 + 시용자:사업자 전달
                    Intent intent2 = new Intent(getApplicationContext(),Select_User_Activity.class);
                    intent2.putExtra("user","person");
                    startActivity(intent2);
                }
            });
        }
    }
}
