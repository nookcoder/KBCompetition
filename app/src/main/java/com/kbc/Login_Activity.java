package com.kbc;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.MenuItemHoverListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kbc.Server.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

//유저 선택 + 로그인 화면
//To do 사업자 정보 등록되어 있는지 확인 필요!!
//To do 데이터(userId) 전달

public class Login_Activity extends AppCompatActivity {
    String selectedUser;
    String id,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //화면 전환 4개!
        //사업자 가게 정보 ㅇ , x
        Intent intentForStoreO = new Intent(Login_Activity.this, StoreManager_MainActivity.class);
        Intent intentForStoreX = new Intent(Login_Activity.this, EmptyStoreInfo_Activity.class);
        //개인 기본 정보 ㅇ , x
        Intent intentForPsersonO = new Intent(Login_Activity.this, StoreManager_MainActivity.class);//변경 필요!!!
        Intent intentForPsersonX = new Intent(Login_Activity.this, EmptyStoreInfo_Activity.class);//변경 필요!!!

        // 라디오그룹 참조
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        RadioButton store = (RadioButton) findViewById(R.id.storeManagerSelected);
        RadioButton person = (RadioButton) findViewById(R.id.personSelected);

        //텍스트 크기 줄마다 다르게 조절
        makeOtherSize(store,"음식을 팔고 싶어요.");
        makeOtherSize(person,"음식을 사고 싶어요.");

        //로그인 버튼
        Button login = (Button) findViewById(R.id.loginButton);
        login.setEnabled(false);

        // 라디오 그룹에 이벤트
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                login.setEnabled(true);
                RadioButton select = (RadioButton) findViewById(id);
                if (id == R.id.storeManagerSelected) {
                    selectedUser = "사업자";
                } else if (id == R.id.personSelected) {
                    selectedUser = "개인";
                }
                //하나라도 선택되면 버튼 색 변경
                login.setTextColor(Color.parseColor("#FFC11B"));
            }
        });

        //로그인 버튼에 이벤트
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> UserApiClient.getInstance().loginWithKakaoTalk(Login_Activity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e("으악", "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i("우왕", "로그인 성공(토큰) : " + oAuthToken.getAccessToken());

                UserApiClient.getInstance().me((user, meError) -> {
                    if (meError != null) {
                        Log.e("a", "사용자 정보 요청 실패", meError);
                    } else {
                        System.out.println("로그인 완료");
                        Log.i("b", user.toString());
                        {
                            id=String.valueOf(user.getId());
                            name=String.valueOf(user.getKakaoAccount().getProfile().getNickname());
                            Log.i("회원정보", "사용자 정보 요청 성공" +
                                    "\n회원번호: " + user.getId() +
                                    "\n이메일: " + user.getKakaoAccount().getEmail());
                        }
                        Account user1 = user.getKakaoAccount();
                        System.out.println("사용자 계정" + user1);
                    }

                    //사업자 화면 전환!!
                    if(selectedUser.equals("사업자")) {
                        //1)정보 없는 경우 -> 정보 추가 화면으로
                        //데이터 전달 (userID)
                        sendIdToServer(id,name,intentForStoreO,intentForStoreX);
                    }

                    //개인 화면 전환!!
                    else if(selectedUser.equals("개인")) {
                        //데이터 전달 (userID)
                        intentForPsersonO.putExtra("userID" , id);
                        startActivity(intentForPsersonO);
                    }
                    return null;
                });
            }
            return null;
        }));
    }

    //텍스트 특정 글자 크기 다르게 하기
    public void makeOtherSize(RadioButton radioButton , String word){
        String content = radioButton.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        int start = content.indexOf(word);
        int end=start+word.length();
        spannableString.setSpan(new StyleSpan(Typeface.NORMAL), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(0.6f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        radioButton.setText(spannableString);
    }

    public void sendIdToServer(String ID,String NAME, Intent intentO,Intent intentx){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //사업자 정보 등록
                if(Boolean.valueOf(response)){
                    //데이터 전달 (userID)
                    intentO.putExtra("userID" ,ID);
                    startActivity(intentO);
                }
                //사업자 정보 등록 X
                else{
                    //데이터 전달 (userID)
                    intentx.putExtra("userID" , ID);
                    intentx.putExtra("user","개인");
                    startActivity(intentx);
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(ID,NAME,listener);
        RequestQueue requestQueue = Volley.newRequestQueue(Login_Activity.this);
        requestQueue.add(loginRequest);
    }




}

