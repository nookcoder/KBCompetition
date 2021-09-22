package com.kbc.Purchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kbc.R;
import com.kbc.Sale.Sale_Item;
import com.kbc.StoreManger.StoreManager_MainActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Personal_Purchase_Inquiry_Activity extends AppCompatActivity {

    private Personal_Purchase_Inquiry_Activity personal_purchase_inquiry_activity;
    private Sale_Item purchase_item;

    private CircleImageView storemanager_image_profile;
    private ImageButton product_inquiry_close, chatting_imageButton;
    private TextView storemanager_id, storemanager_location, product_title, product_category, product_date_type, product_deadline_time
            ,product_price,product_details;

    private Button product_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_purchase_inquiry_activity);
        personal_purchase_inquiry_activity = this;
        //선택한 장보기 아이템 가져오기 (사업자이름 , 상품이름 , 카테고리, 가격 만 있음!)
        Intent intent = getIntent();
        purchase_item = ((ArrayList<Sale_Item>)intent.getSerializableExtra("purchase_item_list")).get(0);

        //상품 조회 창 닫기
        product_inquiry_close = findViewById(R.id.product_inquiry_close);

        product_inquiry_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(personal_purchase_inquiry_activity, StoreManager_MainActivity.class);
                intent.putExtra("userID",purchase_item.getPersonal_Id());
                startActivity(intent);
            }
        });

        //차례대로 컴포넌트 가져오기 (텍스트뷰)
        storemanager_id = findViewById(R.id.storemanager_id);
        storemanager_location = findViewById(R.id.storemanager_location);
        product_title = findViewById(R.id.product_title);
        product_category = findViewById(R.id.product_category);
        product_date_type = findViewById(R.id.product_date_type);
        product_deadline_time = findViewById(R.id.product_deadline_time);
        product_price = findViewById(R.id.product_price);
        product_details = findViewById(R.id.product_details);

        //데이터 넣기
        storemanager_id.setText(purchase_item.getUser_Id());
        storemanager_location.setText(purchase_item.getUser_location());
        product_title.setText(purchase_item.getName());
        product_category.setText(purchase_item.getCategory());

        if(purchase_item.getDate_type().equals("유통"))
            product_date_type.setText("유통기한");
        else
            product_date_type.setText("구입날짜");
        product_deadline_time.setText(purchase_item.getDate_year() +" "+ purchase_item.getDate_month() + " " + purchase_item.getDate_day());
        product_price.setText( "가격 "+ purchase_item.getPrice()+"원");
        product_details.setText(purchase_item.getDetails());




    }

}