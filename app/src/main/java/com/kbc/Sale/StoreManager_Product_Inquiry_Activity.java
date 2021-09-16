package com.kbc.Sale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.kbc.R;

import java.util.ArrayList;

public class StoreManager_Product_Inquiry_Activity extends AppCompatActivity {
    private ArrayList<Sale_Item> sale_items ;
    private Sale_Item sale_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_product_inquiry_activity);
        //상품 정보 가져오기
        Intent intent = getIntent();
        sale_items = (ArrayList<Sale_Item>)intent.getSerializableExtra("sale_item_list");
        sale_item = sale_items.get(0);
    }
}