package com.kbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kbc.Sale.StoreManager_Product_Register_Activity;

public class Popup_OneButton_Activity extends AppCompatActivity {

    private  String button_name;
    private TextView popup_title, popup_context;
    Button ok_btn;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_onebutton_activity);

        //인텐트 가져오기
        Intent intent = getIntent();

        //이벤트 발생 버튼 확인을 위한 데이터 접근
        button_name = intent.getExtras().getString("button_name");
        userId = intent.getExtras().getString("userID");

        //팝업창 제목, 내용 바꾸기
        popup_title = findViewById(R.id.popup_title);
        popup_context = findViewById(R.id.popup_context);
        switch (button_name){
            case "product_register":
                popup_title.setText("상품등록");
                popup_context.setText("상품등록에 성공하였습니다");
                break;

        }

        //확인, 취소 버튼 가져오기
        ok_btn = (Button)findViewById(R.id.ok_btn);
    }

    //확인 버튼 클릭
    public void click_ok(View view){
        //사장님 액티비티 호출하고,
        StoreManager_Product_Register_Activity storeManager_product_register_activity = (StoreManager_Product_Register_Activity)
                StoreManager_Product_Register_Activity.storeManager_product_register_activity;


        switch (button_name){

            //상품 등록
            case "product_register":
                //팝업 액티비티 닫아주고, 상품 등록 닫기
                finish();
                storeManager_product_register_activity.finish();
                //로그인 페이지 열어주기!
                Intent main_intent = new Intent(this, StoreManager_MainActivity.class);
                main_intent.putExtra("userID", userId);
                startActivity(main_intent);
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