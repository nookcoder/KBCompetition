package com.kbc.Sale;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kbc.Popup_OneButton_Activity;
import com.kbc.Popup_TwoButton_Activity;
import com.kbc.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

import org.json.JSONException;
import org.json.JSONObject;

public class StoreManager_Product_Modify_Activity extends AppCompatActivity {

    public static StoreManager_Product_Modify_Activity storeManager_product_modify_activity;
    //인텐트에서 넘어오는 정보들
    private ArrayList<Sale_Item> sale_items ;
    private Sale_Item sale_item, previous_item = new Sale_Item();

    //상품 정보들
    private String storeManager_id, storeManager_location;
    private EditText product_name, product_register_time, product_price, product_origin, product_details;


    //카테고리, 재고
    private Spinner product_category,product_stock;
    private ArrayAdapter category_adapter, stock_adapter;
    private String[] category_list , stock_list;

    //기한
    private Spinner product_date_year, product_date_month, product_date_day;
    private ArrayAdapter year_adapter, month_adapter, day_adapter;
    private String[] year_list, month_list, day_list;

    private Button date_type_expiration, date_type_purchase;
    private String change_date_type;

    //창 닫기, 수정하기 버튼
    private ImageButton product_modify_close;
    private Button product_modify_sucess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_product_modify_activity);
        storeManager_product_modify_activity = this;

        //상품 정보 가져오기
        Intent intent = getIntent();
        sale_items = (ArrayList<Sale_Item>)intent.getSerializableExtra("sale_item_list");
        sale_item = sale_items.get(0);
        Previous_Sale_item(sale_item);
        storeManager_id = intent.getExtras().getString("userID");
        storeManager_location = intent.getExtras().getString("location");

        //상품제목
        product_name = findViewById(R.id.product_name);

        //카테고리 스피너 가져오기
        product_category = findViewById(R.id.product_category);
        category_adapter = ArrayAdapter.createFromResource(this, R.array.category,
                R.layout.spinner_item);
        category_adapter.setDropDownViewResource(R.layout.spinner_item);
        product_category.setAdapter(category_adapter);
        category_list = getResources().getStringArray(R.array.category);
        Insert_Spinner_Current_Data(category_list, product_category,sale_item.getCategory());

        //카테고리 스피너 이벤트
        product_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_category = category_adapter.getItem(position).toString();
                sale_item.setCategory(select_category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //재고 스피너 가져오기
        product_stock = findViewById(R.id.product_stock);
        stock_adapter = ArrayAdapter.createFromResource(this, R.array.stock,
                R.layout.spinner_item);
        stock_adapter.setDropDownViewResource(R.layout.spinner_item);
        product_stock.setAdapter(stock_adapter);
        stock_list = getResources().getStringArray(R.array.stock);
        Insert_Spinner_Current_Data(stock_list, product_stock, sale_item.getStock());

        product_stock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_stock = stock_adapter.getItem(position).toString();
                sale_item.setStock(select_stock);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //상품 기한 -년
        product_date_year =findViewById(R.id.product_date_year);
        year_adapter = ArrayAdapter.createFromResource(this, R.array.year,R.layout.spinner_date);
        year_adapter.setDropDownViewResource(R.layout.spinner_date);
        product_date_year.setAdapter(year_adapter);
        year_list = getResources().getStringArray(R.array.year);
        Insert_Spinner_Current_Data(year_list, product_date_year,sale_item.getDate_year());
        product_date_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_year = year_adapter.getItem(position).toString();
                sale_item.setDate_year(select_year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //상품 기한 -월
        product_date_month = findViewById(R.id.product_date_month);
        month_adapter = ArrayAdapter.createFromResource(this, R.array.month,R.layout.spinner_date);
        month_adapter.setDropDownViewResource(R.layout.spinner_date);
        product_date_month.setAdapter(month_adapter);
        month_list = getResources().getStringArray(R.array.month);
        Insert_Spinner_Current_Data(month_list, product_date_month,sale_item.getDate_month());
        product_date_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_month = month_adapter.getItem(position).toString();
                sale_item.setDate_month(select_month);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //상품 기한 -일
        product_date_day = findViewById(R.id.product_date_day);
        day_adapter = ArrayAdapter.createFromResource(this, R.array.day,R.layout.spinner_date);
        day_adapter.setDropDownViewResource(R.layout.spinner_date);
        product_date_day.setAdapter(day_adapter);
        day_list = getResources().getStringArray(R.array.day);
        Insert_Spinner_Current_Data(day_list, product_date_day,sale_item.getDate_day());
        product_date_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_day = day_adapter.getItem(position).toString();
                sale_item.setDate_day(select_day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        date_type_expiration = findViewById(R.id.product_expiration);
        date_type_purchase = findViewById(R.id.product_purchase);

        product_origin = findViewById(R.id.product_origin);
        product_price = findViewById(R.id.product_price);

        product_details = findViewById(R.id.product_details);

    //실제 값 넣기

        //상품제목
        product_name.setText(sale_item.getName());

        //유통기한 넣고, 구입/ 유통인지 판별하기

        if(sale_item.getDate_type().equals("유통"))
            Change_Button_color("유통");
        else
            Change_Button_color("구입");


        date_type_expiration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Change_Button_color("유통");
                sale_item.setDate_type("유통");
            }
        });

        date_type_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Button_color("구입");
                sale_item.setDate_type("구입");
            }
        });
        //원산지
        product_origin.setText(sale_item.getOrigin());
        product_price.setText("가격 "+ sale_item.getPrice()+"원");

        //상세설명
        product_details.setText(sale_item.getDetails());



        //버튼 이벤트
        product_modify_close =findViewById(R.id.product_modify_close);
        product_modify_sucess = findViewById(R.id.product_modify_sucess);

        //창닫기
        product_modify_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ArrayList<Sale_Item> sale_items = new ArrayList<Sale_Item>();
                Log.d(TAG, "날짜 ->" + previous_item.getDate_day());
                sale_items.add(previous_item);

