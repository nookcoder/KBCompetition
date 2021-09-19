package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.Sale.Sale_Item;
import com.kbc.Sale.StoreManager_Product_Inquiry_Activity;
import com.kbc.Sale.StoreManager_Product_Modify_Activity;

import java.util.ArrayList;

public class Popup_TwoButton_Activity extends AppCompatActivity {

    private Intent intent;
    private String userId, userLocation;
    private ArrayList<Sale_Item> sale_items;
    private  Sale_Item sale_item;
    private  String button_name;
    private TextView popup_title, popup_context;
    Button ok_btn, cancle_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_onebutton_activity);

        //인텐트 가져오기
        intent = getIntent();

        //이벤트 발생 버튼 확인을 위한 데이터 접근
        button_name = intent.getExtras().getString("button_name");


        //!!서버 관련 데이터 가져오기 !!!!!!
        userId = intent.getStringExtra("userID");
        sale_items = (ArrayList<Sale_Item>)intent.getSerializableExtra("sale_item_list");
        //실질적으로 수정 / 삭제 해야하는 정보 담은 객체!!!!!!!
        sale_item = sale_items.get(0);




        //팝업창 제목, 내용 바꾸기
        popup_title = findViewById(R.id.popup_title);
        popup_context = findViewById(R.id.popup_context);
        //확인, 취소 버튼 가져오기
        ok_btn = (Button)findViewById(R.id.ok_btn);
        cancle_btn = (Button)findViewById(R.id.cancle_btn);

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

            case "product_modify":
                popup_title.setText("수정 완료");
                popup_context.setText("정보수정을 하시겠습니까?");
                break;

            case "product_delete":
                popup_title.setText("상품 삭제");
                popup_context.setText("정말로 삭제하시겠습니까???");
                break;
        }




    }

    //확인 버튼 클릭
    public void click_ok(View view){
        //사장님 액티비티 호출하고,
        StoreManager_MainActivity storeManager_mainActivity = (StoreManager_MainActivity)StoreManager_MainActivity.storeManager_mainActivity;
        PickupDetailActivity pickupDetailActivity = (PickupDetailActivity)PickupDetailActivity.pickupDetailActivity;
        StoreManager_Product_Modify_Activity storeManager_product_modify_activity = (StoreManager_Product_Modify_Activity)StoreManager_Product_Modify_Activity.storeManager_product_modify_activity;


        switch (button_name){

            //로그아웃일때!
            case "logout":
                //팝업 액티비티 닫아주고,
                finish();
                //사장님 액티비티 닫고,
                storeManager_mainActivity.finish();
                //로그인 페이지 열어주기!
                Intent login_intent = new Intent(this, Login_Activity.class);
                startActivity(login_intent);
                break;

            case "withdrawal":
                finish();
                //사장님 액티비티 닫고,
                storeManager_mainActivity.finish();
                //첫 페이지 열어주기!
                Intent first_intent = new Intent(this, Login_Activity.class);
                startActivity(first_intent);
                break;

            //픽업 완료 했을때!
            case "Pickup complete":
                //팝업 액티비티 닫아주고,
                finish();
                pickupDetailActivity.finish();
                break;

                //상품 수정완료
            case "product_modify":
                finish();
                storeManager_product_modify_activity.finish();
               //여기서 상품 최종 수정이 되어야함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                //다시 수정 올려주기
                Intent modify_intent = new Intent(this, StoreManager_Product_Modify_Activity.class);
                intent.putExtra("sale_item_list", (ArrayList<Sale_Item>)intent.getSerializableExtra("sale_item_list"));
                intent.putExtra("userID", intent.getStringExtra("userID"));
                intent.putExtra("location", intent.getStringExtra("location"));
                startActivity(modify_intent);
                break;

                //상품 삭제 완료
            case "product_delete":
                finish();
                storeManager_product_modify_activity.finish();
                //여기서 상품 삭제!!!!!
                Intent intent = new Intent(this, StoreManager_MainActivity.class);
                intent.putExtra("userID",intent.getStringExtra("userID"));
                startActivity(intent);


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
