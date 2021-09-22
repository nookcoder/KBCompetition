package com.kbc.Purchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kbc.R;
import com.kbc.Sale.Sale_Item;

import java.util.ArrayList;

public class Personal_Purchase_Inquiry_Activity extends AppCompatActivity {

    private Sale_Item purchase_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_purchase_inquiry_activity);

        //선택한 장보기 아이템 가져오기 (사업자이름 , 상품이름 , 카테고리, 가격 만 있음!)
        Intent intent = getIntent();
        purchase_item = ((ArrayList<Sale_Item>)intent.getSerializableExtra("purchase_item_list")).get(0);


    }

}