//                //상품 조회 액티비티로 들어가기
                Intent intent = new Intent(storeManager_product_modify_activity, StoreManager_Product_Inquiry_Activity.class);
                intent.putExtra("sale_item_list", sale_items);
                intent.putExtra("userID", storeManager_id);
                intent.putExtra("location",storeManager_location);
                startActivity(intent);


            }
        });

        //수정하기
        product_modify_sucess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Sale_Item> sale_items = new ArrayList<Sale_Item>();
                //이미지, 이름, 카테고리, 재고, 가격, 기한, 원산지, 상세설명
                //이미지 넣기코드 필요 !!!!!!!!!!
                sale_item.setName(product_name.getText().toString());
                sale_item.setCategory(product_category.getSelectedItem().toString());
                sale_item.setStock(product_stock.getSelectedItem().toString());

                //기한, 가격
                sale_item.setOrigin(product_origin.getText().toString());
                sale_item.setDetails(product_details.getText().toString());
                sale_items.add(sale_item);

                // 서버에 정보 업데이트
                try {
                    updateProductInfo(storeManager_id,sale_item.getName(),sale_item.getCategory(),sale_item.getStock(),sale_item.getPrice(),sale_item.getDate_year(),sale_item.getDate_month(),sale_item.getDate_day(),sale_item.getDate_type(),sale_item.getOrigin(),sale_item.getDetails(),sale_item.getRegister_time());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //상품 수정 완료 확인 -> 조회 화면 또는 재수정
                Intent popup_intent = new Intent(storeManager_product_modify_activity, Popup_TwoButton_Activity.class);
                intent.putExtra("button_name","product_modify");
                intent.putExtra("sale_item_list", sale_items);
                intent.putExtra("userID",storeManager_id);
                intent.putExtra("location", storeManager_location);
                startActivity(intent);

            }
        });


    }

    private void Change_Button_color(String date_type){

        if(date_type.equals("유통")){
            date_type_expiration.setBackgroundResource(R.color.icon_color2);
            date_type_purchase.setBackgroundResource(R.drawable.edge);
        }
        else{
            date_type_purchase.setBackgroundResource(R.color.icon_color2);
            date_type_expiration.setBackgroundResource(R.drawable.edge);
        }
    }

    private void Insert_Spinner_Current_Data(String []spinner_list, Spinner spinner, String current_data){
        for(int index = 0 ; index< spinner_list.length ; index++){
            Log.d(TAG, "현재 데이터 -> " + current_data + "/ 스피너 리스트 -> " + spinner_list[index] + " 판별 -> "+current_data.equals(spinner_list[index]));
            if(current_data.equals(spinner_list[index])){
                spinner.setSelection(index);
                break;
            }
        }
    }

    public void Previous_Sale_item(Sale_Item current_item){
        previous_item.serProductImageSrc(current_item.getProductImageSrc());
        previous_item.setName(current_item.getName());
        previous_item.setCategory(current_item.getCategory());
        previous_item.setStock(current_item.getStock());
        previous_item.setPrice(current_item.getPrice());
        previous_item.setDate_year(current_item.getDate_year());
        previous_item.setDate_month(current_item.getDate_month());
        previous_item.setDate_day(current_item.getDate_day());
        previous_item.setDate_type(current_item.getDate_type());
        previous_item.setOrigin(current_item.getOrigin());
        previous_item.setDetails(current_item.getDetails());
        previous_item.setRegister_time(current_item.getRegister_time());
    }

    // 서버 정보 수정 함수
    public void updateProductInfo(String id,String name, String category,String stock,String price,String dateYear,String dateMonth,String dateDay,String dateType,String origin,String details,String registerTime) throws JSONException {
        String URL = "http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000/product/update";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",id);
        jsonObject.put("name",name);
        jsonObject.put("category",category);
        jsonObject.put("stock",stock);
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

        RequestQueue requestQueue = Volley.newRequestQueue(StoreManager_Product_Modify_Activity.this);
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        },null);

    }
}