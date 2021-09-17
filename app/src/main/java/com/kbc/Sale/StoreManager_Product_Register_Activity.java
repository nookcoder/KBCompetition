package com.kbc.Sale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.kbc.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StoreManager_Product_Register_Activity extends AppCompatActivity {

    //새로 등록하는 상품
    private Sale_Item register_item = new Sale_Item();

    //상품 정보들
    private TextView product_name, product_register_time, product_price, product_origin, product_details;


    //카테고리, 재고
    private Spinner product_category, product_stock;
    private ArrayAdapter category_adapter, stock_adapter;
    private String[] category_list, stock_list;

    //기한
    private Spinner product_date_year, product_date_month, product_date_day;
    private ArrayAdapter year_adapter, month_adapter, day_adapter;
    private String[] year_list, month_list, day_list;

    private Button date_type_expiration, date_type_purchase;
    private String change_date_type;

    //창 닫기, 수정하기 버튼
    private ImageButton product_register_close;
    private Button product_register_sucess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_product_register_activity);

        //상품제목
        product_name = findViewById(R.id.product_name);

        //카테고리, 재고, 기한(년,월,일) 스피너 가져오기
        product_category = findViewById(R.id.product_category);
        product_stock = findViewById(R.id.product_stock);
        product_date_year = findViewById(R.id.product_date_year);
        product_date_month = findViewById(R.id.product_date_month);
        product_date_day = findViewById(R.id.product_date_day);

        //스피너 어뎁터 초기화
        category_adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_item);
        stock_adapter = ArrayAdapter.createFromResource(this, R.array.stock, R.layout.spinner_item);
        year_adapter = ArrayAdapter.createFromResource(this, R.array.year, R.layout.spinner_date);
        month_adapter = ArrayAdapter.createFromResource(this, R.array.month, R.layout.spinner_date);
        day_adapter = ArrayAdapter.createFromResource(this, R.array.day, R.layout.spinner_date);

        //스피너 형성
        Create_Spinner(product_category, category_adapter, R.layout.spinner_item);
        Create_Spinner(product_stock,stock_adapter, R.layout.spinner_item);
        Create_Spinner(product_date_year,year_adapter,R.layout.spinner_date);
        Create_Spinner(product_date_month,month_adapter, R.layout.spinner_date);
        Create_Spinner(product_date_day,day_adapter, R.layout.spinner_date);

        //나머지 상품정보 다 가져오기
        date_type_expiration = findViewById(R.id.product_expiration);
        date_type_purchase = findViewById(R.id.product_purchase);
        product_origin = findViewById(R.id.product_origin);
        product_price = findViewById(R.id.product_price);
        product_details = findViewById(R.id.product_details);

        //버튼 가져오기
        product_register_close = findViewById(R.id.product_register_close);
        product_register_sucess = findViewById(R.id.product_register_sucess);


        //카테고리 넣기
        product_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_category = category_adapter.getItem(position).toString();
                register_item.setCategory(select_category);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //재고 넣기
        product_stock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_stock = stock_adapter.getItem(position).toString();
                register_item.setStock(select_stock);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //년 넣기
        product_date_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_year = year_adapter.getItem(position).toString();
                register_item.setDate_year(select_year);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

       //월 넣기
        product_date_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_month = month_adapter.getItem(position).toString();
                register_item.setDate_month(select_month);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        //일 넣기
        product_date_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_day = day_adapter.getItem(position).toString();
                register_item.setDate_day(select_day);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        //유통 넣기
        date_type_expiration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Button_color("유통");
                register_item.setDate_type("유통");
            }
        });
        //구입 넣기
        date_type_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Button_color("구입");
                register_item.setDate_type("구입");
            }
        });


        //버튼 이벤트
        //창닫기
        product_register_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //등록하기
        product_register_sucess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //이미지, 이름, 카테고리, 재고, 가격, 기한, 원산지, 상세설명
                //이미지 넣기코드 필요 !!!!!!!!!!
                register_item.setName(product_name.getText().toString());
                register_item.setCategory(product_category.getSelectedItem().toString());
                register_item.setStock(product_stock.getSelectedItem().toString());

                //날짜
                register_item.setDate_year(product_date_year.getSelectedItem().toString());
                register_item.setDate_month(product_date_month.getSelectedItem().toString());
                register_item.setDate_day(product_date_day.getSelectedItem().toString());

                //원산지, 상세정보 , 등록시간
                register_item.setOrigin(product_origin.getText().toString());
                register_item.setDetails(product_details.getText().toString());
                register_item.setRegister_time(Register_Time());


            }
        });


    }

    private void Change_Button_color(String date_type) {

        if (date_type.equals("유통")) {
            date_type_expiration.setBackgroundResource(R.color.icon_color2);
            date_type_purchase.setBackgroundResource(R.drawable.edge);
        } else {
            date_type_purchase.setBackgroundResource(R.color.icon_color2);
            date_type_expiration.setBackgroundResource(R.drawable.edge);
        }
    }

    //스피너 형성
    private void Create_Spinner(Spinner spinner, ArrayAdapter arrayAdapter,  int layout_type){

        arrayAdapter.setDropDownViewResource(layout_type);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

    }

    private String Register_Time(){
        //메세지 보낸 시간
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat_date = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");

        return dateFormat_date.format(calendar.getTime());
    }




}