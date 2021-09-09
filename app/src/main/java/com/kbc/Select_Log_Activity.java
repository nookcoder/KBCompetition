package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Select_Log_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //화면 셋팅
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_log_activity);

        //회원가입 버튼에 리스너 달기
        Button signupButton = (Button) findViewById(R.id.signup);
        signupButton.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view){
              //화면 전환 + 로그 전달
              Intent intent = new Intent(getApplicationContext(),Select_User_Activity.class);
              intent.putExtra("log","sign up");
              startActivity(intent);
          }
        });

        //로그인 버튼에 리스너 달기
        Button signinButton = (Button) findViewById(R.id.signin);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환 + 로그 전달
                Intent intent = new Intent(getApplicationContext(),Select_User_Activity.class);
                intent.putExtra("log","signin up");
                startActivity(intent);
            }
        });
    }
}
