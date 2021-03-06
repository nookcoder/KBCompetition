package com.kbc.Common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kbc.Chatting.Chatting;
import com.kbc.Personal_MainActivity;
import com.kbc.R;
import com.kbc.Sale.Sale_Item;
import com.kbc.Sale.StoreManager_Product_Modify_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.kbc.Pickup.PickupDetailActivity;
import com.kbc.Server.Personal;
import com.kbc.Server.PickUpData;
import com.kbc.Server.RetrofitBulider;
import com.kbc.Server.ServiceApi;
import com.kbc.StoreManger.StoreManager_MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;


public class Popup_TwoButton_Activity extends AppCompatActivity {

    private Intent intent;
     String userId, userLocation, mode;
     ArrayList<Sale_Item> sale_items;
    private  Sale_Item sale_item;
    private  String button_name;
    private TextView popup_title, popup_context;
    Button ok_btn, cancle_btn;
    ServiceApi serviceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_twobutton_activity);

        //인텐트 가져오기
        intent = getIntent();

        //이벤트 발생 버튼 확인을 위한 데이터 접근
        button_name = intent.getExtras().getString("button_name");


        //팝업창 제목, 내용 바꾸기
        popup_title = findViewById(R.id.popup_title);
        popup_context = findViewById(R.id.popup_context);
        //확인, 취소 버튼 가져오기
        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancle_btn = (Button) findViewById(R.id.cancle_btn);

        switch (button_name) {
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
                //!!서버 관련 데이터 가져오기 !!!!!!
                userId = intent.getStringExtra("userID");
                sale_items = (ArrayList<Sale_Item>) intent.getSerializableExtra("sale_item_list");
                //실질적으로 수정 / 삭제 해야하는 정보 담은 객체!!!!!!!
                sale_item = sale_items.get(0);

                Log.d("팝업창 아이디", userId);
                break;

            case "product_delete":
                popup_title.setText("상품 삭제");
                popup_context.setText("정말로 삭제하시겠습니까???");
                //!!서버 관련 데이터 가져오기 !!!!!!
                userId = intent.getStringExtra("userID");
                sale_items = (ArrayList<Sale_Item>) intent.getSerializableExtra("sale_item_list");
                //실질적으로 수정 / 삭제 해야하는 정보 담은 객체!!!!!!!
                sale_item = sale_items.get(0);
                Log.d("팝업창 아이디", userId);

                break;

        }


    }
    //확인 버튼 클릭
    public void click_ok(View view){
        //사장님 액티비티 호출하고,
        StoreManager_MainActivity storeManager_mainActivity = (StoreManager_MainActivity)StoreManager_MainActivity.storeManager_mainActivity;
        PickupDetailActivity pickupDetailActivity = (PickupDetailActivity)PickupDetailActivity.pickupDetailActivity;
        StoreManager_Product_Modify_Activity storeManager_product_modify_activity = (StoreManager_Product_Modify_Activity)StoreManager_Product_Modify_Activity.storeManager_product_modify_activity;
        Personal_MainActivity personal_mainActivity  = (Personal_MainActivity)Personal_MainActivity.personal_mainActivity;


        switch (button_name){

            //로그아웃일때!
            case "logout":
                //팝업 액티비티 닫아주고,

                finish();
                //사장님 액티비티 닫고,
                mode = intent.getStringExtra("mode");
                if(mode.equals(Chatting.PERSONAL))
                    personal_mainActivity.finish();
                else
                    storeManager_mainActivity.finish();

                //로그인 페이지 열어주기!
                Intent login_intent = new Intent(this, Login_Activity.class);
                startActivity(login_intent);
                break;

            case "withdrawal":
                finish();
                //사장님 액티비티 닫고,
                mode = intent.getStringExtra("mode");
                if(mode.equals(Chatting.PERSONAL))
                    personal_mainActivity.finish();
                else
                    storeManager_mainActivity.finish();


                //첫 페이지 열어주기!
                Intent first_intent = new Intent(this, Login_Activity.class);
                startActivity(first_intent);
                break;

            //픽업 완료 했을때!
            case "Pickup complete":
                //팝업 액티비티 닫아주고,
                userId = intent.getStringExtra("storeManagerId");
                String buyerName = intent.getStringExtra("buyerName");
                String productName = intent.getStringExtra("productName");
                String registerTime = intent.getStringExtra("registerTime");
                PickUpData pick = new PickUpData(buyerName,productName,registerTime);
                donePickUp(userId,pick);
                finish();
                pickupDetailActivity.finish();
                break;

                //상품 수정완료
            case "product_modify":
                //여기서 상품 최종 수정이 되어야함!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                // 서버에 정보 업데이트
                try {
                    updateProductInfo(userId,sale_item.getName(),sale_item.getCategory(),sale_item.getPrice(),sale_item.getDate_year(),sale_item.getDate_month(),sale_item.getDate_day(),sale_item.getDate_type(),sale_item.getOrigin(),sale_item.getDetails(),sale_item.getRegister_time());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                //다시 수정 올려주기
//                Log.d("팝업창 아이디", userId);
//
                storeManager_mainActivity.finish();

                Intent main_intent = new Intent(this, StoreManager_MainActivity.class);
                main_intent.putExtra("userID",userId);
                startActivity(main_intent);

                finish();


                break;

                //상품 삭제 완료
            case "product_delete":
                storeManager_mainActivity.finish();
                //여기서 상품 삭제!!!!!
                deleteProductInfo(userId,sale_item.getRegister_time());
                Intent intent = new Intent(this, StoreManager_MainActivity.class);
                intent.putExtra("userID",userId);
                startActivity(intent);
                Log.d("userId",userId);


                storeManager_product_modify_activity.finish();

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

    // 서버 정보 수정 함수
    public void updateProductInfo(String id,String name, String category,String price,String dateYear,String dateMonth,String dateDay,String dateType,String origin,String details,String registerTime) throws JSONException {
        String URL = "http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000/product/update";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",id);
        jsonObject.put("name",name);
        jsonObject.put("category",category);
        jsonObject.put("price",price);
        jsonObject.put("dateYear",dateYear);
        jsonObject.put("dateMonth",dateMonth);
        jsonObject.put("dateDay",dateDay);
        jsonObject.put("dateType",dateType);
        jsonObject.put("origin",origin);
        jsonObject.put("details",details);
        jsonObject.put("registerTime",registerTime);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Result",response.toString());
            }
        },null);

        RequestQueue requestQueue = Volley.newRequestQueue(Popup_TwoButton_Activity.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void deleteProductInfo(String id, String registerTiem){
        String URL = "http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000/product/delete";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",id);
            jsonObject.put("registerTime",registerTiem);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        },null);

        RequestQueue requestQueue = Volley.newRequestQueue(Popup_TwoButton_Activity.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void donePickUp(String userId, PickUpData pick){
        ServiceApi serviceApi;
        serviceApi = new RetrofitBulider().initRetrofit();

        Call<PickUpData> call = serviceApi.donePickUp(userId,pick);

        call.enqueue(new Callback<PickUpData>() {
            @Override
            public void onResponse(Call<PickUpData> call, retrofit2.Response<PickUpData> response) {
                PickUpData pickUpData = response.body();
            }

            @Override
            public void onFailure(Call<PickUpData> call, Throwable t) {
            }
        });
    }

}
