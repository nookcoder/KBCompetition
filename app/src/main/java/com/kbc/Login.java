package com.kbc;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;


public class Login extends AppCompatActivity {
    String selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Intent intent1 = new Intent(Login.this, StoreManager_Chatting_Send_Activity.class);
        Intent intent2 = new Intent(Login.this, Select_Log_Activity.class);

        //테스트
        TextView textView = (TextView) findViewById(R.id.textView);

        // 라디오그룹 참조
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        Button login = (Button) findViewById(R.id.loginButton);
        login.setEnabled(false);

        // 클릭이벤트
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                login.setEnabled(true);
                RadioButton select = (RadioButton) findViewById(id);
                if (id == R.id.storeManagerSelected) {
                    textView.setText("사업자");
                    selectedUser = "사업자";
                } else if (id == R.id.personSelected) {
                    textView.setText("개인");
                    selectedUser = "개인";
                }
                login.setTextColor(Color.parseColor("#FFC11B"));
            }
        });

        //로그인 버튼에 이벤트
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> UserApiClient.getInstance().loginWithKakaoTalk(Login.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e("으앙", "로그인 실패", error);
                startActivity(intent1);

            } else if (oAuthToken != null) {
                Log.i("으악", "로그인 성공(토큰) : " + oAuthToken.getAccessToken());

                UserApiClient.getInstance().me((user, meError) -> {
                    if (meError != null) {
                        Log.e("a", "사용자 정보 요청 실패", meError);
                    } else {
                        System.out.println("로그인 완료");
                        Log.i("b", user.toString());
                        {
                            Log.i("회원정보", "사용자 정보 요청 성공" +
                                    "\n회원번호: " + user.getId() +
                                    "\n이메일: " + user.getKakaoAccount().getEmail());
                        }
                        Account user1 = user.getKakaoAccount();
                        System.out.println("사용자 계정" + user1);
                    }
                    if(selectedUser.equals("사업자")) {
                        startActivity(intent1);
                    }
                    else if(selectedUser.equals("개인")) {
                        startActivity(intent2);
                    }
                    textView.setText(selectedUser.toString());

                    return null;
                });
            }
            return null;
        }));
    }
}

