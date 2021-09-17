package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Popup_Activity extends AppCompatActivity {

    private  String button_name;
    private TextView popup_title, popup_context;
    Button ok_btn, cancle_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_activity);

        //인텐트 가져오기
        Intent intent = getIntent();

        //이벤트 발생 버튼 확인을 위한 데이터 접근
        button_name = intent.getExtras().getString("button_name");

        //팝업창 제목, 내용 바꾸기
        popup_title = findViewById(R.id.popup_title);
        popup_context = findViewById(R.id.popup_context);
        switch (button_name){
            case "logout":
                popup_title.setText("로그아웃");
                popup_context.setText("로그아웃을 하시겠습니까?");
                break;

            case "withdrawal":
                popup_title.setText("탈퇴하기");
                popup_context.setText("정말로 탈퇴하시겠습니까???");
                break;
            case "Pickup complete":
                popup_title.setText("픽업 완료");
               popup_context.setText("픽업을 완료했습니까?");
               break;
        }



        //확인, 취소 버튼 가져오기
        ok_btn = (Button)findViewById(R.id.ok_btn);
        cancle_btn = (Button)findViewById(R.id.cancle_btn);
    }

    //확인 버튼 클릭
    public void click_ok(View view){
        //사장님 액티비티 호출하고,
        StoreManager_MainActivity storeManager_mainActivity = (StoreManager_MainActivity)StoreManager_MainActivity.storeManager_mainActivity;
        PickupDetailActivity pickupDetailActivity = (PickupDetailActivity)PickupDetailActivity.pickupDetailActivity;

        switch (button_name){

            //로그아웃일때!
            case "logout":
                //팝업 액티비티 닫아주고,
                finish();
                //사장님 액티비티 닫고,
                storeManager_mainActivity.finish();
                //로그인 페이지 열어주기!
                Intent login_intent = new Intent(this, Select_Log_Activity.class);
                startActivity(login_intent);
                break;

            case "withdrawal":
                finish();
                //사장님 액티비티 닫고,
                storeManager_mainActivity.finish();
                //첫 페이지 열어주기!
                Intent first_intent = new Intent(this, Select_Log_Activity.class);
                startActivity(first_intent);
                break;

            //픽업 완료 했을때!
            case "Pickup complete":
                //팝업 액티비티 닫아주고,
                finish();
                pickupDetailActivity.finish();
                break;
        }
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
