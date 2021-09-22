package com.kbc.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.kbc.R;
import com.kbc.Sale.Sale_Item;

import java.util.ArrayList;

public class Payment_Check_Activity extends AppCompatActivity {

    private Payment_Check_Activity payment_check_activity;
    private Sale_Item purchase_item;

    private TextView buyerNameInPickup, productNameInPickupDetail;

    private Spinner pickup_date_year,pickup_date_month, pickup_date_day, pickup_am_pm, pickup_hour, pickup_minute;
    private ArrayAdapter year_adapter, month_adapter, day_adapter, am_pm_adapter, hour_adapter, minute_adapter;

    private Button  payment_open;
    private ImageButton payment_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_check_activity);
        payment_check_activity = this;

        Intent intent = getIntent();
        purchase_item = ((ArrayList<Sale_Item>)intent.getSerializableExtra("purchase_item_list")).get(0);

        buyerNameInPickup = findViewById(R.id.buyerNameInPickup);
        productNameInPickupDetail = findViewById(R.id.productNameInPickupDetail);

        buyerNameInPickup.setText(purchase_item.getUser_Id());
        productNameInPickupDetail.setText(purchase_item.getName());

        //픽업 날짜 스피너연결
        pickup_date_year = findViewById(R.id.pickup_date_year);
        pickup_date_month = findViewById(R.id.pickup_date_month);
        pickup_date_day = findViewById(R.id.pickup_date_day);
        //픽업 시간 스피너 연결
        pickup_am_pm = findViewById(R.id.pickup_am_pm);
        pickup_hour = findViewById(R.id.pickup_hour);
        pickup_minute = findViewById(R.id.pickup_minute);

        //스피너 어뎁터 초기화
        year_adapter = ArrayAdapter.createFromResource(this, R.array.year, R.layout.spinner_date);
        month_adapter = ArrayAdapter.createFromResource(this, R.array.month, R.layout.spinner_date);
        day_adapter = ArrayAdapter.createFromResource(this, R.array.day, R.layout.spinner_date);

        am_pm_adapter = ArrayAdapter.createFromResource(this, R.array.am_pm, R.layout.spinner_date);
        hour_adapter = ArrayAdapter.createFromResource(this, R.array.hour, R.layout.spinner_date);
        minute_adapter = ArrayAdapter.createFromResource(this, R.array.minute, R.layout.spinner_date);

        //스피너 형성
        Create_Spinner(pickup_date_year, year_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_date_month, month_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_date_day, day_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_am_pm, am_pm_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_hour, hour_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_minute, minute_adapter, R.layout.spinner_date);


        payment_close = findViewById(R.id.payment_close);
        payment_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //결제하기 창 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        payment_open = findViewById(R.id.payment_open);
        payment_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //결제 완료되면 장보기로 돌아가기!!!!!!!!!!!!!!! + 서버쪽에 전달해야함 !!!
            }
        });
    }
    //스피너 형성
    private void Create_Spinner(Spinner spinner, ArrayAdapter arrayAdapter,  int layout_type){

        arrayAdapter.setDropDownViewResource(layout_type);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

    }

}