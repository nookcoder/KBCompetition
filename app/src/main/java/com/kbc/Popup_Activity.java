package com.kbc;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Popup_Activity extends AppCompatActivity {

    Button ok_btn, cancle_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_activity);

        //확인, 취소 버튼 가져오기
        ok_btn = (Button)findViewById(R.id.ok_btn);
        cancle_btn = (Button)findViewById(R.id.cancle_btn);
    }

    //확인 버튼 클릭
    public void click_ok(View view){
        finish();
    }
    //취소 버튼 클릭
    public void click_cancle(View view){
        finish();
    }

    //바깥레이어 클릭시 안닫히게
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }

        return true;
    }

    //안드로이드 백버튼 막기
    @Override
    public void onBackPressed(){
        return;
    }
}
