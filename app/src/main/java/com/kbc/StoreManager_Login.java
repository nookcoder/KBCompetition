package com.kbc;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kbc.Chatting.Chatting_Send_Activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StoreManager_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent intent = new Intent(StoreManager_Login.this, Chatting_Send_Activity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_login);
        getHashKey();

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> UserApiClient.getInstance().loginWithKakaoTalk(StoreManager_Login.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e("으앙", "로그인 실패", error);
                startActivity(intent);

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
                                    "\n회원번호: "+user.getId() +
                                    "\n이메일: "+user.getKakaoAccount().getEmail());
                        }
                        Account user1 = user.getKakaoAccount();
                        System.out.println("사용자 계정" + user1);
                    }
                    startActivity(intent);
                    return null;
                });
            }
            return null;
        }));
    }

    private void getHashKey()
    {
        PackageInfo packageInfo = null;
        try
        {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures)
        {
            try
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
            catch (NoSuchAlgorithmException e)
            {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

}