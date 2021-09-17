package com.kbc.Sale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kbc.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class StoreManager_Product_Inquiry_Activity extends AppCompatActivity {
    //인텐트에서 넘어오는 정보들
    private ArrayList<Sale_Item> sale_items ;
    private Sale_Item sale_item;
    private String storeManager_id , storeManager_location;

    //상품 정보들
    private TextView storemanager_id, storemanager_location;
    private TextView product_title, product_category,product_register_time, product_price, product_stock, product_details;

    //창 닫기, 수정하기 버튼
    private ImageButton product_modify_imageButton, product_inquiry_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_product_inquiry_activity);
        //상품 정보 가져오기
        Intent intent = getIntent();
        sale_items = (ArrayList<Sale_Item>)intent.getSerializableExtra("sale_item_list");
        sale_item = sale_items.get(0);

        //로그인 정보 가져오기
        storeManager_id = intent.getExtras().getString("id");
        storeManager_location = intent.getExtras().getString("location");

        //컴포넌트 가져오기
        storemanager_id = findViewById(R.id.storemanager_id);
        storemanager_location  = findViewById(R.id.storemanager_location);
        product_title = findViewById(R.id.product_title);
        product_category = findViewById(R.id.product_category);
        product_register_time = findViewById(R.id.product_register_time);
        product_price = findViewById(R.id.product_price);
        product_stock = findViewById(R.id.product_stock);
        product_details = findViewById(R.id.product_details);

        product_modify_imageButton = findViewById(R.id.product_modify_imageButton);
        product_inquiry_close = findViewById(R.id.product_inquiry_close);

        //상품 사진들 넣어야함!!!!!!!!!!!!!!!!!!!!!!!!!!1


        //가게 이름, 주소 넣기
        storemanager_id.setText(storeManager_id);
        storemanager_location.setText(storeManager_location);

        //제목, 카테고리 넣기
        product_title.setText(sale_item.getName());
        product_category.setText(sale_item.getCategory());
        product_register_time.setText(sale_item.getRegister_time());

        //판매가격, 재고 넣기
        product_price.setText("가격 "+ sale_item.getPrice()+"원");
        product_stock.setText("재고 " + sale_item.getStock() +"개");

        //상세설명
        product_details.setText(sale_item.getDetails());


        Log.d(TAG, "리스트 -> "+ sale_item.getDate());
        Log.d(TAG, "리스트 -> "+ sale_item.getRegister_time());
        Log.d(TAG, "리스트 -> "+ sale_item.getOrigin());


        //수정하기 버튼 이벤트
        product_modify_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Sale_Item> sale_items = new ArrayList<Sale_Item>();
                sale_items.add(sale_item);

                //상품 등록 액티비티로 들어가기
                Intent intent = new Intent(StoreManager_Product_Inquiry_Activity.this, StoreManager_Product_Modify_Activity.class);
                intent.putExtra("sale_item_list", sale_items);
                intent.putExtra("id", storeManager_id);
                intent.putExtra("location",storeManager_location);
                startActivity(intent);
                finish();
            }
        });

        //창 닫기
        product_inquiry_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}