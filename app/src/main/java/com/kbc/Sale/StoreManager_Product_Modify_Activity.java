package com.kbc.Sale;

import androidx.appcompat.app.AppCompatActivity;
import com.kbc.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class StoreManager_Product_Modify_Activity extends AppCompatActivity {

    //인텐트에서 넘어오는 정보들
    private ArrayList<Sale_Item> sale_items ;
    private Sale_Item sale_item;

    //상품 정보들
    private TextView storemanager_id, storemanager_location;
    private TextView product_title, product_date, product_register_time, product_price, product_origin, product_details;

    private Spinner product_category,product_stock;
    private ArrayAdapter category_adapter, stock_adapter;

    private Button date_type_expiration, date_type_purchase;
    private String change_date_type;
    //창 닫기, 수정하기 버튼
    private ImageButton product_modify_imageButton, product_inquiry_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_product_modify_activity);

        //상품 정보 가져오기
        Intent intent = getIntent();
        sale_items = (ArrayList<Sale_Item>)intent.getSerializableExtra("sale_item_list");
        sale_item = sale_items.get(0);

        Log.d(TAG, "리스트-> "+ sale_item.getDate());


        product_title = findViewById(R.id.product_title);

        //카테고리 스피너 가져오기
        product_category = findViewById(R.id.product_category);
        category_adapter = ArrayAdapter.createFromResource(this, R.array.category,
                R.layout.spinner_item);
        category_adapter.setDropDownViewResource(R.layout.spinner_item);
        product_category.setAdapter(category_adapter);
        product_category.setSelection(0);

        //카테고리 스피너 이벤트
        product_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_category = category_adapter.getItem(position).toString();
                Log.d(TAG, "카테고리 -> "+ select_category);
                sale_item.setCategory(select_category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //재고 스피너 가져오기
        product_stock = findViewById(R.id.product_stock);
        stock_adapter = ArrayAdapter.createFromResource(this, R.array.count,
                R.layout.spinner_item);
        stock_adapter.setDropDownViewResource(R.layout.spinner_item);
        product_stock.setAdapter(stock_adapter);
        product_stock.setSelection(0);

        product_stock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_stock = stock_adapter.getItem(position).toString();
                Log.d(TAG, "카테고리 -> "+ select_stock);
                sale_item.setStock(select_stock);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //상품 기한
        product_date = findViewById(R.id.product_date);
        date_type_expiration = findViewById(R.id.product_expiration);
        date_type_purchase = findViewById(R.id.product_purchase);

        product_origin = findViewById(R.id.product_origin);
        product_price = findViewById(R.id.product_price);

        product_details = findViewById(R.id.product_details);

    //실제 값 넣기

        //상품제목
        product_title.setText(sale_item.getName());

        //유통기한 넣고, 구입/ 유통인지 판별하기
        product_date.setText(sale_item.getDate());

        if(sale_item.getDate().contains("(유통)"))
            Change_Button_color("유통");
        else
            Change_Button_color("구입");


        date_type_expiration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Change_Button_color("유통");
                if(sale_item.getDate().contains("구입"))
                    sale_item.setDate(sale_item.getDate().replace("구입", "유통"));
            }
        });

        date_type_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Button_color("구입");
                if(sale_item.getDate().contains("유통"))
                    sale_item.setDate(sale_item.getDate().replace("유통", "구입"));
            }
        });
        //원산지
        product_origin.setText(sale_item.getOrigin());
        product_price.setText("가격 "+ sale_item.getPrice()+"원");

        //상세설명
        product_details.setText(sale_item.getDetails());



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

}