package com.kbc.Sale;

import androidx.appcompat.app.AppCompatActivity;
import com.kbc.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class StoreManager_Product_Modify_Activity extends AppCompatActivity {

    //인텐트에서 넘어오는 정보들
    private ArrayList<Sale_Item> sale_items ;
    private Sale_Item sale_item;

    //상품 정보들
    private TextView storemanager_id, storemanager_location;
    private TextView product_title, product_category,product_date, product_register_time, product_price, product_count,product_origin, product_details;

    private Button date_type_expiration, date_type_purchase;
    private String date_type;
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
        product_count = findViewById(R.id.product_count);

        product_date = findViewById(R.id.product_date);
        date_type_expiration = findViewById(R.id.product_expiration);
        date_type_purchase = findViewById(R.id.product_purchase);

        product_origin = findViewById(R.id.product_origin);
        product_price = findViewById(R.id.product_price);

        product_details = findViewById(R.id.product_details);

        //상품제목
        product_title.setText(sale_item.getName());

        //카테고리 넣기
        product_category.setText(sale_item.getCategory());
        //재고 넣기
        product_count.setText("재고 " + sale_item.getStock() +"개");

        //유통기한 넣고, 구입/ 유통인지 판멸하기
        product_date.setText(sale_item.getDate());

        if(sale_item.getDate().contains("(유통)"))
            Change_Button_color("유통");
        else
            Change_Button_color("구입");


        date_type_expiration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Change_Button_color("유통");
            }
        });

        date_type_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Button_color("구입");

            }
        });
        //원산지
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