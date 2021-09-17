package com.kbc.Sale;

import androidx.appcompat.app.AppCompatActivity;
import com.kbc.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class StoreManager_Product_Modify_Activity extends AppCompatActivity {

    //인텐트에서 넘어오는 정보들
    private ArrayList<Sale_Item> sale_items ;
    private Sale_Item sale_item;

    //상품 정보들
    private TextView storemanager_id, storemanager_location;
    private TextView product_title, product_category,product_register_time, product_price, product_count, product_details;

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

        product_title = findViewById(R.id.product_title);
        product_category = findViewById(R.id.product_category);
        product_register_time = findViewById(R.id.product_register_time);
        product_price = findViewById(R.id.product_price);
        product_count = findViewById(R.id.product_count);
        product_details = findViewById(R.id.product_details);



    }
}